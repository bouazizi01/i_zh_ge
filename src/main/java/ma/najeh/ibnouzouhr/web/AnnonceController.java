package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.model.Annonce;
import ma.najeh.ibnouzouhr.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;
import java.util.List;
import java.util.Map;


@Controller
public class AnnonceController {
    @Autowired
    private AnnonceRepository annonceRepository;

    @GetMapping({SCOLARITY_ANNONCE_ALL,SCOLARITY_ANNONCE})
    public String all(Model model){
        List<Annonce> annonces= (List<Annonce>) annonceRepository.findAll();
        model.addAttribute("annonces",annonces);
        return "annonces/all";
    }

    @GetMapping(SCOLARITY_ANNONCE_ADD)
    public String add() {
        return "/annonces/add";
    }

    @PostMapping(SCOLARITY_ANNONCE_ADD)
    public String save(Annonce annonce){
        annonceRepository.save(annonce);
        return "/annonces/add";
    }
    @GetMapping(SCOLARITY_ANNONCE_DELETE)
    public ModelAndView delete(ModelMap model, @PathVariable(value="id") Long id) {
        annonceRepository.delete(id);
        return new ModelAndView("redirect:"+SCOLARITY_ANNONCE_ALL, model);

    }

    @GetMapping(SCOLARITY_ANNONCE_EDIT)
    public String edit(Map<String, Object> model, @PathVariable(value="id") Long id) {
        Annonce annonce=annonceRepository.findOne(id);
        model.put("annonce",annonce);
        return "/annonces/edit";
    }

    @PostMapping(SCOLARITY_ANNONCE_UPDATE)
    public ModelAndView update(ModelMap model,Annonce annonce) {
        annonceRepository.save(annonce);
        return new ModelAndView("redirect:"+SCOLARITY_ANNONCE_ALL, model);
    }
}
