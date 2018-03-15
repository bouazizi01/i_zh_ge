package ma.najeh.ibnouzouhr.validator;

import ma.najeh.ibnouzouhr.model.Admin;
import ma.najeh.ibnouzouhr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AdminValidator implements Validator {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean supports(Class<?> aClass) {
        return Admin.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required","ce champ est obligatoire");
        Admin admin=(Admin) o;

        if (userRepository.existsByUsername(admin.getUsername()) && admin.getId()==null){
            errors.rejectValue("username", "exist", "ce nom de l'utilisateur est deja exist");
        }
    }
}
