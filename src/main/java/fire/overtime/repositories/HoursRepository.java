package fire.overtime.repositories;

import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import fire.overtime.models.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HoursRepository extends JpaRepository<Hours, Integer> {
    int setHoursWithType(LocalDate date,
                         Firefighter firefighterId,
                         Month month,
                         int factHours,
                         String hoursType);

    int getHoursByDate(Integer firefighterId, Integer monthYearId, LocalDate date);

    void deleteHours(Integer firefighterId, Integer monthYearId, LocalDate date);

    int getOvertimeByMonth(Integer firefighterId, Integer monthYearId);

    int getOvertimeByYear(Integer firefighterId, int year);
}
