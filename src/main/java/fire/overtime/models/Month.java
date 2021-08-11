package fire.overtime.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "month")
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "month_type")
    private String month_type;

    @Column(name = "norma_hours")
    private Integer norma_hours;

    @Column(name = "year")
    private Integer year;

    @ManyToMany(mappedBy = "months")
    private Set<Firefighter> firefighters;

    public Month() {
    }

    public Integer getMonthYearId() {
        return Id;
    }

    public void setMonthYearId(Integer Id) {
        this.Id = Id;
    }

    public String getMonth_type() {
        return month_type;
    }

    public void setMonth_type(String month_type) {
        this.month_type = month_type;
    }

    public Integer getNorma_hours() {
        return norma_hours;
    }

    public void setNorma_hours(Integer norma_hours) {
        this.norma_hours = norma_hours;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

