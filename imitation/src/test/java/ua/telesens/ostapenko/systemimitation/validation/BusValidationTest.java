package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.Bus;

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
public class BusValidationTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkBusValidatorMin() {
        Bus item = Bus.of(-1,-1);
        Set<ConstraintViolation<Bus>> constraintViolations = validator.validate(item, RouteListSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(2, constraintViolations.size());
        assertEquals(
                "must be greater than or equal toXML 0",
                constraintViolations.iterator().next().getMessage()
        );
    }
}
