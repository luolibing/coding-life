package com.tim.router.domain;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Router {

    int order() default 0;
}
