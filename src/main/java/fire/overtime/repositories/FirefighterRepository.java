package fire.overtime.repositories;

import fire.overtime.models.Firefighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirefighterRepository extends JpaRepository<Firefighter, Integer> {

}

