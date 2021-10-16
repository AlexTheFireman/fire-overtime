package fire.overtime.controllers;

import fire.overtime.commands.FirefighterSaveCommand;
import fire.overtime.commands.FirefighterUpdateCommand;
import fire.overtime.models.Firefighter;
import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.services.FirefighterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/firefighter")
public class FirefighterController {

    FirefighterService firefighterService;

    @Autowired
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

    @DeleteMapping(value = "/{firefighterId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFirefighter(@PathVariable Integer firefighterId) {
        firefighterRepository.deleteById(firefighterId);
    }

    @GetMapping(value = "/{firefighterId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void getFirefighter(@PathVariable Integer firefighterId) {
        firefighterRepository.getById(firefighterId);
    }

    @GetMapping(value = "/all", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public String getAllFirefighters() throws IOException {
//        return firefighterRepository.findAll();
        return "Novikov";
    }
}

