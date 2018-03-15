package ma.najeh.ibnouzouhr.service;

import ma.najeh.ibnouzouhr.constant.Constant;
import ma.najeh.ibnouzouhr.model.Teacher;
import ma.najeh.ibnouzouhr.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by youssef on 12/15/17.
 */

@Service("TeacherService")
public class TeacherService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher findOne(Long id) {
        return teacherRepository.findOne(id);
    }

    public List<Teacher> findAll() {
        return (List<Teacher>) teacherRepository.findAll();
    }

    public Teacher save(Teacher teacher) {
        teacher.setJoinAt(new Date());
        String username=teacher.getEmailPro();
        String password=teacher.getCin();
        teacher.setUsername(username.trim());
        teacher.setRole(Constant.ROLE.TEACHER);
        teacher.setPassword(passwordEncoder.encode(password).toCharArray());
        return teacherRepository.save(teacher);
    }

    public Teacher update(Teacher teacher) {
        return teacherRepository.save(teacher);
    }


    public void delete(Long id) {
        teacherRepository.delete(id);
    }

    public boolean existsByCin(String cin) {
        return teacherRepository.existsByCin(cin);
    }


    public void save(Set<Teacher> teachers) {
        for (Teacher teacher:teachers){
            save(teacher);
        }
    }
}
