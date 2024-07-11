package models;

import play.data.validation.Constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValidation {
    String message() default "Password must be at least 6 characters long, contain at least one uppercase letter, one lowercase letter, and one special character";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
