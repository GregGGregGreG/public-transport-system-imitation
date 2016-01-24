package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckImitationSourceTimeWaitingPassenger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author root
 * @since 20.01.16
 */
public class CheckImitationSourceTimeWaitingPassengerValidator implements ConstraintValidator<CheckImitationSourceTimeWaitingPassenger, ImitationSource> {

    @Override
    public void initialize(CheckImitationSourceTimeWaitingPassenger constraintAnnotation) {

    }

    // FIXME: 20.01.16 Fix my comment
    @Override
    public boolean isValid(ImitationSource value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        int min = value.getMinTimePassengerWaiting();
        int max = value.getMaxTimePassengerWaiting();
        if (min > max) {
            context.buildConstraintViolationWithTemplate("min time waiting passenger not is more max")
                    .addPropertyNode("Min: " + max + " Max: " + min)
                    .addConstraintViolation();
            return false;
        }
        if (max == min) {
            context.buildConstraintViolationWithTemplate("min time waiting passenger not equal max")
                    .addPropertyNode("Min: " + max + " Max: " + min)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
