package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteArc;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

/**
 * @author root
 * @since 15.01.16
 */
public class ArcValidationTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkArcIsNull() {
        RouteArc item = RouteArc.of(Route.MIN_COUNT_ARC, null, null, null);
        Set<ConstraintViolation<RouteArc>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        assertEquals(3, constraintViolations.size());
        constraintViolations.forEach(constraint -> assertEquals("may not be null", constraint.getMessage()));
    }

    @Test
    public void checkArcMinNumber() {
        int number = Route.MIN_COUNT_ARC;
        RouteArc item = RouteArc.of(--number, null, null, null);
        Set<ConstraintViolation<RouteArc>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        Set<ConstraintViolation<RouteArc>> buff = constraintViolations
                .stream()
                .filter(constraint -> constraint.getPropertyPath().toString().equals("number"))
                .collect(Collectors.toSet());
        assertEquals(1, buff.size());
        assertEquals("must be between 1 and 25", buff.iterator().next().getMessage());
    }

    @Test
    public void checkArcMaxNumber() {
        int number = Route.MAX_COUNT_ARC;
        RouteArc item = RouteArc.of(++number, null, null, null);
        Set<ConstraintViolation<RouteArc>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        Set<ConstraintViolation<RouteArc>> buff = constraintViolations
                .stream()
                .filter(constraint -> constraint.getPropertyPath().toString().equals("number"))
                .collect(Collectors.toSet());
        assertEquals(1, buff.size());
        assertEquals("must be between 1 and 25", buff.iterator().next().getMessage());
    }

}
