package ua.telesens.ostapenko.systemimitation.validation;

import org.junit.Before;
import org.junit.Test;
import ua.telesens.ostapenko.systemimitation.model.internal.PassengerGenerationRule;
import ua.telesens.ostapenko.systemimitation.model.internal.PassengerGenerationRuleList;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static ua.telesens.ostapenko.systemimitation.model.internal.DayType.HOLIDAY;

/**
 * @author root
 * @since 15.01.16
 */
public class PassengerGenerationRuleListValidationTest {


    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkPassengerGenerationRuleListIsNull() {
        PassengerGenerationRuleList item = PassengerGenerationRuleList.of(null, null);
        Set<ConstraintViolation<PassengerGenerationRuleList>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(2, constraintViolations.size());
        constraintViolations.forEach(constraint -> assertEquals("may not be null", constraint.getMessage()));
    }

    @Test
    public void checkRulePassengerTime() {
        List<PassengerGenerationRule> workDayRulesOfGagarina = new ArrayList<>();
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                8, LocalTime.of(6, 0), LocalTime.of(6, 0), LocalTime.of(0, 15))
        );
//        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
//                4, LocalTime.of(9, 0), LocalTime.of(13, 0), LocalTime.of(0, 17))
//        );
//        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
//                2, LocalTime.of(13, 0), LocalTime.of(16, 0), LocalTime.of(0, 17))
//        );
//        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
//                8, LocalTime.of(16, 0), LocalTime.of(19, 0), LocalTime.of(0, 15))
//        );
//        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
//                4, LocalTime.of(19, 0), LocalTime.of(23, 0), LocalTime.of(0, 15))
//        );

        PassengerGenerationRuleList item = PassengerGenerationRuleList.of(HOLIDAY, workDayRulesOfGagarina);

        Set<ConstraintViolation<PassengerGenerationRuleList>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(1, constraintViolations.size());
        assertEquals("{incorrect rule passenger generation time}", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkRulePassengerInterval() {
        List<PassengerGenerationRule> workDayRulesOfGagarina = new ArrayList<>();
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                8, LocalTime.of(6, 0), LocalTime.of(6, 50), LocalTime.of(5, 15))
        );

        PassengerGenerationRuleList item = PassengerGenerationRuleList.of(HOLIDAY, workDayRulesOfGagarina);

        Set<ConstraintViolation<PassengerGenerationRuleList>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(1, constraintViolations.size());
        assertEquals("{incorrect rule passenger generation interval}", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkRulePassengerIntersection() {
        List<PassengerGenerationRule> workDayRulesOfGagarina = new ArrayList<>();
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                8, LocalTime.of(6, 0), LocalTime.of(9, 50), LocalTime.of(0, 15))
        );
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                8, LocalTime.of(5, 0), LocalTime.of(8, 50), LocalTime.of(0, 20))
        );
        workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                8, LocalTime.of(22, 0), LocalTime.of(4, 50), LocalTime.of(0, 20))
        );

        PassengerGenerationRuleList item = PassengerGenerationRuleList.of(HOLIDAY, workDayRulesOfGagarina);

        Set<ConstraintViolation<PassengerGenerationRuleList>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(1, constraintViolations.size());
        assertEquals("{rule passenger generation is intersection}", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkRulePassengerMinSize() {
        List<PassengerGenerationRule> workDayRulesOfGagarina = new ArrayList<>();
        PassengerGenerationRuleList item = PassengerGenerationRuleList.of(HOLIDAY, workDayRulesOfGagarina);

        Set<ConstraintViolation<PassengerGenerationRuleList>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 2 and 50", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkRulePassengerMaxSize() {
        List<PassengerGenerationRule> workDayRulesOfGagarina = new ArrayList<>();
        for (int i = 0; i < 51; i++) {
            workDayRulesOfGagarina.add(PassengerGenerationRule.of(
                    8, LocalTime.of(6, 0), LocalTime.of(9, 50), LocalTime.of(0, 15))
            );
        }


        PassengerGenerationRuleList item = PassengerGenerationRuleList.of(HOLIDAY, workDayRulesOfGagarina);

        Set<ConstraintViolation<PassengerGenerationRuleList>> constraintViolations = validator.validate(item, ImitationSourceSequence.class);
        constraintViolations.forEach(System.out::println);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 2 and 50", constraintViolations.iterator().next().getMessage());
    }
}
