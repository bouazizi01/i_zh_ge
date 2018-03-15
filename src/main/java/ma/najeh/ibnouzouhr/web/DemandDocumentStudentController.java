package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.model.DemandDocumentStudent;
import ma.najeh.ibnouzouhr.model.Notification;
import ma.najeh.ibnouzouhr.repository.DemandDocumentStudentRepository;
import ma.najeh.ibnouzouhr.service.NotificationService;
import ma.najeh.ibnouzouhr.service.StudentService;
import ma.najeh.ibnouzouhr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.Map;

import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.SCOLARITY;
import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.STUDENT;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

@Controller
public class DemandDocumentStudentController {

    private final StudentService studentService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final DemandDocumentStudentRepository demandDocumentStudentRepository;

    @Autowired
    public DemandDocumentStudentController(StudentService studentService, NotificationService notificationService, UserService userService, DemandDocumentStudentRepository demandDocumentStudentRepository) {
        this.studentService = studentService;
        this.notificationService = notificationService;
        this.userService = userService;
        this.demandDocumentStudentRepository = demandDocumentStudentRepository;
    }

    @GetMapping(value = {STUDENT_MY_DOCUMENTS,STUDENT_DOCUMENTS, STUDENT_DOCUMENTS_ALL, SCOLARITY_DOCUMENTS, SCOLARITY_DOCUMENTS_ALL})
    public String all(Map<String, Object> model) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getRole().equals(STUDENT)){
            Iterable<DemandDocumentStudent> documents = demandDocumentStudentRepository.findAllByStudentId(userDetails.getId());
            model.put("documents",documents);
        }else if (userDetails.getRole().equals(SCOLARITY)){
            Iterable<DemandDocumentStudent> documents = demandDocumentStudentRepository.findAll();
            model.put("documents",documents);
        }

        return "/documents/all";
    }


    @GetMapping(STUDENT_DOCUMENTS_ADD)
    public String add() {
        return "/documents/add";
    }

    @PostMapping(STUDENT_DOCUMENTS_ADD)
    public String saveDocument(DemandDocumentStudent demandDocumentStudent) {
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        demandDocumentStudent.setStudent(studentService.findByUsername(userDetails.getUsername()));
        demandDocumentStudent.setState(Constant.DEMAND_DOC_STATE.INITIALIZED);
        demandDocumentStudent.setDemandAt(new Date());
        demandDocumentStudentRepository.save(demandDocumentStudent);
        StringBuilder message =new StringBuilder();
        message.append("Vous avez des nouveaux demande du documents");
        Notification notification=new Notification(message,SCOLARITY_DOCUMENTS,userService.findByRole(SCOLARITY));
        notificationService.save(notification);
        return "redirect:"+STUDENT_MY_DOCUMENTS;
    }


    @PostMapping(SCOLARITY_DOCUMENTS_REFUSE)
    public String refuse(@PathVariable("id") Long id,DemandDocumentStudent demandDocumentStudent) {
        DemandDocumentStudent demandDoc = demandDocumentStudentRepository.findOne(id);
        demandDoc.setReason(demandDocumentStudent.getReason());
        demandDoc.setState(Constant.DEMAND_DOC_STATE.REFUSED);
        demandDoc.setProcessedAt(new Date());
        StringBuilder message =new StringBuilder();
        demandDocumentStudentRepository.save(demandDoc);
        message.append("Votre demande de document a été refusé");
        Notification notification=new Notification(message,STUDENT_DOCUMENTS,demandDoc.getStudent());
        notificationService.save(notification);
        return "redirect:"+SCOLARITY_DOCUMENTS;
    }

    @GetMapping(SCOLARITY_DOCUMENTS_VALIDATE)
    public String validate(@PathVariable("id") Long id) {
        DemandDocumentStudent demandDoc = demandDocumentStudentRepository.findOne(id);
        demandDoc.setState(Constant.DEMAND_DOC_STATE.COMPLETED);
        demandDoc.setProcessedAt(new Date());
        demandDocumentStudentRepository.save(demandDoc);
        StringBuilder message =new StringBuilder();
        message.append("Votre demande de document est traité");
        Notification notification=new Notification(message,STUDENT_DOCUMENTS,demandDoc.getStudent());
        notificationService.save(notification);
        return "redirect:"+SCOLARITY_DOCUMENTS;
    }




}
