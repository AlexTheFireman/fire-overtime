package fire.overtime.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hours")
public class Hours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hoursId;

    @ManyToOne
    @JoinColumn(name = "firefighter_id")
    private Firefighter firefighter;

    @ManyToOne
    @JoinColumn(name = "month_year_id")
    private Month month;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "fact_hours")
    private Integer factHours;

    @Column(name = "hours_type")
    private String hoursType;

    public Integer getHoursId() {
        return hoursId;
    }

    public void setHoursId(Integer hoursId) {
        this.hoursId = hoursId;
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

