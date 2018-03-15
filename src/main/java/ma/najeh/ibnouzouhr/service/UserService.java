package ma.najeh.ibnouzouhr.service;
import ma.najeh.ibnouzouhr.model.SuperAdmin;
import ma.najeh.ibnouzouhr.model.User;
import ma.najeh.ibnouzouhr.model.UserConfig;
import ma.najeh.ibnouzouhr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by youssef on 12/15/17.
 */
@Service("UserService")
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void init( PasswordEncoder passwordEncoder){
        if (userRepository.count() == 0) {
            SuperAdmin superAdmin = new SuperAdmin("admin", passwordEncoder.encode("123456"), "Sir Admin");
            UserConfig userConfig = new UserConfig("najeh.config", passwordEncoder.encode("123456"), "Najeh","Config");
            userRepository.save(userConfig);
            userRepository.save(superAdmin);
        }
    }
    public User findOne(Long id) {
        return  userRepository.findOne(id);
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public int updateLoginDate(Date date, Long id) {
        return userRepository.updateLoginDate(date,id);
    }

    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }
}
