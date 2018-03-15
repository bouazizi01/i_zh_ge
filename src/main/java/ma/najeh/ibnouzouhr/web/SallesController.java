package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.dto.Bloc;
import ma.najeh.ibnouzouhr.model.Salle;
import ma.najeh.ibnouzouhr.repository.SalleRepository;
import ma.najeh.ibnouzouhr.service.NotificationService;
import ma.najeh.ibnouzouhr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class SallesController {

    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping(value = {ADMIN_SALLES,ADMIN_SALLES_ALL})
    public String all(Map<String, Object> model) {
        Iterable<Salle> salles = salleRepository.findAll();
        model.put("salles",salles);
        return "/salles/all";
    }

    @GetMapping(ADMIN_SALLES_ADD)
    public String add() {
        return "/salles/add";
    }

    @PostMapping(ADMIN_SALLES_ADD)
    public String save(Bloc bloc) {

        List<Salle> salles=new ArrayList<>();
        for (int i=1;i<= bloc.getNumberSalle();i++){
            salles.add(new Salle(bloc.getName()+"-"+String.valueOf(i),bloc.getName(),"Salle",bloc.getCapacity()));
        }
        salleRepository.save(salles);
        return "/salles/add";
    }

    @PostMapping(ADMIN_SALLES_ADD_AMPHI)
    public String save(Salle salle) {
        salle.setType("Amphi");
        salleRepository.save(salle);
        return "/salles/add";
    }


    @GetMapping(ADMIN_SALLES_DELETE)
    public ModelAndView delete(ModelMap model, @PathVariable(value="id") Long id) {
        salleRepository.delete(id);
        return new ModelAndView("redirect:"+ADMIN_SALLES_ALL, model);
    }


    @GetMapping(ADMIN_SALLES_EDIT)
    public String edit(Map<String, Object> model,@PathVariable(value="id") Long id) {
        Salle salle=salleRepository.findOne(id);
        model.put("salle",salle);
        return "/salles/edit";
    }

    @PostMapping(ADMIN_SALLES_UPDATE)
    public ModelAndView update(ModelMap model,Salle salle) {
        salleRepository.save(salle);
        return new ModelAndView("redirect:"+ADMIN_SALLES_ALL, model);
    }

}
