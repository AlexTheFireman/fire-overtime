package fire.overtime.services;

import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.commands.HoursUpdateCommand;
import fire.overtime.models.Enums.HourType;
import fire.overtime.models.Hours;
import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.repositories.HoursRepository;
import fire.overtime.repositories.MonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static fire.overtime.models.Enums.HourType.VACATION;
import static fire.overtime.models.Enums.HourType.WORK;

@Service
@Transactional
public class HoursService {

    @Autowired
    private HoursRepository hoursRepository;
    @Autowired
    private MonthRepository monthRepository;
    @Autowired
    FirefighterRepository firefighterRepository;
    @Autowired
    private MonthService monthService;

    @Autowired
    public HoursService(HoursRepository hoursRepository) {
        this.hoursRepository = hoursRepository;
    }

    public Hours updateHours(HoursUpdateCommand hoursUpdateCommand) {
        Optional<Hours> byId = hoursRepository.findById(hoursUpdateCommand.getHourIdForUpdate());
        if (!byId.isPresent())
            throw new RuntimeException(String.format("Hours with id %d should be present", hoursUpdateCommand.getHourIdForUpdate()));

        Hours hours = byId.get();
        if (hoursUpdateCommand.getDate() != null) {
            hours.setDate(hoursUpdateCommand.getDate());
        }

        if (hoursUpdateCommand.getFactHours() > 0) {
            hours.setFactHours(hoursUpdateCommand.getFactHours());
        }

        if (hoursUpdateCommand.getHoursType() != null) {
            hours.setHoursType(hoursUpdateCommand.getHoursType());
        }

        if (hoursUpdateCommand.getFirefighterId() != null) {
            hours.setFirefighter(firefighterRepository.getById(hoursUpdateCommand.getFirefighterId()));
        }

        if (hoursUpdateCommand.getMonthId() != null) {
            hours.setMonth(monthRepository.getById(hoursUpdateCommand.getMonthId()));
        }

        return hoursRepository.save(hours);
    }

    public Hours saveHours(HoursSaveCommand hoursSaveCommand) {
        Hours hours = new Hours();
        hours.setDate(hoursSaveCommand.getDate());
        hours.setFactHours(hoursSaveCommand.getFactHours());
        hours.setHoursType(hoursSaveCommand.getHoursType());
        hours.setFirefighter(firefighterRepository.getById(hoursSaveCommand.getFirefighterId()));
        hours.setFirefighterId(hoursSaveCommand.getFirefighterId());
        hours.setMonth(monthRepository.getById(hoursSaveCommand.getMonthId()));

        return hoursRepository.save(hours);
    }

    public int getHoursPerPeriodByType(int firefighterId, int periodId, HourType hoursType) {
        List<Hours> hoursPerPeriodList;
        if (periodId < 2000) {
            hoursPerPeriodList = hoursRepository.getHoursByFirefighterIdAndMonthIdAndHoursType(
                    firefighterId, periodId, hoursType);
        } else {
            hoursPerPeriodList = hoursRepository.getHoursByFirefighterIdAndMonth_YearAndHoursType(
                    firefighterId, periodId, hoursType);
        }

        return hoursPerPeriodList.stream().mapToInt(Hours::getFactHours).sum();
    }

    public int getOvertimePerMonth(int firefighterId, int monthYearId) {
        int workingHoursPerMonth = getHoursPerPeriodByType(firefighterId, monthYearId,
                WORK);
        int vacationHoursPerMonth = getHoursPerPeriodByType(firefighterId, monthYearId, VACATION);
        Integer normWorkingHoursPerMonth = monthRepository.getById(monthYearId).getNormaHours();
        return workingHoursPerMonth - (normWorkingHoursPerMonth - vacationHoursPerMonth);
    }

    public int getOvertimePerYear(int firefighterId, Integer year) {
        int workingHoursPerYear = getHoursPerPeriodByType(firefighterId, year, WORK);
        int vacationHoursPerYear = getHoursPerPeriodByType(firefighterId, year, VACATION);
        int normWorkingHoursPerYear = monthService.getLawNormWorkingHoursByYear(year);
        return workingHoursPerYear - (normWorkingHoursPerYear - vacationHoursPerYear);
    }

    public void deleteHours(Integer firefighterId, LocalDate date) {
        hoursRepository.deleteByFirefighterIdAndDate(firefighterId, date);
    }
}

