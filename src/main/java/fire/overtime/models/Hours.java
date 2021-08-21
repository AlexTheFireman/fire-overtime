package fire.overtime.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hours")
public class Hours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hours_id;

    @ManyToOne
    @JoinColumn(name = "firefighter_id")
    Firefighter firefighter;

    @ManyToOne
    @JoinColumn(name = "month_year_id")
    Month month;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "fact_hours")
    private int factHours;

    @Column(name = "hours_type")
    private String hoursType;

    public Integer getHours_id() {
        return hours_id;
    }

    public void setHours_id(Integer hours_id) {
        this.hours_id = hours_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getFactHours() {
        return factHours;
    }

    public void setFactHours(int factHours) {
        this.factHours = factHours;
    }

    public String getHoursType() {
        return hoursType;
    }

    public void setHoursType(String hoursType) {
        this.hoursType = hoursType;
    }

    public Firefighter getFirefighter() {
        return firefighter;
    }

    public void setFirefighter(Firefighter firefighter) {
        this.firefighter = firefighter;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}

