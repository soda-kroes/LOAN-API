package com.java.year3.loan_api.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAgeValidator.class)
public @interface ValidAge {
    String message() default "Age must be between 18 and 65.";
    int minAge() default 18;
    int maxAge() default 65;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}