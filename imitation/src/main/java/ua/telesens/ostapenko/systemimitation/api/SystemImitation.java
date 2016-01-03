package ua.telesens.ostapenko.systemimitation.api;

/**
 * @author root
 * @since 05.12.15
 */
public interface SystemImitation {

    Report run();

    void stop();

    void pause();

    void status();

}
