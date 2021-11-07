package fire.overtime.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fire.overtime.models.Enums.HourType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "hours")
public class Hours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "firefighter_id")
    @JsonBackReference
    private Firefighter firefighter;

    @Column(name = "firefighter_id", insertable = false, updatable = false)
    private Integer firefighterId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "hours_amount")
    private Integer amountHours;

    @Column(name = "hours_type")
    @Enumerated(EnumType.STRING)
    private HourType hoursType;

}

