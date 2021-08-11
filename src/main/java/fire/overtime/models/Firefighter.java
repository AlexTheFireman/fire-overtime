package fire.overtime.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "firefighter")
public class Firefighter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "position")
    private String position;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hours",
            joinColumns = @JoinColumn(name = "firefighterId"),
            inverseJoinColumns = @JoinColumn(name = "monthYearId")
    )

    private Set<Month> months;

    public Integer getFirefighter_id() {
        return id;
    }

    public void setFirefighter_id(Integer firefighter_id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
}

