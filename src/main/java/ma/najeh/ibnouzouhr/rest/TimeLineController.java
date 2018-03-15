package ma.najeh.ibnouzouhr.rest;

import ma.najeh.ibnouzouhr.dto.TimeLineCreation;
import ma.najeh.ibnouzouhr.model.*;
import ma.najeh.ibnouzouhr.repository.GroupRepository;
import ma.najeh.ibnouzouhr.repository.SalleRepository;
import ma.najeh.ibnouzouhr.service.SeanceService;
import ma.najeh.ibnouzouhr.service.TeacherService;
import ma.najeh.ibnouzouhr.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static ma.najeh.ibnouzouhr.constant.Constant.CONFIG.START_DATE;
import static ma.najeh.ibnouzouhr.constant.Constant.CONFIG.END_DATE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by youssef on 12/19/17.
 */
@RestController()
public class TimeLineController {
    final private GroupRepository groupRepository;
    final private SalleRepository salleRepository;
    final private TeacherService teacherService;
    final private SeanceService seanceService;
    @Autowired
    public TimeLineController(GroupRepository groupRepository, SalleRepository salleRepository, TeacherService teacherService, SeanceService seanceService) {
        this.groupRepository = groupRepository;
        this.salleRepository = salleRepository;
        this.teacherService = teacherService;
        this.seanceService = seanceService;
    }

    @PostMapping("/api/admin/timeline/create")
    public String save(TimeLineCreation timeLineCreation) throws CloneNotSupportedException{
        System.out.println(timeLineCreation.toString());
        Date startDate=DateUtil.toDate(START_DATE);
        Date endDate=DateUtil.toDate(END_DATE);
        Group group=groupRepository.findOne(timeLineCreation.getGroupId());
        Salle salle=salleRepository.findOne(timeLineCreation.getSalleId());
        Teacher teacher=teacherService.findOne(timeLineCreation.getTeacherId());

        int i=0;
        startDate=DateUtil.firstDay(timeLineCreation.getDay(),startDate);
        while (endDate.after(startDate)){
            Planing planing=new Planing(timeLineCreation.getStartHour(),timeLineCreation.getEndHour());
            Seance seance=new Seance();
            seance.setScholarYear(String.valueOf(DateUtil.getYear(startDate)))
                    .setTeacher(teacher)
                    .setSalle(salle)
                    .setGroup(group);
            //seance.setPlaning(planing);
            startDate=DateUtil.nextWeek(startDate);
            seanceService.save(seance);
            i++;
        }
        System.out.println(i);

        return "/timeline/create";
    }
}
