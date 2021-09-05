package fire.overtime.controllers;

import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.commands.HoursUpdateCommand;
import fire.overtime.models.Enums.HourType;
import fire.overtime.models.Hours;
import fire.overtime.services.HoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}

