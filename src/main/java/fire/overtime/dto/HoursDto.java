package fire.overtime.dto;

import fire.overtime.models.Enums.HourType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HoursDto {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer factHours;
    private HourType hoursType;
}
