package ma.najeh.ibnouzouhr.web;

import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import net.sf.jasperreports.components.barcode4j.QRCodeImageProducer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.*;
import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */
@Controller
public class AuthController {


    @GetMapping(value = {DASHBOARD})
    public String home(){
        UserDetailsDto userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String url="";
        switch (userDetails.getRole()){
            case  STUDENT:
                url=DEFAULT_STUDENT_URL;
                break;
            case  TEACHER:
                url=DEFAULT_TEACHER_URL;
                break;
            case  SCOLARITY:
                url=DEFAULT_SCOLARITY_URL;
                break;
            case  SUPER_ADMIN:
                url=DEFAULT_ADMIN_URL;
                break;
        }
        return "redirect:"+url;
    }


    @GetMapping("/403")
    public String accessDenied() {
        return "/403";
    }


    @GetMapping("/login")
    public String login() {
        return "/login";
    }


}
