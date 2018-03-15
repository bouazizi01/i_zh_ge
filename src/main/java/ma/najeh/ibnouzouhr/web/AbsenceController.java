package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.Absence;
import ma.najeh.ibnouzouhr.model.Planing;
import ma.najeh.ibnouzouhr.model.Seance;
import ma.najeh.ibnouzouhr.repository.PlaningRepository;
import ma.najeh.ibnouzouhr.service.AbsenceService;
import ma.najeh.ibnouzouhr.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.TEACHER_ABSENCES_ADD;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.TEACHER_ABSENCES_SAVE;

/**
 * Created by youssef on 12/19/17.
 */
@Controller
public class AbsenceController {
    private final AbsenceService absenceService;
    private final PlaningRepository planingRepository;
    private final SeanceService seanceService;

    @Autowired
    public AbsenceController(AbsenceService absenceService, PlaningRepository planingRepository, SeanceService seanceService) {
        this.absenceService = absenceService;
        this.planingRepository = planingRepository;
        this.seanceService = seanceService;
    }

    @GetMapping(TEACHER_ABSENCES_ADD)
    public String getAllPlaning(Map<String, Object> model) {
        model.put("allPlaning", planingRepository.findAll());
        return "/absences/add";
    }

    @PostMapping("/teacher/absences/save/web")
    public String saveAbsence(Map<String, Object> model, @RequestParam(value = "planing") Long IdPlaning) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idTeacher = userDetails.getId();
        Absence absence = new Absence();
        Planing planing = planingRepository.findOne(IdPlaning);
        absence.setPlaning(planing);
        Seance seance = seanceService.findOneByTeacherId(idTeacher);

        String username = seance.getTeacher().getUsername();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        if (username.equals(userDetails.getUsername()) && calendar.getTime().before(planing.getPlaningDate())) {
            absenceService.save(absence);
        }
        List<Planing> allPlaning = (List<Planing>) planingRepository.findAll();
        model.put("allPlaning", allPlaning);
        return "/absences/add";
    }


}
