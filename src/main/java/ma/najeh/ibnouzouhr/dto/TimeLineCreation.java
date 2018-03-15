package ma.najeh.ibnouzouhr.dto;

import ma.najeh.ibnouzouhr.model.Hour;

import java.io.Serializable;

/**
 * Created by youssef on 12/19/17.
 */
public class TimeLineCreation implements Serializable {
    private Long teacherId;
    private Long salleId;
    private Long groupId;
    private Integer day;
    private String startHour;
    private String endHour;

    public Integer getDay() {
        return day;
    }

    public TimeLineCreation setDay(Integer day) {
        this.day = day;
        return this;
    }

    public Long getSalleId() {
        return salleId;
    }

    public TimeLineCreation setSalleId(Long salleId) {
        this.salleId = salleId;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public TimeLineCreation setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public TimeLineCreation setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
        return this;
    }


    public String getStartHour() {
        return startHour;
    }

    public TimeLineCreation setStartHour(String startHour) {
        this.startHour = startHour;
        return this;
    }

    public String getEndHour() {
        return endHour;
    }

    public TimeLineCreation setEndHour(String endHour) {
        this.endHour = endHour;
        return this;
    }

    @Override
    public String toString() {
        return "TimeLineCreation{" +
                "teacherId=" + teacherId +
                ", salleId=" + salleId +
                ", groupId=" + groupId +
                ", day=" + day +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}
