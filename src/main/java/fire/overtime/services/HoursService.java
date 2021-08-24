package fire.overtime.services;

import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import fire.overtime.models.Month;
import fire.overtime.repositories.HoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HoursService {

    private HoursRepository hoursRepository;

    @Autowired
    public HoursService(HoursRepository hoursRepository) {
        this.hoursRepository = hoursRepository;
    }

    public void updateHoursById(Integer hourIdForUpdate, LocalDate date, Firefighter firefighter,
                                Month month, int factHours, String hoursType) {
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

    public Hours setHoursWithType(LocalDate date, Firefighter firefighter,
                                Month month, int factHours, String hoursType) {
        Hours hours = new Hours();
        hours.setDate(date);
        hours.setFactHours(factHours);
        hours.setHoursType(hoursType);
        hours.setFirefighter(firefighter);
        hours.setMonth(month);
        return hours;
    }

//    List<Hours> getFactHoursByFirefighterIdAndMonthIdAndHoursType(Integer firefighterId, Integer monthYearId){
//
//    };
    int getHoursByDate(Integer firefighterId, Integer monthYearId, LocalDate date) {
        return 0;
    }

    void deleteHours(Integer firefighterId, Integer monthYearId, LocalDate date) {
    }

    int getOvertimeByMonth(Integer firefighterId, Integer monthYearId) {
        return 0;
    }

    int getOvertimeByYear(Integer firefighterId, int year) {
        return 0;
    }

}

