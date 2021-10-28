//package fire.overtime.integration.services;
//
//import fire.overtime.OvertimeApplication;
//import fire.overtime.services.MonthService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.*;
//
//import javax.transaction.Transactional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = OvertimeApplication.class)
//@Transactional
//public class MonthServiceTests {
//
//    @Autowired
//    MonthService monthService;
//
//    @Test
//    public void getLawNormWorkingHoursByYearTest(){
//        givenSavedMonths();
//        assertEquals(1972, monthService.getLawNormWorkingHoursByYear(2021));
//    }
//
//    public void givenSavedMonths() {
//        monthService.setMonthsByYear(2021, monthService.setNormWorkingHoursForMonths());
//    }
//}
