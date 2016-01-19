package ua.telesens.ostapenko.systemimitation.validation.constraint;

import ua.telesens.ostapenko.systemimitation.validation.CheckRoutePriceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author root
 * @since 17.01.16
 */

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {CheckRoutePriceValidator.class})
@Documented
public @interface RoutePrice {

    String message() default "price must be between '{min}' and '{max}'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double min() default 0;

    double max() default 100.0;

}
