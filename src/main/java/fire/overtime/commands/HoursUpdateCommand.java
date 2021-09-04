package fire.overtime.commands;

import java.time.LocalDate;

public class HoursUpdateCommand {
    private Integer firefighterId;
    private Integer hourIdForUpdate;
    private LocalDate date;
    private Integer monthId;
    private int factHours;
    private String hoursType;

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

    public String getHoursType() {
        return hoursType;
    }

    public void setHoursType(String hoursType) {
        this.hoursType = hoursType;
    }

    public Integer getHourIdForUpdate() {
        return hourIdForUpdate;
    }

    public void setHourIdForUpdate(Integer hourIdForUpdate) {
        this.hourIdForUpdate = hourIdForUpdate;
    }
}

