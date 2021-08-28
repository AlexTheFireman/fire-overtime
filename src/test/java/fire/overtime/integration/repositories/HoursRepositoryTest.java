package fire.overtime.integration.repositories;

import fire.overtime.OvertimeApplication;
import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import fire.overtime.models.Month;
import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.repositories.HoursRepository;
import fire.overtime.repositories.MonthRepository;
import fire.overtime.services.HoursService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    @Autowired
    HoursService hoursService;

    private static final LocalDate DATE = LocalDate.of(2021, 8, 21);

    @Test
    public void AddHoursWithDateForFirefighterTest() {
        Firefighter firefighter = givenSavedFirefighter();
        Month month = givenSavedMonth();
        hoursService.setHoursWithFirefighterAndMonthAndType(DATE, firefighter, month, 24,
                "WORK");
        assertEquals(24, hoursRepository.getById(1).getFactHours());
    }

    @Test
    public void GetHoursByFirefighterIdAndMonthIdAndHoursTypeTest() {
        Firefighter firefighter = givenSavedFirefighter();
        Month month = givenSavedMonth();
        hoursService.setHoursWithFirefighterAndMonthAndType(DATE, firefighter, month, 24,
                "WORK");
        assertNotNull(hoursRepository.getHoursByFirefighterIdAndMonthIdAndHoursType(
                firefighter.getFirefighterId(), month.getMonthYearId(), "WORK"));
    }

    @Test
    public void GetHoursByFirefighterIdAndMonth_YearAndHoursTypeTest() {
        Firefighter firefighter = givenSavedFirefighter();
        Month month = givenSavedMonth();
        hoursService.setHoursWithFirefighterAndMonthAndType(DATE, firefighter, month, 24,
                "WORK");
        assertNotNull(hoursRepository.getHoursByFirefighterIdAndMonthIdAndHoursType(
                firefighter.getFirefighterId(), 2021,"WORK"));
    }

    @Test
    public void DeleteByFirefighterIdAAndDateTest() {
        Firefighter firefighter = givenSavedFirefighter();
        Month month = givenSavedMonth();
        hoursService.setHoursWithFirefighterAndMonthAndType(DATE, firefighter, month, 24,
                "WORK");
        hoursRepository.deleteByFirefighterIdAndDate(firefighter.getFirefighterId(), DATE);
        assertNull(hoursRepository.getHoursByDate(DATE));
    }


    public Firefighter givenSavedFirefighter() {
        Firefighter firefighter = new Firefighter();
        firefighter.setFirstName("test-name");
        firefighter.setLastName("test-lastname");
        firefighter.setPatronymic("test-patronimic");
        firefighter.setPosition("test-position");
        return firefighterRepository.save(firefighter);
    }

    public Month givenSavedMonth() {
        Month month = new Month();
        month.setMonthName("June");
        month.setNormaHours(160);
        month.setYear(2021);
        return monthRepository.save(month);
    }
}

