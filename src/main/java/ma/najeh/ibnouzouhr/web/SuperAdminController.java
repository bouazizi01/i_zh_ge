package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.dto.Password;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.repository.*;
import ma.najeh.ibnouzouhr.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static ma.najeh.ibnouzouhr.constant.Constant.URL.DEFAULT_ADMIN_URL;

/**
 * Created by youssef on 1/3/18.
 */
@Controller
public class SuperAdminController {
    private final StudentRepository studentRepository;
    private final NoteRepository noteRepository;
    private final DemandDocumentStudentRepository demandDocumentStudentRepository;
    private final ReclamationRepository reclamationRepository;
    private final PasswordValidator passwordValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public SuperAdminController(StudentRepository studentRepository, NoteRepository noteRepository, DemandDocumentStudentRepository demandDocumentStudentRepository, ReclamationRepository reclamationRepository, PasswordValidator passwordValidator, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.noteRepository = noteRepository;
        this.demandDocumentStudentRepository = demandDocumentStudentRepository;
        this.reclamationRepository = reclamationRepository;
        this.passwordValidator = passwordValidator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(DEFAULT_ADMIN_URL)
    public String dashboard(Model model){
        HashMap<String,String> infos=new HashMap<>();
        infos.put("students",String.valueOf(studentRepository.count()));
        infos.put("notes",String.valueOf(noteRepository.count()));
        infos.put("reclamations",String.valueOf(reclamationRepository.count()));
        infos.put("demands",String.valueOf(demandDocumentStudentRepository.count()));
        model.addAttribute("infos",infos);
        return "/dashboard/dashboard";
    }

    @GetMapping("/administration/change-password")
    public String changePassword( Model model){
        model.addAttribute("password",new Password());
        return "/dashboard/change_password";
    }
    @PostMapping("/administration/change-password")
    public String updatePassword(@Valid @ModelAttribute Password password, BindingResult bindingResult, Model model){
        passwordValidator.validate(password,bindingResult);
        model.addAttribute("password",password);
        if (bindingResult.hasErrors()){
            return "/dashboard/change_password";
        }
        UserDetailsDto user = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userRepository.updatePassword(passwordEncoder.encode(password.getNewPassword()).toCharArray(),user.getId());
        model.addAttribute("changed",true);
        return "/dashboard/change_password";
    }
}


