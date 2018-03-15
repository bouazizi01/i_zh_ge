package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.HomeWork;
import ma.najeh.ibnouzouhr.model.Notification;
import ma.najeh.ibnouzouhr.repository.HomeWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.STUDENT_TIMELINE;

/**
 * Created by Hamza on 04/01/18.
 */
@Service("HomeWorkService")
public class HomeWorkService {
    private final HomeWorkRepository homeWorkRepository;
    private final NotificationService notificationService;

    @Autowired
    public HomeWorkService(HomeWorkRepository homeWorkRepository, NotificationService notificationService) {
        this.homeWorkRepository = homeWorkRepository;
        this.notificationService = notificationService;
    }

    public HomeWork save(HomeWork homeWork){
        homeWork= homeWorkRepository.save(homeWork);
        StringBuilder msj=new StringBuilder("le professeur ")
                .append(homeWork.getSeance().getTeacher().getFullName())
                .append(" a ajouté un devoire pour la seance du ")
                .append(homeWork.getPlaning().getDay().getName())
                .append(" ")
                .append(homeWork.getPlaning().getPlaningDate());
        Notification notification=new Notification(msj,STUDENT_TIMELINE,homeWork.getSeance().getGroup().getStudents());
        notificationService.save(notification);
        return homeWork;
    }

    public HomeWork findOne(Long homeWorkId) {
        return homeWorkRepository.findOne(homeWorkId);
    }

    public Iterable<HomeWork> findAll() {
        return homeWorkRepository.findAll();
    }

    public Iterable<HomeWork> findAllBySeanceTeacherId(Long id) {
        return homeWorkRepository.findAllBySeanceTeacherId(id);
    }

    public Iterable<HomeWork> findAllBySeanceGroupStudentsId(Long id) {
        return homeWorkRepository.findAllBySeanceGroupStudentsId(id);
    }

    public void delete(Long id) {
        homeWorkRepository.delete(id);
    }
}
