package fire.overtime.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "month_year")
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "month_name")
    private String monthName;

    @Column(name = "norma_hours")
    private int normaHours;

    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "month")
    private Set<Hours> hours = new HashSet<>();

    public Month(Integer monthId) {
    }

    public Month() {

    }
}

