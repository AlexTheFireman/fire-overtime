package fire.overtime.controllers;

import fire.overtime.commands.FirefighterSaveCommand;
import fire.overtime.commands.FirefighterUpdateCommand;
import fire.overtime.models.Firefighter;
import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.services.FirefighterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/firefighter")
public class FirefighterController {

    FirefighterService firefighterService;

    FirefighterRepository firefighterRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Firefighter save(@RequestBody FirefighterSaveCommand firefighterSaveCommand) {
        //можно добавить валидацию чуть позже
        return firefighterService.saveFirefighter(firefighterSaveCommand);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Firefighter updateFirefighter(@RequestBody FirefighterUpdateCommand firefighterUpdateCommand) {
        //можно добавить валидацию чуть позже
        return firefighterService.updateFirefighter(firefighterUpdateCommand);
    }

    @DeleteMapping(value = "/delete/{firefighterId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFirefighter(@PathVariable Integer firefighterId) {
        firefighterRepository.deleteById(firefighterId);
    }
}

