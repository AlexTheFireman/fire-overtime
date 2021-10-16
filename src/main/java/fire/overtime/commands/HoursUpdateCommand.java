package fire.overtime.commands;

import fire.overtime.models.Enums.HourType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HoursUpdateCommand {
    private Integer firefighterId;
    private Integer hourIdForUpdate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer monthId;
    private int factHours;
    private HourType hoursType;

    public HoursUpdateCommand() {
    }
}

