package fire.overtime.controllers;

import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.security.AuthenticationBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000", "https://overtime-front.herokuapp.com"})
@RestController
public class BasicAuthenticationController {

    private FirefighterRepository firefighterRepository;

    public BasicAuthenticationController(FirefighterRepository firefighterRepository) {
        this.firefighterRepository = firefighterRepository;
    }

    @GetMapping(path = "/basicauth")
    public AuthenticationBean helloWorldBean() {

        return new AuthenticationBean("You are authenticated");
    }
}