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

import static java.util.Calendar.YEAR;
import static org.hibernate.loader.Loader.SELECT;

@Repository
@Transactional
public interface HoursRepository extends JpaRepository<Hours, Integer> {

//    @Query("select h from Hours h where h.firefighter.id=:firefighterId and h.month.id= :monthYearId")
//    List<Hours> getHours(@Param("firefighterId") Integer firefighterId, @Param("monthYearId") Integer monthYearId);

    @Query("select h from Hours h where year(h.startDate) = :year")
    List<Hours> getHoursByFirefighterAndYearAndType(
            @Param("firefighterId")Integer firefighterId,
            @Param("hoursType") HourType hoursType,
            @Param("year") Integer year);

//    List<Hours> getHoursByFirefighterIdAndMonth_YearAndHoursType(
//            Integer firefighterId, int year, HourType hoursType);
//
//    void deleteByFirefighterIdAndStartDate(Integer firefighterId, LocalDate startDate);
//
//    Hours getHoursByStartDateAndFirefighterId(LocalDate startDate, Integer firefighterId);
//
//    List<Hours> getHoursByFirefighterId(Integer firefighterId);

}
