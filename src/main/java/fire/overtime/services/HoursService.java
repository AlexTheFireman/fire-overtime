package fire.overtime.services;

import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.commands.HoursUpdateCommand;
import fire.overtime.models.Hours;
import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.repositories.HoursRepository;
//import fire.overtime.repositories.MonthRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fire.overtime.models.Enums.HourType.*;

@Service
@Transactional
public class HoursService {

    @Autowired
    private HoursRepository hoursRepository;

    @Autowired
    FirefighterRepository firefighterRepository;

    private ModelMapper modelMapper;

    public Hours updateHours(HoursUpdateCommand hoursUpdateCommand) {
        Optional<Hours> byId = hoursRepository.findById(hoursUpdateCommand.getHourIdForUpdate());
        if (!byId.isPresent())
            throw new RuntimeException(String.format("Hours with id %d should be present", hoursUpdateCommand.getHourIdForUpdate()));

        Hours hours = byId.get();
        if (hoursUpdateCommand.getStartDate() != null) {
            hours.setStartDate(hoursUpdateCommand.getStartDate());
        }
        if (hoursUpdateCommand.getStartDate() != null) {
            hours.setStartDate(hoursUpdateCommand.getStartDate());
        }
        if (hoursUpdateCommand.getEndDate() != null) {
            hours.setEndDate(hoursUpdateCommand.getEndDate());
        }
        if (hoursUpdateCommand.getAmountHours() > 0) {
            hours.setAmountHours(hoursUpdateCommand.getAmountHours());
        }
        if (hoursUpdateCommand.getHoursType() != null) {
            hours.setHoursType(hoursUpdateCommand.getHoursType());
        }
        if (hoursUpdateCommand.getFirefighterId() != null) {
            hours.setFirefighterId(hoursUpdateCommand.getFirefighterId());
        }
        return hoursRepository.save(hours);
    }

    public void saveHours(HoursSaveCommand hoursSaveCommand) {
        if(hoursSaveCommand.getHoursType().equals(WORK)) {
            saveWorkedHours(hoursSaveCommand, 16,  false);
            saveWorkedHours(hoursSaveCommand, 8,  true);
        } else {
            saveWorkedHours(hoursSaveCommand, hoursSaveCommand.getAmountHours(),  false);
        }
    }

    public Hours saveWorkedHours(HoursSaveCommand hoursSaveCommand, int hoursWorked, boolean isNextDay) {
        Hours hours = new Hours();
        if (!isNextDay) {
            hours.setStartDate(hoursSaveCommand.getStartDate());
            hours.setEndDate(hoursSaveCommand.getEndDate());
        } else {
            hours.setStartDate(hoursSaveCommand.getStartDate().plusDays(1));
            hours.setEndDate(hoursSaveCommand.getEndDate().plusDays(1));
        }
        hours.setAmountHours(hoursWorked);
        hours.setHoursType(hoursSaveCommand.getHoursType());
        hours.setFirefighterId(hoursSaveCommand.getFirefighterId());
        hours.setFirefighter(firefighterRepository.getById(hoursSaveCommand.getFirefighterId()));
        return hoursRepository.save(hours);
    }

    public int getAnnualHoursByFirefighterIdAndType(int firefighterId, String hoursType, int year) {
        List<Hours> hoursPerYearList = hoursRepository.getAnnualHoursByTypeAndFirefighter(
                    firefighterId, hoursType, year);
        return hoursPerYearList.stream().mapToInt(Hours::getAmountHours).sum();
    }

    public int getMonthlyHoursByFirefighterIdAndType(int firefighterId, String hoursType, int year, int month) {
        List<Hours> hoursPerMonthList = hoursRepository.getMonthlyHoursByTypeAndFirefighter(
                firefighterId, hoursType, year, month);
        return hoursPerMonthList.stream().mapToInt(Hours::getAmountHours).sum();
    }

