package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.validation.constraint.RoutePrice;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author root
 * @since 17.01.16
 */
public class CheckRoutePriceValidator implements ConstraintValidator<RoutePrice, Double> {

    private double min;
    private double max;

    @Override
    public void initialize(RoutePrice constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= min && value <= max;
    }

}
