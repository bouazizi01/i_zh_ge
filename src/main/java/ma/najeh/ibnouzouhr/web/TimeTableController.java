package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.Specialty;
import ma.najeh.ibnouzouhr.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.ADMIN_TIMELINE_CREATE;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.STUDENT_TIMELINE;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.TEACHER_TIMELINE;

/**
 * Created by youssef on 12/19/17.
 */
@Controller
public class TimeTableController {
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public TimeTableController(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping(ADMIN_TIMELINE_CREATE)
    public String create(){

        return "/timeline/create";
    }
    @GetMapping(TEACHER_TIMELINE)
    public String teacherPlaning(Model model){
        UserDetailsDto teacher = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean coordinator=false;
        for (Specialty s:specialtyRepository.findAll()){
            if (Objects.nonNull(s) && Objects.nonNull(s.getTeacher()) && Objects.equals(s.getTeacher().getId(), teacher.getId())){
                coordinator=true;
                break;
            }
        }
        model.addAttribute("isCoordinator",coordinator);

        return "/timeline/calendar";
    }
    @GetMapping(STUDENT_TIMELINE)
    public String studentPlaning(){

        return "/timeline/calendar";
    }

}
