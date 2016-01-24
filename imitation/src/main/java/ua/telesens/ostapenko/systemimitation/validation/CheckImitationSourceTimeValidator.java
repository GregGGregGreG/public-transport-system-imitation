package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckImitationSourceTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * @author root
 * @since 20.01.16
 */
public class CheckImitationSourceTimeValidator implements ConstraintValidator<CheckImitationSourceTime, ImitationSource> {

    @Override
    public void initialize(CheckImitationSourceTime constraintAnnotation) {

    }

    @Override
    public boolean isValid(ImitationSource value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        LocalDateTime end = value.getEnd();
        LocalDateTime starting = value.getStarting();
        if (starting.isAfter(end)) {
            context.buildConstraintViolationWithTemplate("start not is after end")
                    .addPropertyNode("Starting: " + starting + " Ending: " + end)
                    .addConstraintViolation();
            return false;
        }
        if (starting.equals(end)) {
            context.buildConstraintViolationWithTemplate("end not equal start")
                    .addPropertyNode("Starting: " + starting + " Ending: " + end)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
