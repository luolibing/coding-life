package cn.tim.validate.validator;


import cn.tim.validate.entity.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by LuoLiBing on 15/10/8.
 */
@Component
public class FormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        validateName(errors, person);
    }

    private void validateName(Errors errors, Person form) {
        if (form.getName().startsWith("admin")) {
            //errors.reject("name", "invalid username");
            errors.reject("person.name", "invalid username");
        }
    }
}
