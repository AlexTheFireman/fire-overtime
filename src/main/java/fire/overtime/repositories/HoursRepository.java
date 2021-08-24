package fire.overtime.repositories;

import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoursRepository extends JpaRepository<Hours, Integer> {

    @Query("select h from Hours h where h.firefighter.id=:firefighterId and h.month.id= :monthYearId")
    List<Hours> getHours(@Param("firefighterId") Integer firefighterId, @Param("monthYearId") Integer monthYearId);

    void deleteByFirefighterId(Integer firefighterId);

    Hours getByHoursId(int hoursId);

    List<Hours> getHoursByFirefighterAndHoursType(Firefighter firefighter, String hoursType);

    Hours getByHoursTypeAndDate(String hoursType, LocalDate date);
}
