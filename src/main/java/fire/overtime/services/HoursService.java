package fire.overtime.services;

import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.commands.HoursUpdateCommand;
import fire.overtime.dto.HoursDto;
import fire.overtime.models.Enums.HourType;
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
import java.util.List;
import java.util.Optional;

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
        int normWorkingHoursPerMonth = getMonthNormaHours(year, month);
        return workingHoursPerMonth - (normWorkingHoursPerMonth - vacationHoursPerMonth);
    }

    public int getOvertimePerYear(int firefighterId, int year) throws IOException {
        int workingHoursPerYear = getAnnualHoursByFirefighterIdAndType(firefighterId, "WORK", year) +
                getAnnualHoursByFirefighterIdAndType(firefighterId, "EXTRA_WORK", year) +
                getAnnualHoursByFirefighterIdAndType(firefighterId, "EIGHT", year);
        System.out.println("Work hours: " + workingHoursPerYear);
        int vacationHoursPerYear = getAnnualHoursByFirefighterIdAndType(firefighterId, "VACATION", year) +
                getAnnualHoursByFirefighterIdAndType(firefighterId, "SICK", year);
        int normWorkingHoursPerYear = getAnnualNormaHours(year);
        return workingHoursPerYear - (normWorkingHoursPerYear - vacationHoursPerYear);
    }

    public void deleteHours(Integer firefighterId, LocalDate startDate) {
        hoursRepository.deleteByFirefighterIdAndStartDate(firefighterId, startDate);
    }

    public int getAnnualNormaHours(int year) throws IOException {
        final String URL_FORMAT = "https://isdayoff.ru/api/getdata?year=%d&cc=ru&pre=1&&covid=1";
        final String request = String.format(URL_FORMAT, year);
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

        int daysOff = StringUtils.countOccurrencesOf(sb.toString(), "1");
        int workDays = StringUtils.countOccurrencesOf(sb.toString(), "0");
        int partTimeDays = StringUtils.countOccurrencesOf(sb.toString(), "2");
        int covidWorkDays = StringUtils.countOccurrencesOf(sb.toString(), "4");
        System.out.println(sb);
        System.out.println("Рабочих: " + workDays);
        System.out.println("Сокращенных: " + partTimeDays);
        System.out.println("Выходных: " + daysOff);
        System.out.println("Ковидных : " + covidWorkDays);
        return (workDays * 8) + (partTimeDays * 7);
    }

    public int getMonthNormaHours(int year, int month) throws IOException {
        final String URL_FORMAT = "https://isdayoff.ru/api/getdata?year=%d&month=%d";
        final String request = String.format(URL_FORMAT, year, month);
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

        int daysOff = StringUtils.countOccurrencesOf(sb.toString(), "1");
        int workDays = StringUtils.countOccurrencesOf(sb.toString(), "0");
        int partTimeDays = StringUtils.countOccurrencesOf(sb.toString(), "2");
        int covidWorkDays = StringUtils.countOccurrencesOf(sb.toString(), "4");

        return (workDays * 8) + (partTimeDays * 7);
    }
}

