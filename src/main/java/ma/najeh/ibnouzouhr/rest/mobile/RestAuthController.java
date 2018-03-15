package ma.najeh.ibnouzouhr.rest.mobile;

import ma.najeh.ibnouzouhr.model.User;
import ma.najeh.ibnouzouhr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by youssef on 1/18/18.
 */
@RestController
public class RestAuthController {
    private final UserService userService;

    @Autowired
    public RestAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/rest/login")
    public ResponseEntity<?> login(@RequestBody User u){
        User user=userService.getByUsername(u.getUsername());
        if (user==null){
            return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);
        }
        userService.updateLoginDate(new Date(),user.getId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
