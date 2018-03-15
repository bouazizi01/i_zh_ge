package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.model.Branch;
import ma.najeh.ibnouzouhr.model.Specialty;
import ma.najeh.ibnouzouhr.repository.BranchRepository;
import ma.najeh.ibnouzouhr.repository.SpecialtyRepository;
import ma.najeh.ibnouzouhr.validator.BranchValidator;
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
import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
@Controller
public class BranchController {


    final private BranchRepository branchRepository;
    final private SpecialtyRepository specialtyRepository;
    final private BranchValidator branchValidator;

    @Autowired
    public BranchController(BranchValidator branchValidator, BranchRepository branchRepository, SpecialtyRepository specialtyRepository){
        this.branchRepository = branchRepository;
        this.specialtyRepository = specialtyRepository;
        this.branchValidator = branchValidator;
    }

    @GetMapping(ADMIN_BRANCHES_ADD)
    public String add(Model model) {
        List<Specialty> specialties= (List<Specialty>) specialtyRepository.findAll();
        model.addAttribute("specialties",specialties);
        model.addAttribute("branch",new Branch());
        return "/branches/add";
    }

    @PostMapping(ADMIN_BRANCHES_ADD)
    public String save(@Valid @ModelAttribute Branch branch, BindingResult bindingResult, Model model){
        List<Specialty> specialties= (List<Specialty>) specialtyRepository.findAll();
        branchValidator.validate(branch,bindingResult);
        model.addAttribute("specialties",specialties);
        model.addAttribute("branch",branch);
        if(bindingResult.hasErrors()){
            return "/branches/add";
        }
        model.addAttribute("branch",branchRepository.save(branch));
        return "/branches/add";
    }


    @GetMapping(value = {ADMIN_BRANCHES_ALL,ADMIN_BRANCHES})
    public String all(Model model){
        List<Branch> branches=(List<Branch>) branchRepository.findAll();
        model.addAttribute("branches",branches);
        return "branches/all";
    }

    @GetMapping(ADMIN_BRANCHES_DELETE)
    public ModelAndView deleteSpecialty(ModelMap model, @PathVariable(value="id") Long id) {
        branchRepository.delete(id);
        return new ModelAndView("redirect:"+ADMIN_BRANCHES_ALL, model);
    }

    @GetMapping(ADMIN_BRANCHES_EDIT)
    public String edit(Model model, @PathVariable(value="id") Long id) {
        Branch branch=branchRepository.findOne(id);
        List<Specialty> specialties= (List<Specialty>) specialtyRepository.findAll();
        model.addAttribute("specialties",specialties);
        model.addAttribute("branch",branch);
        return "/branches/edit";
    }
    @PostMapping(ADMIN_BRANCHES_UPDATE)
    public String update(Model model,@Valid @ModelAttribute Branch branch,BindingResult bindingResult) {
        List<Specialty> specialties= (List<Specialty>) specialtyRepository.findAll();
        branchValidator.validate(branch,bindingResult);
        model.addAttribute("specialties",specialties);
        model.addAttribute("branch",branch);
        if(bindingResult.hasErrors()){
            return "/branches/edit";
        }
        branchRepository.save(branch);
        return"redirect:"+ADMIN_BRANCHES_ALL;
    }
}
