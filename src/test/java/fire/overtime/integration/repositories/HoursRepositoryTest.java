package fire.overtime.integration.repositories;

import fire.overtime.OvertimeApplication;
import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import fire.overtime.models.Month;
import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.repositories.HoursRepository;
import fire.overtime.repositories.MonthRepository;
import fire.overtime.services.HoursService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

//    @Before
//    public void setFireFighter() {
//        Firefighter firefighter = new Firefighter();
//        firefighter.setFirstName("test-name");
//        firefighter.setLastName("test-lastname");
//        firefighter.setPatronymic("test-patronimic");
//        firefighter.setPosition("test-position");
//        firefighterRepository.save(firefighter);
//    }

//    @Before
//    public void setMonth() {
//        Month month = new Month();
//        month.setMonthType("June");
//        month.setNormaHours(160);
//        month.setYear(2020);
//        monthRepository.save(month);
//    }

//    @Before
//    public void setHours() {
//        LocalDate date = LocalDate.of(2021, 8, 21);
//        Hours hours = hoursService.setHoursWithType(date, firefighterRepository.getById(1), monthRepository.getById(1), 24,
//                "WORK");
//        hoursRepository.save(hours);
//    }

    @Test
    public void addHoursWithDateForFirefighter() {
        //Given
        Firefighter firefighter = new Firefighter();
        firefighter.setFirstName("test-name");
        firefighter.setLastName("test-lastname");
        firefighter.setPatronymic("test-patronimic");
        firefighter.setPosition("test-position");
        Firefighter savedFirefighter = firefighterRepository.save(firefighter);

        Month month = new Month();
        month.setMonthType("June");
        month.setNormaHours(160);
        month.setYear(2020);
        Month savedMonth = monthRepository.save(month);

        LocalDate date = LocalDate.of(2021, 8, 21);
        Hours hours = hoursService.setHoursWithType(date, savedFirefighter, savedMonth, 24,
                "WORK");
        hoursRepository.save(hours);
        //When


        //Then
        assertNotNull(firefighterRepository.findById(1));
        assertNotNull(monthRepository.findById(1));
        assertNotNull(hoursRepository.findById(1));
//
    }


    @Test
    public void getHoursByFirefighterIdAndType() {
        Firefighter firefighter = new Firefighter();
        firefighter.setFirstName("test-name");
        firefighter.setLastName("test-lastname");
        firefighter.setPatronymic("test-patronimic");
        firefighter.setPosition("test-position");
        Firefighter savedFirefighter = firefighterRepository.save(firefighter);

        Month month = new Month();
        month.setMonthType("June");
        month.setNormaHours(160);
        month.setYear(2020);
        Month savedMonth = monthRepository.save(month);

        LocalDate date = LocalDate.of(2021, 8, 21);
        Hours hours = hoursService.setHoursWithType(date, savedFirefighter, savedMonth, 24,
                "WORK");
        hoursRepository.save(hours);

        assertNotNull(hoursRepository.getHoursByFirefighterAndHoursType(firefighter, "WORK"));
//        System.out.println(firefighterRepository.getById(1));
//        List<Hours> listHours = hoursRepository.getHoursByFirefighterAndHoursType(
//                firefighter, "WORK");
//        System.out.println(listHours);
    }
}

