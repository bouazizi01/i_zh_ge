package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.model.Admin;
import ma.najeh.ibnouzouhr.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.SCOLARITY;

/**
 * Created by youssef on 12/15/17.
 */
@Service("AdminService")
public class AdminService  {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public Admin save(Admin admin){
        char[] password = passwordEncoder.encode(String.valueOf(admin.getPassword())).toCharArray();
        admin.setPassword(password);
        admin.setRole(SCOLARITY);
        admin.setJoinAt(new Date());
        return adminRepository.save(admin);
    }

    public Iterable<Admin> findAll() {
        return adminRepository.findAll();
    }

    public void delete(Long id) {
        adminRepository.delete(id);
    }

    public Admin findOne(Long id) {
        return adminRepository.findOne(id);
    }
}
