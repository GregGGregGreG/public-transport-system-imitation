package ua.telesens.ostapenko.systemimitation.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.metamodel.ValidationException;
import ua.telesens.ostapenko.systemimitation.api.RouteListValidator;
import ua.telesens.ostapenko.systemimitation.exeption.ImitationSourceValidationException;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceSequence;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author root
 * @since 16.01.16
 */
@Slf4j
public final class HibernateValidator implements RouteListValidator {

    // FIXME: 16.01.16 Handler validation exception
    @Override
    public void validate(ImitationSource routeList) {
        log.info("Validation imitation source");
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ImitationSource>> constraintViolations = validator.validate(routeList, ImitationSourceSequence.class);
            if (constraintViolations.size() > 0) {
                ImitationSourceValidationException e = new ImitationSourceValidationException("Error validation imitation source");
                constraintViolations
                        .forEach(constrain -> e.add(constrain.getMessage() + "\t" + constrain.getPropertyPath()));
                throw e;
            }
        } catch (ValidationException e) {
            throw new ImitationSourceValidationException("Error validation imitation source", e);
        }
        log.debug("Done validation imitation source");
    }
}
