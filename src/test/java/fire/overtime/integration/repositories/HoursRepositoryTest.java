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

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OvertimeApplication.class)
@Transactional
public class HoursRepositoryTest {

    @Autowired
    HoursRepository hoursRepository;
    @Autowired
    FirefighterRepository firefighterRepository;
    @Autowired
    MonthRepository monthRepository;

    @Test
    public void addHoursWithDateForFirefighter() {
        //Given
        Firefighter firefighter = new Firefighter();
        firefighter.setFirstName("test-name");
        firefighter.setLastName("test-lastname");
        firefighter.setPatronymic("test-patronimic");
        firefighter.setPosition("test-position");

        Month month = new Month();
        month.setMonthType("June");
        month.setNormaHours(160);
        month.setYear(2020);

        LocalDate date = LocalDate.of(2021, 8, 21);

        Hours hours = new Hours();
        hoursRepository.setHoursWithType(date, firefighter, month, 24, "WORK");

        //When
        Firefighter savedFighter = firefighterRepository.save(firefighter);
        Month savedMonth = monthRepository.save(month);
        Hours savedHours = hoursRepository.save(hours);


        //Then
        assertNotNull(firefighterRepository.findById(savedFighter.getFirefighterId()));
        assertNotNull(monthRepository.findById(savedMonth.getMonthYearId()));
        assertNotNull(hoursRepository.findById(savedHours.getHours_id()));
    }
}

