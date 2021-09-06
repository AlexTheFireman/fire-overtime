package fire.overtime.commands;

import fire.overtime.models.Enums.HourType;

import java.time.LocalDate;

public class HoursSaveCommand {
    private Integer firefighterId;
    private LocalDate date;
    private Integer monthId;
    private int factHours;
    private HourType hoursType;

    public HoursSaveCommand() {
    }

    public Integer getFirefighterId() {
        return firefighterId;
    }

    public void setFirefighterId(Integer firefighterId) {
        this.firefighterId = firefighterId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public int getFactHours() {
        return factHours;
    }

    public void setFactHours(int factHours) {
        this.factHours = factHours;
    }

    public HourType getHoursType() {
        return hoursType;
    }

    public void setHoursType(HourType hoursType) {
        this.hoursType = hoursType;
    }
}

