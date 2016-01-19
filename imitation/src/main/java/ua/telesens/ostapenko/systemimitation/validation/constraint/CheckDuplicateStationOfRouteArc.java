package ua.telesens.ostapenko.systemimitation.validation.constraint;

import ua.telesens.ostapenko.systemimitation.validation.CheckDuplicateStationOfRouteArcValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author root
 * @since 17.01.16
 */
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckDuplicateStationOfRouteArcValidator.class)
@Documented
public @interface CheckDuplicateStationOfRouteArc {

    String message() default "duplicate route in route arc'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
