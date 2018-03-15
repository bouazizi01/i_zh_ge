package ma.najeh.ibnouzouhr;

import ma.najeh.ibnouzouhr.model.Day;
import ma.najeh.ibnouzouhr.service.DayService;
import ma.najeh.ibnouzouhr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

/**
 * Created by youssef et jaouad el aoud on 12/3/17.
 */

@SpringBootApplication
public class IbnouZouhrApplication extends SpringBootServletInitializer {
    @Autowired
    private  UserService userService;
    @Autowired
    private  DayService dayService;
    @Autowired
    private  PasswordEncoder passwordEncoder;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {
        SpringApplication.run(IbnouZouhrApplication.class, args);

    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IbnouZouhrApplication.class);
    }




    @PostConstruct
    public void start() {
        System.out.println("PostConstruct start ....... ");
        // put some use
        if(dayService.count()==0){
            for (int i=0;i< Day.daysOfWeek().size();i++){
                dayService.save(Day.daysOfWeek().get(i));
            }
        }
        userService.init(passwordEncoder);
        System.out.println("PostConstruct end ....... ");
    }

    private static final Logger logger = LoggerFactory.getLogger(IbnouZouhrApplication.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //@Scheduled(fixedRate = 604_800_000) every week
    @Scheduled(fixedRate = 20000)
    public void scheduleTaskWithFixedRate() {
        //logger.info("Excution For Cleaning Note", dateFormat.format(new Date()) );
    }
}
