package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.Notification;
import ma.najeh.ibnouzouhr.model.User;
import ma.najeh.ibnouzouhr.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("NotificationService")
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> findByUsers(List<User> users){
        return notificationRepository.findAllByUsers(users);
    }


}
