package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.RouteArc;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckDuplicateStationOfRouteArc;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author root
 * @since 17.01.16
 */
public class CheckDuplicateStationOfRouteArcValidator implements ConstraintValidator<CheckDuplicateStationOfRouteArc, List<RouteArc>> {
    @Override
    public void initialize(CheckDuplicateStationOfRouteArc constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<RouteArc> value, ConstraintValidatorContext context) {
        for (RouteArc arc : value) {
            if (checkDuplicate(arc)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDuplicate(RouteArc arc) {
        return arc.getStart().equals(arc.getEnd());
    }
}
