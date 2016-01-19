package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckBusDuplicateOfRouteList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author root
 * @since 15.01.16
 */
public class CheckRouteListBusDuplicateValidator implements ConstraintValidator<CheckBusDuplicateOfRouteList, List<Route>> {
    @Override
    public void initialize(CheckBusDuplicateOfRouteList constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<Route> value, ConstraintValidatorContext context) {
        List<Route> result = new ArrayList<>();
        for (Route source : value) {
            for (Route target : result) {
                if (source.getBuses().containsAll(target.getBuses())) {
                    return false;
                }
            }
            result.add(source);
        }
        return true;
    }
}