package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteTrafficRule;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalTime;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

/**
 * @author root
 * @since 15.01.16
 */
public class RouteTrafficRuleValidationTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkRouteTrafficRuleListValidationIsNull() {
        RouteTrafficRule item = RouteTrafficRule.of(0, 0, null, null);
        Set<ConstraintViolation<RouteTrafficRule>> constraintViolations = validator.validate(item, RouteListSequence.class);
        constraintViolations.forEach(System.out::println);
        assertEquals(2, constraintViolations.size());
        constraintViolations.forEach(constraint -> assertEquals("may not be null", constraint.getMessage()));
    }

    @Test
    public void checkRouteTrafficRuleListValidationMin() {
        RouteTrafficRule item = RouteTrafficRule.of(-1, -1, LocalTime.now(), LocalTime.now());
        Set<ConstraintViolation<RouteTrafficRule>> constraintViolations = validator.validate(item, RouteListSequence.class);
        constraintViolations.forEach(System.out::println);
        assertEquals(2, constraintViolations.size());
        constraintViolations.forEach(constraint -> assertEquals("must be greater than or equal toXML 1", constraint.getMessage()));
    }
}
