package fire.overtime.controllers;

import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import fire.overtime.models.Month;
import fire.overtime.services.HoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class HoursController {

    @Autowired
    HoursService hoursService;

//    @PostMapping(value = "/hours/add")
//    public void addHoursWithFirefighterAndMonthAndType(@RequestBody LocalDate date, Firefighter firefighter,
//                                                       Month month, Integer factHours, String hoursType){
//        hoursService.setHoursWithFirefighterAndMonthAndType(date, firefighter, month, factHours, hoursType);
//    }

    @PostMapping(value = "/hours/get/{firefighterId}")
    public void getHoursPerPeriodByType(@PathVariable int firefighterId, @RequestBody int periodId, String hoursType){
        hoursService.getHoursPerPeriodByType(firefighterId, periodId, hoursType);
    }
//    @PostMapping(value = "/get/{fileName}", produces = "application/json",
//            consumes = "application/json")
//    public String getDataFromFile(@PathVariable String fileName, @RequestBody String params) throws IOException {
//        return filterManager.filteringByAllSelectedFilters(fileName, params);
//    }
//    getHoursPerPeriodByType(int firefighterId, int periodId, String hoursType)
}

