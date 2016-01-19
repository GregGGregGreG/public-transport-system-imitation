package ua.telesens.ostapenko.systemimitation.validation.constraint.rule.station;

import ua.telesens.ostapenko.systemimitation.validation.CheckPassengerRuleIntersectionValidator;

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

@Target({FIELD,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckPassengerRuleIntersectionValidator.class)
@Documented
public @interface CheckPassengerRuleIntersection {

    String message() default "{rule  passenger generation is intersection}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
