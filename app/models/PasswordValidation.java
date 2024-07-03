package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.ValidateWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraints.ValidateWith(PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidation {
    String message() default "Password must be at least 8 characters long and contain uppercase, lowercase, digit, and special character";
}

