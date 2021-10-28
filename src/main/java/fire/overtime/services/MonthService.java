//package fire.overtime.services;
//
//import fire.overtime.models.Month;
//import fire.overtime.repositories.MonthRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Transactional
//public class MonthService {
//
//    @Autowired
//    private MonthRepository monthRepository;
//
//    public Map<String, Integer> setNormWorkingHoursForMonths() {
//        Map<String, Integer> map = new HashMap<String, Integer>();
//        map.put("Январь", 120);
//        map.put("Февраль", 151);
//        map.put("Март", 176);
//        map.put("Апрель", 175);
//        map.put("Май", 152);
//        map.put("Июнь", 167);
//        map.put("Июль", 176);
//        map.put("Август", 176);
//        map.put("Сентябрь", 176);
//        map.put("Октябрь", 168);
//        map.put("Ноябрь", 159);
//        map.put("Декабрь", 176);
//        return map;
//    }
//
//    public void setMonth(String monthName, int normWorkingHours, int year) {
//        Month month = new Month();
//        month.setMonthName(monthName);
//        month.setNormaHours(normWorkingHours);
//        month.setYear(year);
//        monthRepository.save(month);
//    }
//
//    public void setMonthsByYear(int year, Map<String, Integer> normHoursForMonthsMap) {
//        normHoursForMonthsMap.entrySet()
//                .forEach(n -> setMonth(n.getKey(), n.getValue(), year));
//    }
//
//    public int getLawNormWorkingHoursByYear(int year) {
//        List<Month> monthsList = monthRepository.getMonthsByYear(year);
//        return monthsList.stream().mapToInt(Month::getNormaHours).sum();
//    }
//}

