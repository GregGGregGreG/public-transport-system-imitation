package ua.telesens.ostapenko.systemimitation.validation;

import ua.telesens.ostapenko.systemimitation.model.internal.RouteTrafficRuleList;
import ua.telesens.ostapenko.systemimitation.validation.constraint.rule.route.CheckRouteTrafficRuleListDuplicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

import static ua.telesens.ostapenko.systemimitation.model.internal.DayType.HOLIDAY;
import static ua.telesens.ostapenko.systemimitation.model.internal.DayType.WORKDAY;

/**
 * @author root
 * @since 19.01.16
 */
public class CheckRouteTrafficRuleListDuplicateValidator implements ConstraintValidator<CheckRouteTrafficRuleListDuplicate, List<RouteTrafficRuleList>> {


    @Override
    public void initialize(CheckRouteTrafficRuleListDuplicate constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<RouteTrafficRuleList> value, ConstraintValidatorContext context) {
        if (getCountRuleOfWorkDay(value) > 1) {
            return false;
        } else if (getCountRuleOfHoliday(value) > 1) {
            return false;
        }
        return true;
    }

    private int getCountRuleOfWorkDay(List<RouteTrafficRuleList> value) {
        return value
                .stream()
                .filter(rules -> rules.getDayType().equals(WORKDAY))
                .collect(Collectors.toList())
                .size();
    }

    private int getCountRuleOfHoliday(List<RouteTrafficRuleList> value) {
        return value
                .stream()
                .filter(rules -> rules.getDayType().equals(HOLIDAY))
                .collect(Collectors.toList())
                .size();
    }
}
