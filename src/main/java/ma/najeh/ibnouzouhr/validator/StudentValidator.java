package ma.najeh.ibnouzouhr.validator;

import ma.najeh.ibnouzouhr.model.Student;
import ma.najeh.ibnouzouhr.model.Teacher;
import ma.najeh.ibnouzouhr.service.StudentService;
import ma.najeh.ibnouzouhr.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by youssef on 12/16/17.
 */
@Component
public class StudentValidator implements Validator {
    @Autowired
    private StudentService studentService;


    @Override
    public boolean supports(Class<?> aClass) {
        return Student.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cne", "cne.required","ce champ est obligatoire");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cin", "cin.required","ce champ est obligatoire");
        Student student=(Student) o;

        if (studentService.existsByCne(student.getCne()) && student.getId()==null){
            errors.rejectValue("cne", "exist", "ce cne est deja exist");
        }

        if (studentService.existsByCin(student.getCin()) && student.getId()==null){
            errors.rejectValue("cin", "exist", "ce cin est deja exist");
        }
    }
}
