package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.PassengerGenerationRule;
import ua.telesens.ostapenko.systemimitation.validation.constraint.rule.station.CheckPassengerRuleInterval;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.util.List;

/**
 * @author root
 * @since 16.01.16
 */
public class CheckPassengerRuleIntervalValidator implements ConstraintValidator<CheckPassengerRuleInterval, List<PassengerGenerationRule>> {

    private LocalTime min = LocalTime.of(0, 45);

    @Override
    public void initialize(CheckPassengerRuleInterval constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<PassengerGenerationRule> value, ConstraintValidatorContext context) {
        for (PassengerGenerationRule rule : value) {
            if (rule.getInterval().isAfter(min)) {
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
