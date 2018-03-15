package ma.najeh.ibnouzouhr.validator;


import ma.najeh.ibnouzouhr.model.Branch;
import ma.najeh.ibnouzouhr.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BranchValidator implements Validator {
     @Autowired
     private BranchRepository branchRepository;
    @Override

    public boolean supports(Class<?> aClass) {

        return Branch.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Branch branch=(Branch) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required","ce champ est obligatoire");

        if (branchRepository.existsByName(branch.getName())) {
            errors.rejectValue("name", "exist", "ce nom est deja exist");
        }

    }
}