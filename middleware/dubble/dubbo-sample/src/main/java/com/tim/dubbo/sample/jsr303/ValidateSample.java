package com.tim.dubbo.sample.jsr303;

import com.tim.dubbo.sample.Person;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidateSample {

    public static void main(String[] args) {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Person p = new Person();
        Set<ConstraintViolation<Person>> validate = validator.validate(p, Person.UpdatePerson.class);
        System.out.println(validate);
        p.setId(100L);
        validate = validator.validate(p, Person.UpdatePerson.class);
        System.out.println(validate);
    }
}
