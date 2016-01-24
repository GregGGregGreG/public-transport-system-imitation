package ua.telesens.ostapenko.systemimitation.validation;

import javax.validation.GroupSequence;

/**
 * @author root
 * @since 15.01.16
 */
@GroupSequence({ ImitationSourceStep1.class, ImitationSourceStep2.class })
public interface ImitationSourceSequence {
}
