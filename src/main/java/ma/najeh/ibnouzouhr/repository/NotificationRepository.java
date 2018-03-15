package ma.najeh.ibnouzouhr.repository;

import ma.najeh.ibnouzouhr.model.Notification;
import ma.najeh.ibnouzouhr.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification,Long> {
    List<Notification> findAllByUsers(List<User> users);
}
