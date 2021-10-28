package fire.overtime.dto;

import fire.overtime.models.Enums.HourType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HoursDto {

    private int id;
    private LocalDate start_date;
    private LocalDate end_date;
    private Integer factHours;
    private HourType hoursType;
}
