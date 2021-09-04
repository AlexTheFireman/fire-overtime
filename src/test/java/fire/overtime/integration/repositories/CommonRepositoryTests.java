package fire.overtime.integration.repositories;

import fire.overtime.OvertimeApplication;
import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import fire.overtime.models.Month;
import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.repositories.HoursRepository;
import fire.overtime.repositories.MonthRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvertimeApplication.class)
@Transactional
class CommonRepositoryTest {

    @Autowired
    FirefighterRepository firefighterRepository;
    @Autowired
    MonthRepository monthRepository;
    @Autowired
    HoursRepository hoursRepository;

    @Test
    public void addFighter_success() {
        //Given
        Firefighter firefighter = new Firefighter();
        firefighter.setFirstName("test-name");
        firefighter.setLastName("test-lastname");
        firefighter.setPatronymic("P1");
        firefighter.setPosition("P1");

        //When
        Firefighter savedFighter = firefighterRepository.save(firefighter);

        //Then
        assertNotNull(firefighterRepository.findById(savedFighter.getFirefighterId()));
    }

    @Test
    public void addFighterWithMonth_success() {
        //Given
        Month month = new Month();
        month.setMonthName("June");
        month.setNormaHours(12);
        month.setNormaHours(8);
        month.setMonthYearId(2020);
        Month savedMonth = monthRepository.save(month);
        Set<Month> months = new HashSet<>();
        months.add(savedMonth);

        Firefighter firefighter = new Firefighter();
        firefighter.setFirstName("test-name");
        firefighter.setLastName("test-lastname");
        firefighter.setPatronymic("P1");
        firefighter.setPosition("P1");
        Firefighter savedFighter = firefighterRepository.save(firefighter);

        Hours hours = new Hours();
        hours.setDate(LocalDate.now());
        hours.setFactHours(12);
        hours.setFirefighter(savedFighter);
        hours.setMonth(savedMonth);

        //When
        hoursRepository.save(hours);

        //Then
        Optional<Firefighter> fetchedFighter = firefighterRepository.findById(savedFighter.getFirefighterId());
        assertTrue(fetchedFighter.isPresent());
        assertNotNull(fetchedFighter.get());
        assertNotNull(fetchedFighter.get().getHours());
    }
}

