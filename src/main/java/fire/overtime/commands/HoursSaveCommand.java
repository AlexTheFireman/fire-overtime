package fire.overtime.commands;

import fire.overtime.models.Enums.HourType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class HoursSaveCommand {
    private Integer firefighterId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amountHours;
    private HourType hoursType;

    public HoursSaveCommand() {
    }
}

