package ma.najeh.ibnouzouhr.rest;

import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.rest.message.Message;
import ma.najeh.ibnouzouhr.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.STUDENT;
import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.TEACHER;

@RestController
public class CalendarController {
    private final CalendarService  calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    @GetMapping("/api/calendar/planings")
    public ResponseEntity<?> addPlanning() {
        UserDetailsDto user = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRole().equals(TEACHER)){

            return new ResponseEntity<>(
                    new Message(
                            "all planings are here",
                            calendarService.calendarOfTeacher(user),
                            Constant.Status.SUCCESS
                    ),
                    HttpStatus.ACCEPTED
            );
        }else if(user.getRole().equals(STUDENT)){
            return new ResponseEntity<>(
                    new Message(
                            "all planings are here",
                            calendarService.calendarOfStudent(user),
                            Constant.Status.SUCCESS
                    ),
                    HttpStatus.ACCEPTED
            );
        }
        return new ResponseEntity<>(
                new Message(
                        "only student and teacher have a timeline",
                        null,
                        Constant.Status.ERROR
                ),
                HttpStatus.BAD_REQUEST
        );
    }



}