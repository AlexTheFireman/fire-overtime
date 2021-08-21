package fire.overtime.services;

import fire.overtime.models.Firefighter;
import fire.overtime.models.Month;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface HoursService {

    void setHoursWithType(LocalDate date,
                         Firefighter firefighter,
                         Month month,
                         int factHours,
                         String hoursType);

    int getHoursByDate(Integer firefighterId, Integer monthYearId, LocalDate date);

    void deleteHours(Integer firefighterId, Integer monthYearId, LocalDate date);

    int getOvertimeByMonth(Integer firefighterId, Integer monthYearId);

    int getOvertimeByYear(Integer firefighterId, int year);

}

