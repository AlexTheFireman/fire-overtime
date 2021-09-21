package fire.overtime.controllers;

import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.commands.HoursUpdateCommand;
import fire.overtime.models.Enums.HourType;
import fire.overtime.models.Hours;
import fire.overtime.services.HoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;

@RestController
@RequestMapping("/hours")
public class HoursController {

    @Autowired
    HoursService hoursService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Hours save(@RequestBody HoursSaveCommand hoursSaveCommand) {
        //можно добавить валидацию чуть позже
        return hoursService.saveHours(hoursSaveCommand);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Hours update(@RequestBody HoursUpdateCommand hoursUpdateCommand) {
        //можно добавить валидацию чуть позже
        return hoursService.updateHours(hoursUpdateCommand);
    }

    @GetMapping(value = "/firefighter/{firefighterId}/periods/{periodId}/{hoursType}")
    @ResponseStatus(HttpStatus.OK)
    public int getHoursPerPeriodByType(@PathVariable Integer firefighterId, @PathVariable Integer periodId, HourType hoursType) {
        return hoursService.getHoursPerPeriodByType(firefighterId, periodId, hoursType);
    }

    @GetMapping(value = "/firefighter/{firefighterId}/overtime/month/{periodId}")
    @ResponseStatus(HttpStatus.OK)
    public int getMonthOvertime(@PathVariable Integer firefighterId, @PathVariable Integer periodId) {
        return hoursService.getOvertimePerMonth(firefighterId, periodId);
    }

    @GetMapping(value = "/overtime", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public int getYearOvertime(@PathVariable Integer firefighterId, @PathVariable Integer periodId) throws IOException {

        return hoursService.getOvertimePerYear(firefighterId, periodId);
    }

    @DeleteMapping(value = "/firefighter/{firefighterId}/{date}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHours(@PathVariable Integer firefighterId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        hoursService.deleteHours(firefighterId, date);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public int getYearOvertime2() {
        return 13;
    }
}

