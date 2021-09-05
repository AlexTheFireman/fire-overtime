package fire.overtime.commands;

public class FirefighterUpdateCommand {

    private Integer firefighterIdForUpdate;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;

    public FirefighterUpdateCommand(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getFirefighterIdForUpdate() {
        return firefighterIdForUpdate;
    }

    public void setFirefighterIdForUpdate(Integer firefighterIdForUpdate) {
        this.firefighterIdForUpdate = firefighterIdForUpdate;
    }
}

