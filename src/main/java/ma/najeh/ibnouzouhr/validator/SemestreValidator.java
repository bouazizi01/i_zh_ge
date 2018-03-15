package ma.najeh.ibnouzouhr.validator;

import ma.najeh.ibnouzouhr.model.Semestre;
import ma.najeh.ibnouzouhr.repository.SemestreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by youssef on 12/16/17.
 */
@Component
public class SemestreValidator implements Validator {
    private final SemestreRepository semestreRepository;

    @Autowired
    public SemestreValidator(SemestreRepository semestreRepository) {
        this.semestreRepository = semestreRepository;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Semestre.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required","ce champ est obligatoire");
        Semestre semestre=(Semestre) o;

        if (!(semestre.getName().charAt(0) == 'S' || semestre.getName().charAt(0) == 's') ){
            errors.rejectValue("name", "depart", "le nom du semestre doit commence par S");
        }

        if (semestreRepository.existsByName(semestre.getName()) && semestre.getId()==null){
            errors.rejectValue("name", "exist", "ce nom est deja exist");
        }



    }
}
