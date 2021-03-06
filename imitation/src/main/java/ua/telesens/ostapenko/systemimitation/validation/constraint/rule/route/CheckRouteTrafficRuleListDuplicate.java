package ua.telesens.ostapenko.systemimitation.validation.constraint.rule.route;

import ua.telesens.ostapenko.systemimitation.validation.CheckRouteTrafficRuleListDuplicateValidator;

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
 * @since 19.01.16
 */
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {CheckRouteTrafficRuleListDuplicateValidator.class})
@Documented
public @interface CheckRouteTrafficRuleListDuplicate {
    String message() default "{duplicate rule route traffic}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
