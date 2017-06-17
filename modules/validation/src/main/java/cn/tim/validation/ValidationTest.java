package cn.tim.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * User: luolibing
 * Date: 2017/6/7 20:01
 */
public class ValidationTest {

    public static void main(String[] args) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Person>> constraintViolations
                = validator.validate(new Person());
        System.out.println(constraintViolations);

        for(ConstraintViolation<Person> constraintViolation : constraintViolations) {
            throw new RuntimeException(constraintViolation.getMessage());
        }
    }
}
