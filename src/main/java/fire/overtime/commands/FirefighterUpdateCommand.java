package fire.overtime.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirefighterUpdateCommand {

    private Integer firefighterIdForUpdate;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;

    public FirefighterUpdateCommand(){
    }
}

