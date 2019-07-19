package com.tim.dubbo.sample.jsr303;

import com.tim.dubbo.sample.Person;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.engine.PathImpl;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
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
//        p.setId(100L);
//        p.setName("hahaha");

        List<Person> personList = new ArrayList<>();
        personList.add(p);

        Person p2 = new Person();
        p2.setAge(5);
        personList.add(p2);
        P pObj = new P();
        pObj.personList = personList;
        Set<ConstraintViolation<P>> result = validator.validate(pObj, Person.AddPerson.class);
        result.forEach(c -> {
            String path = ((PathImpl)c.getPropertyPath()).getLeafNode().getName().toString();
            System.out.println(path);
            String message = c.getMessage();
            System.out.println(message);
        });
        System.out.println(result);
    }

    static class P {
        @Valid
        @NotEmpty
        private List<Person> personList;

    }}
