package ua.telesens.ostapenko;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.telesens.ostapenko.conf.PersistenceContext;
import ua.telesens.ostapenko.systemimitation.ImitationRunner;

import java.io.IOException;

/**
 * @author root
 * @since 10.01.16
 */
@Configuration
@ComponentScan(basePackages = {"ua.telesens.ostapenko"})
@Import(PersistenceContext.class)

public class Application {

    private static final String FATAL_ERROR_IN_CORE_OOPS = "Fatal error in core! OOPS";

    public static void main(String[] args) throws IOException {
        new Bootstrap().run();

        Logger log = LoggerFactory.getLogger(Application.class);

        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
            ImitationRunner runner = context.getBean(ImitationRunner.class);
            String path = "/home/greg/Program Data/WorkDirectory/IdeaProjects/public-transport-system-imitation/imitation/data/rout_2016-01-21T09:03:16.254Z.xml";
            runner.run(path);
        } catch (Throwable e) {
            log.error(String.valueOf(e.getClass()), e);
            log.info(FATAL_ERROR_IN_CORE_OOPS);
        }
    }
}