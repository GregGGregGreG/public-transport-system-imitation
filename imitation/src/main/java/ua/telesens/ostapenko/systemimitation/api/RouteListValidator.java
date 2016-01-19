package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;

import javax.validation.ValidationException;


/**
 * @author root
 * @since 16.01.16
 */
public interface RouteListValidator {

    void validate(RouteList routeList) throws ValidationException;
}
