package fire.overtime.controllers;


import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.commands.HoursUpdateCommand;
import fire.overtime.dto.HoursDto;
import fire.overtime.models.Enums.HourType;
import fire.overtime.models.Hours;
import fire.overtime.repositories.HoursRepository;
import fire.overtime.services.HoursService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

import static fire.overtime.models.Enums.HourType.WORK;

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
    public void save(@RequestBody HoursSaveCommand hoursSaveCommand) {
        //можно добавить валидацию чуть позже
        hoursService.saveHours(hoursSaveCommand);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Hours update(@RequestBody HoursUpdateCommand hoursUpdateCommand) {
        //можно добавить валидацию чуть позже
        return hoursService.updateHours(hoursUpdateCommand);
    }

    @GetMapping(value = "/all/firefighter/{firefighterId}/year/{year}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<HoursDto> getAllYearHours(@PathVariable Integer firefighterId, @PathVariable Integer year) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        List<Hours> hours = hoursRepository.getHours(firefighterId, year);
        hours.removeIf(h -> (h.getHoursType() == WORK && h.getAmountHours() == 8));
        Type listType = new TypeToken<List<HoursDto>>(){}.getType();
        hours.forEach(System.out::println);
        List<HoursDto> hoursDtoList = modelMapper.map(hours,listType);
        return hoursDtoList;
    }

    @GetMapping(value = "/firefighter/{firefighterId}/overtime/{year}")
    @ResponseStatus(HttpStatus.OK)
    public int getAnnualOvertime(@PathVariable Integer firefighterId, @PathVariable Integer year) throws IOException {
        return hoursService.getOvertimePerYear(firefighterId, year);
    }

    @GetMapping(value = "/firefighter/{firefighterId}/overtime/{year}/month/{month}")
    @ResponseStatus(HttpStatus.OK)
    public int getMonthlyOvertime(@PathVariable Integer firefighterId, @PathVariable Integer year, @PathVariable Integer month) throws IOException {
        return hoursService.getOvertimePerMonth(firefighterId, year, month);
    }

    @DeleteMapping(value = "/firefighter/{firefighterId}/{date}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHours(@PathVariable Integer firefighterId, @PathVariable LocalDate date) {
        hoursService.deleteHours(firefighterId, date);
    }
}

