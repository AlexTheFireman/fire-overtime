package fire.overtime.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "firefighter")

public class Firefighter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "firefighter", cascade = CascadeType.ALL)
    private Set<Hours> hours = new HashSet<>();

    public Firefighter(Integer firefighterId) {
    }

    public Firefighter() {

    }

    public Integer getFirefighterId() {
        return id;
    }

    public void setFirefighterId(Integer id) {
        this.id = id;
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

    public Set<Hours> getHours() {
        return hours;
    }

    public void setMonths(Set<Hours> hours) {
        this.hours = hours;
    }
}

