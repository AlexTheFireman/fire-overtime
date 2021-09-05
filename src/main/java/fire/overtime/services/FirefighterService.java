package fire.overtime.services;

import fire.overtime.commands.FirefighterSaveCommand;
import fire.overtime.commands.FirefighterUpdateCommand;
import fire.overtime.models.Firefighter;
import fire.overtime.repositories.FirefighterRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class FirefighterService {

    @Autowired
    public FirefighterRepository firefighterRepository;

    public Firefighter saveFirefighter(FirefighterSaveCommand firefighterSaveCommand) {
        Firefighter firefighter = new Firefighter();
        firefighter.setFirstName(firefighterSaveCommand.getFirstName());
        firefighter.setPatronymic(firefighterSaveCommand.getPatronymic());
        firefighter.setLastName(firefighterSaveCommand.getLastName());
        firefighter.setPosition(firefighterSaveCommand.getPosition());


        return firefighterRepository.save(firefighter);
    }

    public Firefighter updateFirefighter(FirefighterUpdateCommand firefighterUpdateCommand) {
        Optional<Firefighter> byId = firefighterRepository.findById(firefighterUpdateCommand.getFirefighterIdForUpdate());
        if (!byId.isPresent())
            throw new RuntimeException(String.format(
                    "Firefighter with id %d should be present", firefighterUpdateCommand.getFirefighterIdForUpdate()));

        Firefighter firefighter = byId.get();
        if (firefighterUpdateCommand.getFirstName() != null) {
            firefighter.setFirstName(firefighterUpdateCommand.getFirstName());
        }

        if (firefighterUpdateCommand.getFirstName() != null) {
            firefighter.setFirstName(firefighterUpdateCommand.getFirstName());
        }

        if (firefighterUpdateCommand.getFirstName() != null) {
            firefighter.setFirstName(firefighterUpdateCommand.getFirstName());
        }

        if (firefighterUpdateCommand.getFirstName() != null) {
            firefighter.setFirstName(firefighterUpdateCommand.getFirstName());
        }

        return firefighterRepository.save(firefighter);
    }
}

