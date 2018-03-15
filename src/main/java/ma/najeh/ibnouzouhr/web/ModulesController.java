package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.model.*;
import ma.najeh.ibnouzouhr.repository.BranchRepository;
import ma.najeh.ibnouzouhr.repository.ModuleRepository;
import ma.najeh.ibnouzouhr.repository.SemestreRepository;
import ma.najeh.ibnouzouhr.service.InscriptionService;
import ma.najeh.ibnouzouhr.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
@Controller
public class ModulesController {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private SemestreRepository semestreRepository;

    @GetMapping(value = {ADMIN_MODULES,ADMIN_MODULES_ALL})
    public String all(Map<String, Object> model) {
        Iterable<Module> modules = moduleRepository.findAll();
        model.put("modules",modules);
        return "/modules/all";
    }
    @GetMapping(value = {ADMIN_MODULES_STUDENTS})
    public String studentForModule(Map<String, Object> model,@PathVariable("id") Long id) {
        List<Student> students=new ArrayList<>();
        Module module=moduleRepository.findOne(id);
        Iterable<Inscription> inscriptions = inscriptionService.findAllByModuleId(id);
        for (Inscription i:inscriptions){
            if (i!=null){
                students.add(i.getStudent());
            }
        }
        model.put("students",students);
        model.put("module",module);
        return "/modules/students_for_module";
    }

    @GetMapping(ADMIN_MODULES_ADD)
    public String add(Map<String, Object> model) {
        Iterable<Semestre> semestres = semestreRepository.findAll();
        Iterable<Branch> branches = branchRepository.findAll();
        model.put("semestres",semestres);
        model.put("branches",branches);
        return "/modules/add";
    }

    @PostMapping(ADMIN_MODULES_ADD)
    public String save(Module module) {
        moduleRepository.save(module);
        return "/modules/add";
    }



    @GetMapping(ADMIN_MODULES_UPDATE)
    public String update(Map<String, Object> model,@PathVariable(value = "id") Long id) {
        Iterable<Semestre> semestres = semestreRepository.findAll();
        Iterable<Branch> branches = branchRepository.findAll();
        Module module=moduleRepository.findOne(id);
        model.put("module",module);
        model.put("semestres",semestres);
        model.put("branches",branches);
        return "/modules/edit";
    }


    @PostMapping(ADMIN_MODULES_EDIT)
    public ModelAndView edit(Map<String, Object> model,Module module) {
        moduleRepository.save(module);
        Iterable<Semestre> semestres = semestreRepository.findAll();
        Iterable<Branch> branches = branchRepository.findAll();
        model.put("module",module);
        model.put("semestres",semestres);
        model.put("branches",branches);
        return new ModelAndView("redirect:/admin/modules/edit/"+module.getId(), model);
    }

    @GetMapping(ADMIN_MODULES_DELETE)
    public ModelAndView delete(ModelMap model, @PathVariable(value="id") Long id) {
        moduleRepository.delete(moduleRepository.findOne(id));
        return new ModelAndView("redirect:"+ADMIN_MODULES, model);
    }


    @GetMapping(ADMIN_MODULES_SET_TEACHER)
    public String setTeacher(Map<String, Object> model,@PathVariable(value = "id") Long id) {
        Module module=moduleRepository.findOne(id);
        List<Teacher> teachers = teacherService.findAll();
        model.put("module",module);
        model.put("teachers",teachers);
        return "/modules/edit-teacher";
    }


    @PostMapping(ADMIN_MODULES_UDAPTE_TEACHER)
    public ModelAndView updateTeacher(Module module,Map<String, Object> model) {
        if (module.getTeacher() != null && module.getTeacher().getId() > 0){
            Teacher teacher=teacherService.findOne(module.getTeacher().getId());
            module=moduleRepository.findOne(module.getId());
            module.setTeacher(teacher);
            moduleRepository.save(module);
        }
        return new ModelAndView("redirect:"+ADMIN_MODULES, model);
    }
}
