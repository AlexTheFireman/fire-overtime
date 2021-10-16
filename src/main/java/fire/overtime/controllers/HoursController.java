package fire.overtime.controllers;


import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.commands.HoursUpdateCommand;
import fire.overtime.dto.HoursDto;
import fire.overtime.models.Enums.HourType;
import fire.overtime.models.Hours;
import fire.overtime.repositories.HoursRepository;
import fire.overtime.services.HoursService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hours")
public class HoursController {

    @Autowired
    HoursService hoursService;
    @Autowired
    HoursRepository hoursRepository;

    private ModelMapper modelMapper;

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

    @GetMapping(value = "/1", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public int getYearOvertime() throws IOException {
        return hoursService.getYearNormaHours(2021);
    }

    @GetMapping(value = "/all", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<HoursDto> getDays() throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        List<Hours> hours = hoursRepository.getHours(1, 1);
        System.out.println(hours.get(0));
        Type listType = new TypeToken<List<HoursDto>>(){}.getType();
        List<HoursDto> hoursDtoList = modelMapper.map(hours,listType);
        return hoursDtoList;
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

