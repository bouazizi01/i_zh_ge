package ma.najeh.ibnouzouhr.dto;

import ma.najeh.ibnouzouhr.model.Notification;

import java.util.List;

public class NotificationDto {



    List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public NotificationDto setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }
}
