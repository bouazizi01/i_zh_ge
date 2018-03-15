package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.Absence;
import ma.najeh.ibnouzouhr.model.Notification;
import ma.najeh.ibnouzouhr.model.User;
import ma.najeh.ibnouzouhr.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.STUDENT_TIMELINE;

/**
 * Created by youssef on 12/15/17.
 */
@Service("AbsenceService")
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final NotificationService notificationService;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, NotificationService notificationService) {
        this.absenceRepository = absenceRepository;
        this.notificationService = notificationService;
    }

    public Absence save(Absence absence){
        absence= absenceRepository.save(absence);
        StringBuilder msj=new StringBuilder("le professeur ")
                            .append(absence.getPlaning().getSeance().getTeacher().getFullName())
                            .append(" sera absent la seance du ")
                            .append(absence.getPlaning().getDay().getName())
                            .append(" ")
                            .append(absence.getPlaning().getPlaningDate());
        Notification notification=new Notification(msj,STUDENT_TIMELINE,absence.getPlaning().getSeance().getGroup().getStudents());
        notificationService.save(notification);
        return absence;
    }


    public Absence findByPlaningId(Long id) {
        return absenceRepository.findByPlaningId(id);
    }
}
