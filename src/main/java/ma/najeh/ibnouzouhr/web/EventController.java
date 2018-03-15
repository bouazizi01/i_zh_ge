package ma.najeh.ibnouzouhr.web;


import ma.najeh.ibnouzouhr.model.Annonce;
import ma.najeh.ibnouzouhr.model.Event;
import ma.najeh.ibnouzouhr.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

@Controller
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping(SCOLARITY_EVENT_ADD)
    public String add() {
        return "/events/add";
    }

    @PostMapping(SCOLARITY_EVENT_ADD)
    public String save(Event event){
        eventRepository.save(event);
        return "/events/add";
    }

    @GetMapping({SCOLARITY_EVENT_ALL,SCOLARITY_EVENT})
    public String all(Model model){
        List<Event> events= (List<Event>) eventRepository.findAll();
        model.addAttribute("events",events);
        return "events/all";
    }

    @GetMapping(SCOLARITY_EVENT_DELETE)
    public ModelAndView delete(ModelMap model, @PathVariable(value="id") Long id) {
        eventRepository.delete(id);
        return new ModelAndView("redirect:"+SCOLARITY_EVENT_ALL, model);

    }
    @GetMapping(SCOLARITY_EVENT_EDIT)
    public String edit(Map<String, Object> model, @PathVariable(value="id") Long id) {
        Event event=eventRepository.findOne(id);
        model.put("event",event);
        return "/events/edit";
    }

    @PostMapping(SCOLARITY_EVENT_UPDATE)
    public ModelAndView update(ModelMap model,Event event) {
        eventRepository.save(event);
        return new ModelAndView("redirect:"+SCOLARITY_EVENT_ALL, model);
    }
}
