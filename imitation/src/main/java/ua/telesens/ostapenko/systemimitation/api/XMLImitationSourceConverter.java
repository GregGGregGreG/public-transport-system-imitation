package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;

/**
 * @author root
 * @since 18.01.16
 */
public interface XMLImitationSourceConverter {

    String toXML(ImitationSource source,String path);

    ImitationSource fromXML(String xml);

}
