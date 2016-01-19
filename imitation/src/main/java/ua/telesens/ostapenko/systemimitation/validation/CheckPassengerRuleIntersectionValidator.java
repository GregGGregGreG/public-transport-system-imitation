package ua.telesens.ostapenko.systemimitation.validation;

import com.google.common.base.Joiner;
import ua.telesens.ostapenko.systemimitation.model.internal.PassengerGenerationRule;
import ua.telesens.ostapenko.systemimitation.validation.constraint.rule.station.CheckPassengerRuleIntersection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author root
 * @since 16.01.16
 */
public class CheckPassengerRuleIntersectionValidator implements ConstraintValidator<CheckPassengerRuleIntersection, List<PassengerGenerationRule>> {


    @Override
    public void initialize(CheckPassengerRuleIntersection constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<PassengerGenerationRule> value, ConstraintValidatorContext context) {

        List<PassengerGenerationRule> result = new ArrayList<>();
        for (PassengerGenerationRule source : value) {
            for (PassengerGenerationRule target : result) {
                if (intersection(source, target)) {
                    result.add(source);
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                            .addPropertyNode("\n"+Joiner.on(",\n").join(result))
                            .addConstraintViolation();
                    return false;
                }
            }
            result.add(source);
        }
        return true;
    }

    // FIXME: 16.01.16 Write rule from intersection passenger generation rule
    private boolean intersection(PassengerGenerationRule source, PassengerGenerationRule target) {
        return isEqual(source, target) || isBelongToInterval(source, target) || isBelongToInterval(target, source);
    }

    private boolean isEqual(PassengerGenerationRule source, PassengerGenerationRule target) {
        return source.getStart().equals(target.getStart()) && source.getEnd().equals(target.getEnd());
    }

    private boolean isBelongToInterval(PassengerGenerationRule source, PassengerGenerationRule target) {
        long start = source.getStart().toSecondOfDay();
        long end = source.getEnd().toSecondOfDay();
        long numOne = target.getStart().toSecondOfDay();
        long numTwo = target.getEnd().toSecondOfDay();
        return isBelongToInterval(start, end, numOne) || isBelongToInterval(start, end, numTwo);
    }

    private boolean isBelongToInterval(long start, long end, long numOne) {
        return numOne > start && numOne < end;
    }
}
