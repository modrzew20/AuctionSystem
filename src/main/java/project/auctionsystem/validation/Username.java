package project.auctionsystem.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

@Constraint(validatedBy = {})
@NotBlank
@Size(min = 3, max = 100)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Username {

    String message() default "Username must be between 3 and 100 characters long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
