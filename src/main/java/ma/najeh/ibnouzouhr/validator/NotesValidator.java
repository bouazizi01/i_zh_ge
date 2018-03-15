package ma.najeh.ibnouzouhr.validator;

import ma.najeh.ibnouzouhr.dto.NoteCreation;
import ma.najeh.ibnouzouhr.model.Note;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class NotesValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Note.class.equals(aClass);
    }
    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "note", "note.required","ce champ est obligatoire");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year", "year.required","ce champ est obligatoire");
        NoteCreation note=(NoteCreation) o;
        if ((note.getNote()<=0 || note.getNote()>=20)){
            errors.rejectValue("note", " la note doit etre entre 0 et 20 ");
        }
    }
}
