package ma.najeh.ibnouzouhr.rest.mobile;

import ma.najeh.ibnouzouhr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youssef on 1/18/18.
 */
@RestController
public class RestUserController {
    private final StudentService studentService;

    @Autowired
    public RestUserController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/rest/student/code/{code}")
    public ResponseEntity<?> getByCodeApogee(@PathVariable("code") String code){
        return new ResponseEntity<>(studentService.getByCodeAPOGEE(code), HttpStatus.OK);
    }
}
