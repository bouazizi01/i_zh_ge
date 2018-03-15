package ma.najeh.ibnouzouhr.web;


import ma.najeh.ibnouzouhr.constant.Constant.View;
import ma.najeh.ibnouzouhr.model.Admin;
import ma.najeh.ibnouzouhr.model.Student;
import ma.najeh.ibnouzouhr.repository.StudentRepository;
import ma.najeh.ibnouzouhr.repository.UserRepository;
import ma.najeh.ibnouzouhr.service.AdminService;
import ma.najeh.ibnouzouhr.validator.AdminValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/**
 * Created by youssef on 12/16/17.
 */
@Controller
public class AdminController {


    private final AdminService adminService;
    private final AdminValidator adminValidator;
    @Autowired private UserRepository userRepository;
    @Autowired private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(AdminService adminService,AdminValidator adminValidator) {
        this.adminService = adminService;
        this.adminValidator=adminValidator;

    }

    @GetMapping(value = {ADMIN_ADMINS_ALL,ADMIN_ADMINS})
    public String all(Model model){
        Iterable<Admin> admins = adminService.findAll();
        model.addAttribute("admins",admins);
        return  View.ADMINS_ALL;
    }
    @GetMapping(NEW_STUDENTS)
    public String allStudentsNewlyRegistered(Model model){
        List<Student> students  = studentRepository.findByPasswordLike("invalid");
        /*List<Student> students = new ArrayList<>();
        for (User user:users) {
            students.add((Student) user);
        }*/
        model.addAttribute("students",students);
        return  View.STUDENTS_NEWLY_REGISTERED;
    }

    @GetMapping(ADMIN_ADMINS_ADD)
    public String add(Model model) {

        model.addAttribute("admin",new Admin());
        return View.ADMINS_ADD;
    }

    @PostMapping(ADMIN_ADMINS_ADD)
    public String save(@Valid @ModelAttribute Admin admin, BindingResult bindingResult,Model model){

        adminValidator.validate(admin,bindingResult);
        model.addAttribute("admin",admin);
        if(bindingResult.hasErrors()){
            return "/admins/add";
        }
        adminService.save(admin);
        return View.ADMINS_ADD;
    }
    @GetMapping(NEW_STUDENTS_SAVE)
    public String saveNewlyRegistered(@PathVariable Long id){
       Student student= studentRepository.findOne(id);
       student.setPassword(passwordEncoder.encode(student.getCin().toUpperCase()));
       student.setUsername(student.getCodeAPOGEE());
       userRepository.save(student);
        return "redirect:"+NEW_STUDENTS;
    }
    @GetMapping(NEW_STUDENTS_DELETE)
    public String deleteNewlyRegistered(@PathVariable Long id){
       userRepository.delete(id);
        return "redirect:"+NEW_STUDENTS;
    }


    @GetMapping(ADMIN_ADMINS_DELETE)
    public ModelAndView delete(ModelMap model, @PathVariable(value="id") Long id) {
        adminService.delete(id);
        return new ModelAndView("redirect:"+ADMIN_ADMINS_ALL, model);
    }



   /* @GetMapping(ADMIN_ADMINS_EDIT)
    public String edit(Model model, @PathVariable(value="id") Long id) {
        Admin admin=adminService.findOne(id);
        model.addAttribute("admin",admin);
        return View.ADMINS_EDIT;
    }
    @PostMapping(ADMIN_ADMINS_UPDATE)
    public String update(Model model,@Valid @ModelAttribute Admin admin,BindingResult bindingResult) {
        adminValidator.validate(admin,bindingResult);
        model.addAttribute("admin",admin);
        if (bindingResult.hasErrors()){
            return "/admins/edit";
        }
        adminService.save(admin);
       return "redirect:"+ADMIN_ADMINS_ALL;
    }

        return new ModelAndView("redirect:"+ADMIN_ADMINS_ALL, model);
    }*/



}
