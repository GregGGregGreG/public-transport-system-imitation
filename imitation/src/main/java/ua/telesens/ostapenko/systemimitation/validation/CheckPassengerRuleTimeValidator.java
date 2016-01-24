package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.PassengerGenerationRule;
import ua.telesens.ostapenko.systemimitation.validation.constraint.rule.station.CheckPassengerRuleTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.List;

/**
 * @author root
 * @since 16.01.16
 */
public class CheckPassengerRuleTimeValidator implements ConstraintValidator<CheckPassengerRuleTime, List<PassengerGenerationRule>> {

    @Override
    public void initialize(CheckPassengerRuleTime constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<PassengerGenerationRule> value, ConstraintValidatorContext context) {
        for (PassengerGenerationRule rule : value) {
            long start = rule.getStart().toNanoOfDay();
            long end = rule.getEnd().toNanoOfDay();
            long interval = rule.getInterval().toNanoOfDay();
            long min = LocalTime.of(3, 0).toNanoOfDay();

            if (start == end) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("\n"+ rule)
                        .addConstraintViolation();
                return false;
            }
            if (start < end && end - start < min) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("\n"+ rule)
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
