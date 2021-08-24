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

    @Column(name = "month_type")
    private String monthType;

    @Column(name = "norma_hours")
    private Integer normaHours;

    @Column(name = "year")
    private Integer year;

    @OneToMany(mappedBy = "month")
    private Set<Hours> hours = new HashSet<>();

    public Integer getMonthYearId() {
        return id;
    }

    public void setMonthYearId(Integer id) {
        this.id = id;
    }

    public String getMonthType() {
        return monthType;
    }

    public void setMonthType(String monthType) {
        this.monthType = monthType;
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

