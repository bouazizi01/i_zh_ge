package ma.najeh.ibnouzouhr.validator;

import ma.najeh.ibnouzouhr.model.Specialty;
import ma.najeh.ibnouzouhr.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SpecialtyValidator implements Validator {
    @Autowired
     private SpecialtyRepository specialtyRepository;
    @Override
    public boolean supports(Class<?> aClass) { return  Specialty.class.equals(aClass);}

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required","ce champ est obligatoire");
        Specialty specialty=(Specialty) o;

        if (specialtyRepository.existsByName(specialty.getName()) && specialty.getId()==null){
            errors.rejectValue("name", "exist", "ce nom est deja exist");
        }
    }
}
