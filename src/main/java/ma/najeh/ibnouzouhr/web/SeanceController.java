package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.Day;
import ma.najeh.ibnouzouhr.model.Specialty;
import ma.najeh.ibnouzouhr.repository.GroupRepository;
import ma.najeh.ibnouzouhr.repository.SalleRepository;
import ma.najeh.ibnouzouhr.repository.SpecialtyRepository;
import ma.najeh.ibnouzouhr.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.STUDENT;
import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.TEACHER;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.STUDENT_TIMELINE;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.TEACHER_TIMELINE;

@Controller
public class SeanceController {
    private final TeacherService teacherService;
    private final GroupRepository groupRepository;
    private final SalleRepository salleRepository;
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SeanceController(TeacherService teacherService, GroupRepository groupRepository, SalleRepository salleRepository, SpecialtyRepository specialtyRepository) {
        this.teacherService = teacherService;
        this.groupRepository = groupRepository;
        this.salleRepository = salleRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping(value = Constant.URL.TEACHER_SEANCES_ADD_URL)
    public String add(Model model){
        long specialtyId=0;
        UserDetailsDto teacher = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean coordinator=false;
        for (Specialty s:specialtyRepository.findAll()){
            if (Objects.nonNull(s) && Objects.nonNull(s.getTeacher()) && Objects.equals(s.getTeacher().getId(), teacher.getId())){
                coordinator=true;
                specialtyId=s.getId();
                break;
            }
        }
        model.addAttribute("isCoordinator",coordinator);
        if (specialtyId>0 && coordinator){
            model.addAttribute("days", Day.daysOfWeek());
            model.addAttribute("teachers", teacherService.findAll());
            model.addAttribute("groups", groupRepository.findAllByModuleBranchSpecialtyId(specialtyId));
            model.addAttribute("salles", salleRepository.findAll());
        }else{
            if (teacher.getRole().equals(TEACHER)){
                return "redirect:"+TEACHER_TIMELINE;
            }else if(teacher.getRole().equals(STUDENT)){
                return "redirect:"+STUDENT_TIMELINE;
            }
        }



        return "/seances/add";
    }
}
