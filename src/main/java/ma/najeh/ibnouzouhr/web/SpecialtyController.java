package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.model.Specialty;
import ma.najeh.ibnouzouhr.model.Teacher;
import ma.najeh.ibnouzouhr.repository.SpecialtyRepository;
import ma.najeh.ibnouzouhr.service.TeacherService;
import ma.najeh.ibnouzouhr.validator.SpecialtyValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
@Controller
public class SpecialtyController {
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SpecialtyValidator  specialtyValidator;


    @GetMapping("/admin/specialtys/add")
    public String add(Model model) {
        model.addAttribute("specialty",new Specialty());
        return "/specialtys/add";

    }

    @PostMapping("/admin/specialtys/add")
    public String save(@Valid @ModelAttribute Specialty specialty, Model model,BindingResult bindingResult){
        specialtyValidator.validate(specialty,bindingResult);
        model.addAttribute("specialty",specialty);
        if(bindingResult.hasErrors()){
            return "/specialtys/add";
        }
        model.addAttribute("specialty",specialtyRepository.save(specialty));
        return "/specialtys/add";
    }

    @GetMapping("/admin/specialtys/all")
    public String all(Model model){
        List<Specialty> specialtys= (List<Specialty>) specialtyRepository.findAll();
        model.addAttribute("specialtys",specialtys);
        return "specialtys/all";
    }
    @GetMapping("/admin/specialtys/delete/{id}")
    public ModelAndView deleteSpecialty(ModelMap model, @PathVariable(value="id") Long id) {
        specialtyRepository.delete(id);
        return new ModelAndView("redirect:/admin/specialtys/all", model);
    }
    @GetMapping("/admin/specialtys/edit/{id}")
    public String editSpecialty(Map<String, Object> model, @PathVariable(value="id") Long id) {
        Specialty specialty=specialtyRepository.findOne(id);
        Iterable<Teacher> teachers=teacherService.findAll();
        model.put("teachers",teachers);
        model.put("specialty",specialty);
        return "/specialtys/edit";
    }
    @PostMapping("/admin/specialtys/edit")
    public String update(Model model,@Valid @ModelAttribute Specialty specialty,BindingResult bindingResult){
        specialtyValidator.validate(specialty,bindingResult);
        model.addAttribute("specialty",specialty);
        if(bindingResult.hasErrors()){
            return "/specialtys/edit";
        }
        specialtyRepository.save(specialty);
        return "redirect:/admin/specialtys/all";
    }
}
