package ua.telesens.ostapenko.consoleapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import ua.telesens.ostapenko.conf.AppContext;
import ua.telesens.ostapenko.systemimitation.ImitationRunner;

import java.io.IOException;

/**
 * @author root
 * @since 10.01.16
 */
@Configuration
@Import(AppContext.class)
@Component
public class Application {

    private static final String FATAL_ERROR_IN_CORE_OOPS = "Fatal error in core! OOPS";
    private static Logger log;

    public static void main(String[] args) throws IOException {
        new Bootstrap().installPatch();
        log = LoggerFactory.getLogger(Application.class);
        new Application().run();
    }

    public void run() {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
            ImitationRunner runner = context.getBean(ImitationRunner.class);
            runner.run();
        } catch (Throwable e) {
            log.error(String.valueOf(e.getClass()), e);
            log.info(FATAL_ERROR_IN_CORE_OOPS);
        }
    }
}