    public int getOvertimePerMonth(int firefighterId, int year, int month) throws IOException {
        int workingHoursPerMonth = getMonthlyHoursByFirefighterIdAndType(firefighterId, "WORK", year, month) +
                getMonthlyHoursByFirefighterIdAndType(firefighterId, "EXTRA_WORK", year, month);
        int vacationHoursPerMonth = getMonthlyHoursByFirefighterIdAndType(firefighterId, "VACATION", year, month) +
                getMonthlyHoursByFirefighterIdAndType(firefighterId, "SICK", year, month);
        return workingHoursPerMonth - (getNormaHours(year, month) - vacationHoursPerMonth);
    }

    public int getOvertimePerYear(int firefighterId, int year) throws IOException {
        int workingHoursPerYear = getAnnualHoursByFirefighterIdAndType(firefighterId, "WORK", year) +
                getAnnualHoursByFirefighterIdAndType(firefighterId, "EXTRA_WORK", year) +
                getAnnualHoursByFirefighterIdAndType(firefighterId, "EIGHT", year);
        return workingHoursPerYear - (getNormaHours(year, 0) - getAnnualLegalDayOffHours(firefighterId, year));
    }

    public int getAnnualLegalDayOffHours(int firefighterId, int year) throws IOException {
        List<Hours> vacationHoursPerYearList = hoursRepository.getAnnualHoursByTypeAndFirefighter(
                firefighterId, "VACATION", year);
        List<Hours> sickHoursPerYearList = hoursRepository.getAnnualHoursByTypeAndFirefighter(
                firefighterId, "SICK", year);

        return getLegalNonWorkingHoursInRange(vacationHoursPerYearList) + getLegalNonWorkingHoursInRange(sickHoursPerYearList);
    }

    private int getLegalNonWorkingHoursInRange(List<Hours> hoursList) throws IOException {
        int daysToCount = 0;
        for(Hours hour : hoursList) {
            DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
            String startDate = hour.getStartDate().format(formatter);
            String endDate = hour.getEndDate().format(formatter);
            final String URL_RANGE_FORMAT = "https://isdayoff.ru/api/getdata?date1=%s&date2=%s&cc=ru&covid=1";
            StringBuilder sb = getDaysStringFromIsDayOffService(String.format(URL_RANGE_FORMAT, startDate, endDate));
            daysToCount += StringUtils.countOccurrencesOf(sb.toString(), "0");
        }
        return (daysToCount * 8);
    }
    public void deleteHours(Integer firefighterId, LocalDate startDate) {
        hoursRepository.deleteByFirefighterIdAndStartDate(firefighterId, startDate);
    }

    public int getNormaHours(int year, int month) throws IOException {
        StringBuilder sb;
        if (month != 0) {
            final String URL_FORMAT = "https://isdayoff.ru/api/getdata?year=%d&month=%d&cc=ru&pre=1&covid=1";
            sb = getDaysStringFromIsDayOffService(String.format(URL_FORMAT, year, month));
        } else {
            final String URL_FORMAT = "https://isdayoff.ru/api/getdata?year=%d&cc=ru&pre=1&covid=1";
            sb = getDaysStringFromIsDayOffService(String.format(URL_FORMAT, year));
        }
        System.out.println(sb);
        int workDays = StringUtils.countOccurrencesOf(sb.toString(), "0");
        int daysOff = StringUtils.countOccurrencesOf(sb.toString(), "1");
        int partTimeDays = StringUtils.countOccurrencesOf(sb.toString(), "2");
        int covidWorkDays = StringUtils.countOccurrencesOf(sb.toString(), "4");
        System.out.println("mРабочих: " + workDays);
        System.out.println("mСокращенных: " + partTimeDays);
        System.out.println("mВыходных: " + daysOff);
        System.out.println("mcovid: " + covidWorkDays);
        return (workDays * 8) + (partTimeDays * 7) + (covidWorkDays * 8);
    }

    private StringBuilder getDaysStringFromIsDayOffService (String request) throws IOException {
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while((line = in.readLine()) != null) {
            sb.append(line);
        }
        return sb;
    }
    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate).collect(Collectors.toList());
    }
}

