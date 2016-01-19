package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckRouteNameDuplicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author root
 * @since 16.01.16
 */
public class CheckRouteListNameDuplicateValidator implements ConstraintValidator<CheckRouteNameDuplicate, List<Route>> {
    @Override
    public void initialize(CheckRouteNameDuplicate constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<Route> value, ConstraintValidatorContext context) {
        List<Route> result = new ArrayList<>();
        for (Route source : value) {
            for (Route target : result) {
                if (source.getName().equals(target.getName())) {
                    return false;
                }
            }
            result.add(source);
        }
        return true;
    }
}
