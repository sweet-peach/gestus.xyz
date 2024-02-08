package xyz.gestus.gestus.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import xyz.gestus.gestus.validation.RoleValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {
    String message() default "Invalid role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
