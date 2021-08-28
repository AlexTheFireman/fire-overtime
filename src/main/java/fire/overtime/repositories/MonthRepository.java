package fire.overtime.repositories;

import fire.overtime.models.Firefighter;
import fire.overtime.models.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthRepository extends JpaRepository<Month, Integer> {
    Firefighter findByYear(Integer year);

    List<Month> getMonthsByYear(int year);


}
