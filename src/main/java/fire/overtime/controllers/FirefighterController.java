package fire.overtime.controllers;

import fire.overtime.dto.FirefighterDto;
import fire.overtime.models.Firefighter;
import fire.overtime.repositories.FirefighterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/firefighter")
public class FirefighterController {

    @Autowired
    FirefighterRepository firefighterRepository;

    @GetMapping(value = "/{username}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public FirefighterDto getFirefighter(@PathVariable String username)
    {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeLogin = encoder.encodeToString(username.getBytes());
        Firefighter firefighter = firefighterRepository.getByLogin(encodeLogin);
        ModelMapper modelMapper = new ModelMapper();
        FirefighterDto firefighterDto = modelMapper.map(firefighter, FirefighterDto.class);
        return firefighterDto;
    }

//    @GetMapping(value = "/id/{username}", produces = "application/json")
//    @ResponseStatus(HttpStatus.OK)
//    public int getFirefighterId(@PathVariable String username)
//    {
//        Base64.Encoder encoder = Base64.getEncoder();
//        String encodeLogin = encoder.encodeToString(username.getBytes());
//        Firefighter firefighter = firefighterRepository.getByLogin(encodeLogin);
//        return firefighter.getId();
//    }
}

