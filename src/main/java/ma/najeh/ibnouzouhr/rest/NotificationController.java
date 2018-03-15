package ma.najeh.ibnouzouhr.rest;


import ma.najeh.ibnouzouhr.dto.NotificationDto;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.Notification;
import ma.najeh.ibnouzouhr.model.User;
import ma.najeh.ibnouzouhr.service.NotificationService;
import ma.najeh.ibnouzouhr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youssef on 12/16/17.
 */
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    @Autowired
    private UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/api/notifications")
    public List<Notification> notifications(){
        NotificationDto notificationDto= new NotificationDto();
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user =this.userService.findOne(userDetails.getId());
        List<User> users = new ArrayList<>();
        users.add(user);
        notificationDto.setNotifications(notificationService.findByUsers(users));

        return notificationDto.getNotifications();
    }




}

