package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.Station;

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
public class StationValidationTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkStationValidatorIsNull() {
        Station item = Station.of(null,null);
        Set<ConstraintViolation<Station>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        assertEquals(2, constraintViolations.size());
        assertEquals(
                "may not be null",
                constraintViolations.iterator().next().getMessage()
        );
        constraintViolations.forEach(System.out::println);
    }
}
