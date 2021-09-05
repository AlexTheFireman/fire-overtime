package fire.overtime.integration.repositories;

import fire.overtime.OvertimeApplication;
import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.models.Enums.HourType;
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

import static fire.overtime.models.Enums.HourType.WORK;
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
        HoursSaveCommand hoursSaveCommand = givenHoursSaveCommand(firefighter, month, 24, WORK);
        Hours savedHours = hoursService.saveHours(hoursSaveCommand);

        assertEquals(24, hoursRepository.getById(savedHours.getId()).getFactHours());
    }

    @Test
    public void GetHoursByFirefighterIdAndMonthIdAndHoursTypeTest() {
        Firefighter firefighter = givenSavedFirefighter();
        Month month = givenSavedMonth();
        HoursSaveCommand hoursSaveCommand = givenHoursSaveCommand(firefighter, month, 24, WORK);
        hoursService.saveHours(hoursSaveCommand);
        assertNotNull(hoursRepository.getHoursByFirefighterIdAndMonthIdAndHoursType(
                firefighter.getId(), month.getId(), WORK));
    }

    @Test
    public void GetHoursByFirefighterIdAndMonth_YearAndHoursTypeTest() {
        Firefighter firefighter = givenSavedFirefighter();
        Month month = givenSavedMonth();
        HoursSaveCommand hoursSaveCommand = givenHoursSaveCommand(
                firefighter, month, 24, WORK);
        hoursService.saveHours(hoursSaveCommand);
        assertNotNull(hoursRepository.getHoursByFirefighterIdAndMonthIdAndHoursType(
                firefighter.getId(), 2021, WORK));
    }

    @Test
    public void DeleteByFirefighterIdAAndDateTest() {
        Firefighter firefighter = givenSavedFirefighter();
        Month month = givenSavedMonth();
        HoursSaveCommand hoursSaveCommand = givenHoursSaveCommand(
                firefighter, month, 24, WORK);
        hoursService.saveHours(hoursSaveCommand);
        hoursRepository.deleteByFirefighterIdAndDate(firefighter.getId(), DATE);
        assertNull(hoursRepository.getHoursByDateAndFirefighter(DATE, firefighter));
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

    public HoursSaveCommand givenHoursSaveCommand(
            Firefighter firefighter, Month month, int factHours, HourType hoursType){
        HoursSaveCommand hoursSaveCommand = new HoursSaveCommand();
        hoursSaveCommand.setFirefighterId(firefighter.getId());
        hoursSaveCommand.setMonthId(month.getId());
        hoursSaveCommand.setFactHours(factHours);
        hoursSaveCommand.setHoursType(hoursType);
        hoursSaveCommand.setDate(DATE);
        return hoursSaveCommand;
    }
}

