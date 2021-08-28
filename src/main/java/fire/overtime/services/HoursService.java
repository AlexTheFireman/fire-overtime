package fire.overtime.services;

import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import fire.overtime.models.Month;
import fire.overtime.repositories.HoursRepository;
import fire.overtime.repositories.MonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HoursService {

    @Autowired
    private HoursRepository hoursRepository;
    @Autowired
    private MonthRepository monthRepository;
    @Autowired
    private MonthService monthService;

    @Autowired
    public HoursService(HoursRepository hoursRepository) {
        this.hoursRepository = hoursRepository;
    }

    public void updateHoursById(int hourIdForUpdate, LocalDate date, Firefighter firefighter,
                                Month month, Integer factHours, String hoursType) {
        Optional<Hours> byId = hoursRepository.findById(hourIdForUpdate);
        if (!byId.isPresent())
            throw new RuntimeException(String.format("Hours with id %d should be present", hourIdForUpdate));
        Hours hours = byId.get();
        hours.setDate(date);
        hours.setFactHours(factHours);
        hours.setHoursType(hoursType);
        hours.setFirefighter(firefighter);
        hours.setMonth(month);
    }

    public void setHoursWithFirefighterAndMonthAndType(LocalDate date, Firefighter firefighter,
                                Month month, Integer factHours, String hoursType) {
        Hours hours = new Hours();
        hours.setDate(date);
        hours.setFactHours(factHours);
        hours.setHoursType(hoursType);
        hours.setFirefighter(firefighter);
        hours.setMonth(month);
        hoursRepository.save(hours);
    }

    int getHoursPerPeriodByType(int firefighterId, int periodId, String hoursType) {
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
                "WORK");
        int vacationHoursPerMonth = getHoursPerPeriodByType(firefighterId, monthYearId, "VACATION");
        Integer normWorkingHoursPerMonth = monthRepository.getById(monthYearId).getNormaHours();
        return workingHoursPerMonth - (normWorkingHoursPerMonth - vacationHoursPerMonth);
    }

    public int getOvertimePerYear(int firefighterId, Integer year) {
        int workingHoursPerYear = getHoursPerPeriodByType(firefighterId, year,
                "WORK");
        int vacationHoursPerYear = getHoursPerPeriodByType(firefighterId, year, "VACATION");
        int normWorkingHoursPerYear = monthService.getLawNormWorkingHoursByYear(year);
        return workingHoursPerYear - (normWorkingHoursPerYear - vacationHoursPerYear);
    }
}

