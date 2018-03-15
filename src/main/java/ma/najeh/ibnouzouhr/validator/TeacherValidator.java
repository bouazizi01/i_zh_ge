package ma.najeh.ibnouzouhr.validator;

import ma.najeh.ibnouzouhr.model.Teacher;
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
public class TeacherValidator implements Validator {
    @Autowired
    private TeacherService teacherService;


    @Override
    public boolean supports(Class<?> aClass) {
        return Teacher.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cin", "cin.required","ce champ est obligatoire");


        Teacher teacher=(Teacher) o;

        if (teacherService.existsByCin(teacher.getCin()) && teacher.getId()==null){
            errors.rejectValue("cin", "exist", "ce cin est deja exist");
        }
    }
}
