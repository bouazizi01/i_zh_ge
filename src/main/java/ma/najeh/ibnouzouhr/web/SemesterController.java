package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.model.Semestre;
import ma.najeh.ibnouzouhr.repository.SemestreRepository;
import ma.najeh.ibnouzouhr.validator.SemestreValidator;
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

import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
@Controller
public class SemesterController {
    @Autowired
    private SemestreRepository semestreRepository;
    @Autowired
    private SemestreValidator semestreValidator;

    @GetMapping(ADMIN_SEMESTERS_ALL)
    public String all(Model model) {
        List<Semestre> semestres = (List<Semestre>) semestreRepository.findAll();
        model.addAttribute("semestres", semestres);
        return "semesters/all";
    }

    @GetMapping(ADMIN_SEMESTERS_ADD)
    public String add(Model model) {
        model.addAttribute("semestre", new Semestre());
        return "/semesters/add";
    }

    @PostMapping(ADMIN_SEMESTERS_ADD)
    public String save(@Valid @ModelAttribute Semestre semestre, BindingResult bindingResult, Model model) {
        semestreValidator.validate(semestre, bindingResult);
        model.addAttribute("semestre", semestre);
        if (bindingResult.hasErrors()) {
            return "/semesters/add";
        }
        model.addAttribute("semestre", semestreRepository.save(semestre));
        return "/semesters/add";
    }

    @GetMapping(ADMIN_SEMESTERS_DELETE)
    public ModelAndView deleteSemester(ModelMap model, @PathVariable(value = "id") Long id) {
        semestreRepository.delete(id);
        return new ModelAndView("redirect:/admin/semesters/all", model);
    }

    @GetMapping(ADMIN_SEMESTERS_EDIT)
    public String edit(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Semestre semestre = semestreRepository.findOne(id);
        model.put("semestre", semestre);
        return "/semesters/edit";
    }

    @PostMapping(ADMIN_SEMESTERS_UPDATE)
    public String update( Semestre semestre,Model model) {
        model.addAttribute("semestre", semestre);
        semestreRepository.save(semestre);
        return "redirect:" + ADMIN_SEMESTERS_ALL;
    }
}
