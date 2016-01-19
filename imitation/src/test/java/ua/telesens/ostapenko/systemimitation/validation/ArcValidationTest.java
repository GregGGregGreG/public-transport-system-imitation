package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteArc;

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
public class ArcValidationTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkArcIsNull() {
        RouteArc item = RouteArc.of(1, null, null, null);
        Set<ConstraintViolation<RouteArc>> constraintViolations = validator.validate(item, RouteListSequence.class);
        assertEquals(3, constraintViolations.size());
        assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );
        constraintViolations.forEach(System.out::println);
    }

}
