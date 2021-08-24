//package fire.overtime.services.implimentation;
//
//import fire.overtime.models.Firefighter;
//import fire.overtime.models.Hours;
//import fire.overtime.models.Month;
//import fire.overtime.services.HoursService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDate;
//
//public class HoursServiceImpl implements HoursService {
//
//    @Autowired
//    Hours hours;
//
//    @Override
//    public void setHoursWithType(LocalDate date, Firefighter firefighter, Month month, int factHours, String hoursType) {
//        hours.setDate(date);
//        hours.setFactHours(factHours);
//        hours.setHoursType(hoursType);
//        hours.setFirefighter(firefighter);
//        hours.setMonth(month);
//    }
//
//    @Override
//    public int getHoursByDate(Integer firefighterId, Integer monthYearId, LocalDate date) {
//        return 0;
//    }
//
//    @Override
//    public void deleteHours(Integer firefighterId, Integer monthYearId, LocalDate date) {    }
//
//    @Override
//    public int getOvertimeByMonth(Integer firefighterId, Integer monthYearId) {
//        return 0;
//    }
//
//    @Override
//    public int getOvertimeByYear(Integer firefighterId, int year) {
//        return 0;
//    }
//}
//
