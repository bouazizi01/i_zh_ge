package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.dto.Calendar;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.Planing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ma.najeh.ibnouzouhr.constant.Constant.ABSENCE_CLASSNAME;
import static ma.najeh.ibnouzouhr.constant.Constant.HOMEWORK_CLASSNAME;

@Service
public class CalendarService {


    private final SeanceService seanceService;

    @Autowired
    public CalendarService(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    public List<Calendar> calendarOfTeacher(UserDetailsDto teacher) {
        List<Planing> planings = seanceService.allPlaningsOfSeancesOfATeacher(teacher.getId());
        return formatCalander(planings);
    }


    public List<Calendar> calendarOfStudent(UserDetailsDto student) {
        List<Planing> planings = seanceService.allPlaningsOfSeancesOfAStudent(student.getId());
        return formatCalander(planings);
    }

    private List<Calendar> formatCalander(List<Planing> planings) {
        List<Calendar> calendars = new ArrayList<>();
        StringBuilder fromTo = new StringBuilder();
        for (Planing planing : planings) {
            Calendar calendar = new Calendar();
            calendar.setPlaning(planing);
            calendar.setId(planing.getId());
            Date start = planing.getPlaningDate();
            Date end = planing.getPlaningDate();
            start.setHours(planing.getStartHour().getH());
            start.setMinutes(planing.getStartHour().getM());

            end.setHours(planing.getEndHour().getH());
            end.setMinutes(planing.getEndHour().getM());
            fromTo.append("\n[ ").append(planing.getStartHour().getH()).append(":").append(planing.getStartHour().getM()).append("->").append(planing.getEndHour().getH()).append(":").append(planing.getEndHour().getM()).append(" ]");
            calendar.setStart(start).setDescription("")
                    .setEnd(end)
                    .setTitle(planing.getSeance().getGroup().getModule().getName() + " " + fromTo.toString() + " GR: " + planing.getSeance().getGroup().getName());
            if (planing.getAbsences()!= null && !planing.getAbsences().isEmpty()){
                calendar.addClassName(ABSENCE_CLASSNAME);
            }
            if (planing.getHomeWorks()!= null && !planing.getHomeWorks().isEmpty()){
                calendar.addClassName(HOMEWORK_CLASSNAME);
            }
            calendars.add(calendar);
            fromTo = new StringBuilder();
        }
        return calendars;
    }

}

