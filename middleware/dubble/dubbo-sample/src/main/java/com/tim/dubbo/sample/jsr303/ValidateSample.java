package com.tim.dubbo.sample.jsr303;

import com.tim.dubbo.sample.Person;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidateSample {

    public static void main(String[] args) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Person p = new Person();
        Set<ConstraintViolation<Person>> validate = validator.validate(p, Person.UpdatePerson.class);
        System.out.println(validate);
        p.setId(100L);
        validate = validator.validate(p, Person.UpdatePerson.class);
        System.out.println(validate);
    }
}
