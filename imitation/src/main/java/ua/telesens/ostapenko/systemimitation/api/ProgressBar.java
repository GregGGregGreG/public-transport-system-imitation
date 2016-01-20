package ua.telesens.ostapenko.systemimitation.api;

import java.time.LocalDateTime;

/**
 * @author root
 * @since 20.01.16
 */
public interface ProgressBar {
    void start();

    void step(LocalDateTime imitationTime);
}
