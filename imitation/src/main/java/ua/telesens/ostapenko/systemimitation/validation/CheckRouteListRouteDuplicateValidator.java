package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckRouteDuplicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author root
 * @since 16.01.16
 */
public class CheckRouteListRouteDuplicateValidator implements ConstraintValidator<CheckRouteDuplicate, List<Route>> {

    @Override
    public void initialize(CheckRouteDuplicate constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<Route> value, ConstraintValidatorContext context) {
        return value.stream().distinct().collect(Collectors.toList()).size() >= value.size();
    }

}
