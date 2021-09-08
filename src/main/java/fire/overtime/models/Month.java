package fire.overtime.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getNormaHours() {
        return normaHours;
    }

    public void setNormaHours(Integer normaHours) {
        this.normaHours = normaHours;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

