package ua.telesens.ostapenko.systemimitation.validation;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteArc;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckSequenceRouteArc;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author root
 * @since 17.01.16
 */
public class CheckSequenceRouteArcValidator implements ConstraintValidator<CheckSequenceRouteArc, List<RouteArc>> {

    @Override
    public void initialize(CheckSequenceRouteArc constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<RouteArc> value, ConstraintValidatorContext context) {
        List<RouteArc> source = value
                .stream()
                .sorted((o1, o2) -> Integer.compare(o1.getNumber(), o2.getNumber()))
                .collect(Collectors.toList());

        PeekingIterator<RouteArc> iterator = Iterators.peekingIterator(source.iterator());
        List<RouteArc> result = new ArrayList<>();
        RouteArc arc;
        while (iterator.hasNext()) {
            arc = iterator.next();
            result.add(arc);
            if (iterator.hasNext()) {
                if (!arc.getEnd().equals(iterator.peek().getStart())) {
                    result.add(iterator.next());
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                            .addPropertyNode("\n"+Joiner.on(",\n").join(result))
                            .addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }
}
