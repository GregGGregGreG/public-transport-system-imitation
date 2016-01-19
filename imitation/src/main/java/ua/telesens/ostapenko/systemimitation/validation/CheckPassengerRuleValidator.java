package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.PassengerGenerationRule;
import ua.telesens.ostapenko.systemimitation.validation.constraint.rule.station.CheckPassengerRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.List;

/**
 * @author root
 * @since 16.01.16
 */
public class CheckPassengerRuleValidator implements ConstraintValidator<CheckPassengerRule, List<PassengerGenerationRule>> {

    @Override
    public void initialize(CheckPassengerRule constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<PassengerGenerationRule> value, ConstraintValidatorContext context) {
        for (PassengerGenerationRule rule : value) {
            if (rule.getStart().equals(rule.getEnd())) {
                return false;
            }
            if (rule.getInterval().isAfter(LocalTime.of(0, 45))) {
                return false;
            }
        }
        return false;
    }
}
