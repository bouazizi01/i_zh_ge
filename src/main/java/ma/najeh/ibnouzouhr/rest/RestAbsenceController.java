package ma.najeh.ibnouzouhr.rest;

import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.Absence;
import ma.najeh.ibnouzouhr.model.Planing;
import ma.najeh.ibnouzouhr.model.Seance;
import ma.najeh.ibnouzouhr.repository.PlaningRepository;
import ma.najeh.ibnouzouhr.service.AbsenceService;
import ma.najeh.ibnouzouhr.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.TEACHER_ABSENCES_SAVE;

/**
 * Created by youssef on 12/19/17.
 */
@Controller
public class RestAbsenceController {
    private final AbsenceService absenceService;
    private final PlaningRepository planingRepository;
    private final SeanceService seanceService;

    @Autowired
    public RestAbsenceController(AbsenceService absenceService, PlaningRepository planingRepository, SeanceService seanceService) {
        this.absenceService = absenceService;
        this.planingRepository = planingRepository;
        this.seanceService = seanceService;
    }



    @PostMapping(TEACHER_ABSENCES_SAVE)
    public ResponseEntity<?> saveAbsence(Map<String, String> model, @RequestParam(value = "id") Long id) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Absence absence = new Absence();
        Planing planing = planingRepository.findOne(id);
        Absence ab=absenceService.findByPlaningId(id);
        absence.setPlaning(planing);
        absence.setCreatedAt(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        if (planing==null) {
            model.put("errorMsj","seance n'est pas valide");
        }else if (ab!=null) {
            model.put("errorMsj","cette absence est déja crée");
        }else if (!planing.getSeance().getTeacher().getId().equals(userDetails.getId())) {
            model.put("errorMsj","vous n'êtes pas autorisé");
        }else if (calendar.getTime().after(planing.getPlaningDate())){
            model.put("errorMsj","vous pouvez pas crée un absence dans les 48h avant la seance");
        }else{
            model.put("success","success");
            absenceService.save(absence);
        }
        return new  ResponseEntity<>(model, HttpStatus.OK);

    }


}
