package ma.najeh.ibnouzouhr.rest.mobile;

import ma.najeh.ibnouzouhr.model.ExamPresent;
import ma.najeh.ibnouzouhr.service.ExamPresentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by youssef on 1/27/18.
 */
@RestController
public class RestExamPresentController {
    private final ExamPresentService examPresentService;
    private Logger logger=Logger.getLogger(RestExamPresentController.class);

    @Autowired
    public RestExamPresentController(ExamPresentService examPresentService) {
        this.examPresentService = examPresentService;
    }

    @PostMapping("/rest/exam-present")
    public ResponseEntity<?> save(@RequestBody ExamPresent examPresent){
        examPresent.setCreatedAt(new Date());
        logger.info(examPresent.toString());
        examPresent=examPresentService.save(examPresent);
        return new ResponseEntity<>(examPresent, HttpStatus.OK);
    }
}
