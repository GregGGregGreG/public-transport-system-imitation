package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.util.RouteListGenerator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

/**
 * @author root
 * @since 15.01.16
 */
public class RouteListValidationTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkRouteListValidatorIsNull() {
        RouteList item = RouteList.of(null);
        Set<ConstraintViolation<RouteList>> constraintViolations = validator.validate(item, RouteListSequence.class);
        constraintViolations.forEach(System.out::println);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void checkRouteListValidatorCheckDuplicateRoute() {
        RouteList item = RouteListGenerator.getDuplicate();
        Set<ConstraintViolation<RouteList>> constraintViolations = validator.validate(item, RouteListSequence.class);
        constraintViolations.forEach(System.out::println);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void checkRouteListValidatorCheckDuplicateName() {
        RouteList item = RouteListGenerator.getDuplicate();
        Set<ConstraintViolation<RouteList>> constraintViolations = validator.validate(item, RouteListSequence.class);
        constraintViolations.forEach(System.out::println);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "duplicate name in rout list",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void checkRouteListValidatorCheckSequenceRouteArc() {
        RouteList item = RouteListGenerator.get();
        Set<ConstraintViolation<RouteList>> constraintViolations = validator.validate(item, RouteListSequence.class);
        constraintViolations.forEach(System.out::println);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "",
                constraintViolations.iterator().next().getMessage()
        );
    }


}
