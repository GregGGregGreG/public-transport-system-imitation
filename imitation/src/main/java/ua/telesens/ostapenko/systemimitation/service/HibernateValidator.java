package ua.telesens.ostapenko.systemimitation.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.metamodel.ValidationException;
import ua.telesens.ostapenko.systemimitation.api.RouteListValidator;
import ua.telesens.ostapenko.systemimitation.exeption.RouteListValidationException;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;
import ua.telesens.ostapenko.systemimitation.validation.RouteListSequence;

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
    public void validate(RouteList routeList) {
        log.info("Validation rout");
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<RouteList>> constraintViolations = validator.validate(routeList, RouteListSequence.class);
            if (constraintViolations.size() > 0) {
                RouteListValidationException e = new RouteListValidationException("Error validation route");
                constraintViolations
                        .forEach(constrain -> e.add(constrain.getMessage() + "\t" + constrain.getPropertyPath()));
                throw e;
            }
        } catch (ValidationException e) {
            throw new RouteListValidationException("Error validation route", e);
        }
        log.debug("Done Validation rout");
    }
}
