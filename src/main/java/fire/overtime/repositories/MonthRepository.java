package fire.overtime.repositories;

import fire.overtime.models.Firefighter;
import fire.overtime.models.Month;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthRepository extends JpaRepository<Month, Integer> {
    Firefighter findByYear(Integer year);

}
