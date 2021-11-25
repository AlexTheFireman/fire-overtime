package fire.overtime.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "firefighter")
    @JsonManagedReference
    private Set<Hours> hours = new HashSet<>();

    public Firefighter(Integer firefighterId) {
    }

    public Firefighter() {
    }

    @Column(name = "login", columnDefinition = "varchar(255)", unique = true)
    private String login;

    @Column(name = "password", columnDefinition = "varchar(255)", unique = true)
    private String password;
}

