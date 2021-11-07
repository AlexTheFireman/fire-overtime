package fire.overtime.repositories;

import fire.overtime.models.Enums.HourType;
import fire.overtime.models.Hours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface HoursRepository extends JpaRepository<Hours, Integer> {

    @Query(value = "select * from Hours h where h.firefighter_Id=:firefighterId and year(start_date)=:year", nativeQuery = true)
    List<Hours> getHours(@Param("firefighterId") Integer firefighterId, @Param("year") Integer year);

    @Query(value = "select * from Hours h where h.firefighter_Id=:firefighterId and h.hours_type=:hoursType and year(start_date)=:year", nativeQuery = true)
    List<Hours> getAnnualHoursByTypeAndFirefighter(
            @Param("firefighterId")Integer firefighterId,
            @Param("hoursType") String hoursType,
            @Param("year") Integer year);

    @Query(value = "select * from Hours h where h.firefighter_Id=:firefighterId and h.hours_type=:hoursType and year(start_date)=:year and month(start_date)=:month", nativeQuery = true)
    List<Hours> getMonthlyHoursByTypeAndFirefighter(
            @Param("firefighterId")Integer firefighterId,
            @Param("hoursType") String hoursType,
            @Param("year") Integer year,
            @Param("month") Integer month);

    void deleteByFirefighterIdAndStartDate(Integer firefighterId, LocalDate startDate);
}
