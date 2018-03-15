package ma.najeh.ibnouzouhr.validator;

import ma.najeh.ibnouzouhr.dto.Password;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Password.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "newPassword.required","ce champ est obligatoire");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retyNewPassword", "retyNewPassword.required","ce champ est obligatoire");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "retyNewPassword.required","ce champ est obligatoire");
        Password password=(Password) o;



        if (!password.getNewPassword().equals(password.getRetyNewPassword())){
            errors.rejectValue("newPassword", "exist", "ces mots de passe ne correspondent pas. Réessayer?");
            errors.rejectValue("retyNewPassword", "exist", "ces mots de passe ne correspondent pas. Réessayer?");
        }
    }
}
