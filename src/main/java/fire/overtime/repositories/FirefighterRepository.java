package fire.overtime.repositories;

import fire.overtime.models.Firefighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FirefighterRepository extends JpaRepository<Firefighter, Integer> {

    Firefighter findByLoginAndPassword(String login, String password);

    Firefighter getByLogin (String login);
}

