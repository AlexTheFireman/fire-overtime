package fire.overtime.controllers;

import fire.overtime.dto.FirefighterDto;
import fire.overtime.models.Firefighter;
import fire.overtime.repositories.FirefighterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firefighter")
public class FirefighterController {

    @Autowired
    FirefighterRepository firefighterRepository;


    @GetMapping(value = "/{firefighterId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public FirefighterDto getFirefighter(@PathVariable Integer firefighterId)
    {
        ModelMapper modelMapper = new ModelMapper();
        Firefighter firefighter = firefighterRepository.getById(firefighterId);
        FirefighterDto firefighterDto = modelMapper.map(firefighter, FirefighterDto.class);
        return firefighterDto;
    }
}

