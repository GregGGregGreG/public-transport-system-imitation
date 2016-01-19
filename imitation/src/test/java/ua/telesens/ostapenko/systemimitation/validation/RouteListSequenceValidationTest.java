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
public class RouteListSequenceValidationTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkRouteListSequenceStepOneIsNull() {
        RouteList item = RouteListGenerator.getEmpty();
        Set<ConstraintViolation<RouteList>> constraintViolations = validator.validate(item, RouteListSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(3, constraintViolations.size());
        constraintViolations.forEach(constraint -> assertEquals("may not be null", constraint.getMessage()));
    }



}
