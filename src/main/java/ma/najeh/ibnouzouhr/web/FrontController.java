package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.dto.StudentDto;
import ma.najeh.ibnouzouhr.model.Annonce;
import ma.najeh.ibnouzouhr.model.Student;
import ma.najeh.ibnouzouhr.repository.AnnonceRepository;
import ma.najeh.ibnouzouhr.repository.ConfigurationRepository;
import ma.najeh.ibnouzouhr.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by youssef on 1/10/18.
 */
@Controller
public class FrontController {
    final private AnnonceRepository annonceRepository;
    @Autowired private ConfigurationRepository configurationRepository;
    @Autowired private StudentRepository studentRepository;

    @Autowired
    public FrontController(AnnonceRepository annonceRepository) {
        this.annonceRepository = annonceRepository;
    }

    @GetMapping("/")
    public String home(Model model)
    {
        List<Annonce> annonces= (List<Annonce>) annonceRepository.findAll();
        model.addAttribute("annonces",annonces);
        return "/front/index";
    }
    @GetMapping("/inscription")
    public String inscription()
    {
        Date startDate =  new Date(configurationRepository.findByName("insc_start_date").getVal());
        Date endDate = new Date(configurationRepository.findByName("insc_end_date").getVal());
        Date  now = new Date();
      if (now.after(startDate) && now.before(endDate)){
          return "/front/inscription";
      }else {
          return "redirect:/";
      }



    }
    @PostMapping("/inscription")
    public String store(StudentDto dto)
    {
        Student student = new Student();
        student.setAdress(dto.getAdress());
        student.setBirthday(dto.getBirthday());
        student.setBirthProvince(dto.getBirthProvince());

        student.setCin(dto.getCin());
        student.setCne(dto.getCne());
        student.setCodeAPOGEE(dto.getCodeAPOGEE());

        student.setDiplome(dto.getDiplome());
        student.setFirstName(dto.getFirstName());
        student.setFirstNameAr(dto.getFirstNameAr());

        student.setLastName(dto.getLastName());
        student.setLastNameAr(dto.getLastNameAr());
        student.setHometown(dto.getHometown());
        UUID username = UUID.randomUUID();
        UUID password = UUID.randomUUID();
        student.setUsername(String.valueOf(username));
        student.setPassword(String.valueOf(password)+"-invalid");
        student.setJoinAt(new Date());
        studentRepository.save(student);
        return "redirect:/inscription";


    }
}
