package ma.najeh.ibnouzouhr.dto;

import java.io.Serializable;

public class SeanceDto  implements Serializable{
    private String startTime;
    private String endTime;
    private Long day;
    private Long teacherId;
    private Long groupId;
    private Long salleId;

    public String getStartTime() {
        return startTime;
    }

    public SeanceDto setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public SeanceDto setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public Long getDay() {
        return day;
    }

    public SeanceDto setDay(Long day) {
        this.day = day;
        return this;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public SeanceDto setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public SeanceDto setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public Long getSalleId() {
        return salleId;
    }

    public SeanceDto setSalleId(Long salleId) {
        this.salleId = salleId;
        return this;
    }

    @Override
    public String toString() {
        return "SeanceDto{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", day=" + day +
                ", teacherId=" + teacherId +
                ", groupId=" + groupId +
                ", salleId=" + salleId +
                '}';
    }
}
