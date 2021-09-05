package fire.overtime.commands;

import fire.overtime.models.Enums.HourType;

import java.time.LocalDate;

public class HoursUpdateCommand {
    private Integer firefighterId;
    private Integer hourIdForUpdate;
    private LocalDate date;
    private Integer monthId;
    private int factHours;
    private HourType hoursType;

    public HoursUpdateCommand() {
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

    public Integer getHourIdForUpdate() {
        return hourIdForUpdate;
    }

    public void setHourIdForUpdate(Integer hourIdForUpdate) {
        this.hourIdForUpdate = hourIdForUpdate;
    }
}

