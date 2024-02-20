package com.haredev.library.user.controller.input.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target( { TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password is too weak";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
