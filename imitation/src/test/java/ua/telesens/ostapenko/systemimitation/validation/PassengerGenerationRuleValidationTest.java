package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.PassengerGenerationRule;

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
public class PassengerGenerationRuleValidationTest {


    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkPassengerGenerationRuleIsNull() {
        PassengerGenerationRule item = PassengerGenerationRule.of(2, null, null, null);
        Set<ConstraintViolation<PassengerGenerationRule>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(3, constraintViolations.size());
        constraintViolations.forEach(constraint -> assertEquals("may not be null", constraint.getMessage()));
    }

    @Test
    public void checkPassengerGenerationRuleMinCount() {
        PassengerGenerationRule item = PassengerGenerationRule.of(0, LocalTime.now(), LocalTime.now(), LocalTime.now());
        Set<ConstraintViolation<PassengerGenerationRule>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(1, constraintViolations.size());
        constraintViolations.forEach(constraint -> assertEquals("must be greater than or equal toXML 1", constraint.getMessage()));
    }

    @Test
    public void checkPassengerGenerationRuleMax() {
        PassengerGenerationRule item = PassengerGenerationRule.of(1001, LocalTime.now(), LocalTime.now(), LocalTime.now());
        Set<ConstraintViolation<PassengerGenerationRule>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(1, constraintViolations.size());
        constraintViolations.forEach(constraint -> assertEquals("must be less than or equal toXML 1000", constraint.getMessage()));
    }
}
