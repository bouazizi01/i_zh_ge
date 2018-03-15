package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.*;
import ma.najeh.ibnouzouhr.repository.ReclamationRepository;
import ma.najeh.ibnouzouhr.repository.SpecialtyRepository;
import ma.najeh.ibnouzouhr.service.ModuleService;
import ma.najeh.ibnouzouhr.service.NotesService;
import ma.najeh.ibnouzouhr.service.StudentService;
import ma.najeh.ibnouzouhr.util.XLSUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static ma.najeh.ibnouzouhr.constant.Constant.DEMAND_REC_STATE.ACCEPTED_ADMIN;
import static ma.najeh.ibnouzouhr.constant.Constant.DEMAND_REC_STATE.ACCEPTED_TEACHER;
import static ma.najeh.ibnouzouhr.constant.Constant.DEMAND_REC_STATE.ACCEPTED_UPDATED;
import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.STUDENT;
import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.TEACHER;


@Controller
public class ReclamationController {
    private final ReclamationRepository reclamationRepository;
    private final StudentService studentService;
    private final NotesService notesService;
    private final SpecialtyRepository specialtyRepository;
    private final ModuleService moduleService;
    private static final String ATTACHMENT_FILENAME = "Attachment;Filename=\"";

    @Autowired
    public ReclamationController(NotesService notesService, ReclamationRepository reclamationRepository, StudentService studentService, SpecialtyRepository specialtyRepository, ModuleService moduleService) {
        this.notesService = notesService;
        this.reclamationRepository = reclamationRepository;
        this.studentService = studentService;
        this.specialtyRepository = specialtyRepository;
        this.moduleService = moduleService;
    }


    @GetMapping(value = {"/student/reclamations/all", "/student/my-reclamations", "/student/reclamations", "/teacher/reclamations", "/teacher/reclamations/all"})
    public String all(Map<String, Object> model) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Reclamation> reclamations = new ArrayList<>();
        boolean isCoorinator=false;
        if (userDetails.getRole().equals(STUDENT)) {
            reclamations = reclamationRepository.findAllByStudentId(userDetails.getId());
        } else if (userDetails.getRole().equals(TEACHER)) {
            //by branch
            Specialty specialty = specialtyRepository.findByTeacherId(userDetails.getId());
            if (specialty == null || specialty.getTeacher() == null) {
                Module module = moduleService.findByTeacherId(userDetails.getId());
                if (module != null) {
                    reclamations = reclamationRepository.allReclamationsbyModuleAndState(module.getId(),ACCEPTED_ADMIN);
                }
            } else {
                isCoorinator=true;
                reclamations = reclamationRepository.allReclamationsbySpeciality(specialty.getId());
            }
        }
        model.put("isCoorinator", isCoorinator);
        model.put("reclamations", reclamations);

        return "/reclamations/all";
    }

    @GetMapping("/student/claim-note/{noteId}")
    public String add(@PathVariable("noteId") Long noteId) {
        Note note = notesService.findOne(noteId);
        if (Objects.nonNull(note) && note.isReclamable()) {
            UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Reclamation reclamation = new Reclamation();
            reclamation.setCreatedAt(new Date());
            reclamation.setNote(note);
            reclamation.setStudent(studentService.findByUsername(userDetails.getUsername()));
            reclamation.setState(Constant.DEMAND_REC_STATE.INITIALIZED);
            reclamationRepository.save(reclamation);
        }
        return "redirect:/student/reclamations/all";

    }

    @PostMapping("/teacher/accept-reclamation")
    public String validateByModuleTeacher(Reclamation reclamation) {
        Double newNote=reclamation.getNewNote();
        reclamation=reclamationRepository.findOne(reclamation.getId());
        if (Objects.nonNull(reclamation)) {
            UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Teacher moduleTeacher=reclamation.getNote().getModule().getTeacher();
            if(userDetails.getId().equals(moduleTeacher.getId())){
                if (reclamation.getState().equals(ACCEPTED_ADMIN)){
                    reclamation.setNewNote(newNote);
                    reclamation.setState(ACCEPTED_TEACHER);
                    reclamationRepository.save(reclamation);
                }
            }

        }
        return "redirect:/teacher/reclamations";
    }
    @GetMapping("/teacher/reclamations/validate/{reclamationId}")
    public String validate(@PathVariable("reclamationId") Long reclamationId) {
        Reclamation reclamation = reclamationRepository.findOne(reclamationId);
        if (Objects.nonNull(reclamation)) {
            UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Teacher coordinator=reclamation.getNote().getModule().getBranch().getSpecialty().getTeacher();
            if (userDetails.getId().equals(coordinator.getId())){
                if (reclamation.getState().equals(ACCEPTED_TEACHER)){

                    Note note=reclamation.getNote();
                    note.setNote(reclamation.getNewNote());
                    if (note.getNote()>=10){
                        note.setSession("V");
                    }else{
                        note.setSession("NV");
                    }
                    notesService.updateNote(note);
                    reclamation.setState(ACCEPTED_UPDATED);
                    reclamation.setNewNote(null);
                    reclamationRepository.save(reclamation);
                }else{
                    reclamation.setState(ACCEPTED_ADMIN);
                    reclamationRepository.save(reclamation);
                }
            }

        }
        return "redirect:/teacher/reclamations";
    }

    @GetMapping("/teacher/reclamations/refuse/{reclamationId}")
    public String refuse(@PathVariable("reclamationId") Long reclamationId) {
        Reclamation reclamation = reclamationRepository.findOne(reclamationId);
        if (Objects.nonNull(reclamation)) {
            UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Teacher coordinator=reclamation.getNote().getModule().getBranch().getSpecialty().getTeacher();
            Teacher moduleTeacher=reclamation.getNote().getModule().getTeacher();
            if (userDetails.getId().equals(coordinator.getId())){
                reclamation.setState(Constant.DEMAND_REC_STATE.REFUSED_ADMIN);
                reclamationRepository.save(reclamation);
            }else if(userDetails.getId().equals(moduleTeacher.getId())){
                if (reclamation.getState().equals(ACCEPTED_ADMIN)){
                    reclamation.setState(Constant.DEMAND_REC_STATE.REFUSED_TEACHER);
                    reclamationRepository.save(reclamation);
                }
            }
        }
        return "redirect:/teacher/reclamations";
    }

    @GetMapping("/admin/reclamations/{specialtyId}/all/pdf")
    public void downloadReclamations(HttpServletResponse response, @PathVariable("specialtyId") Long specialtyId) {
        List<Reclamation> reclamations = (List<Reclamation>) reclamationRepository.allReclamationsbySpeciality(specialtyId);
        System.out.println(reclamations.size());
        if (!reclamations.isEmpty()) {
            String specName = reclamations.get(0).getNote().getModule().getBranch().getSpecialty().getName();
            XSSFWorkbook workbook = XLSUtil.writeReclamations(reclamations);
            try {
                OutputStream outputStream = response.getOutputStream();
                String disHeader = ATTACHMENT_FILENAME + "reclamations_2017_" + specName + ".xlsx" + "\"";
                response.setHeader("Content-Disposition", disHeader);
                workbook.write(outputStream);
                workbook.close();
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
