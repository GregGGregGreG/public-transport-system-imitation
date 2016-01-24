package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;

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
public class RouteValidationTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkRouteValidatorIsNull() {
        Route item = Route.of(null,null,0,null,null,null,null);
        Set<ConstraintViolation<Route>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(6, constraintViolations.size());
        assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void checkRouteValidatorIsMin() {
        Route item = Route.of(null,null,-15,null,null,null,null);
        Set<ConstraintViolation<Route>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        assertEquals(7, constraintViolations.size());
        assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );
        constraintViolations.forEach(System.out::println);
    }
}
