package fire.overtime.repositories;

import fire.overtime.models.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MonthRepository extends JpaRepository<Month, Integer> {

    List<Month> getMonthsByYear(int year);

}
