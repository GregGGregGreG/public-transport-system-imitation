package ua.telesens.ostapenko.systemimitation.validation.constraint;

import ua.telesens.ostapenko.systemimitation.validation.CheckImitationSourceTimeWaitingPassengerValidator;

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
 * @since 20.01.16
 */

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {CheckImitationSourceTimeWaitingPassengerValidator.class})
@Documented
public @interface CheckImitationSourceTimeWaitingPassenger {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
