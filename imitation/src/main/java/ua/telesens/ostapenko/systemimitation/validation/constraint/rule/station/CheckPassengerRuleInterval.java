package ua.telesens.ostapenko.systemimitation.validation.constraint.rule.station;

import ua.telesens.ostapenko.systemimitation.validation.CheckPassengerRuleIntervalValidator;

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
 * @since 16.01.16
 */

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {CheckPassengerRuleIntervalValidator.class})
@Documented
public @interface CheckPassengerRuleInterval {

    String message() default "{incorrect rule passenger generation interval}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
