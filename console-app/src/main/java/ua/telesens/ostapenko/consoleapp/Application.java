package ua.telesens.ostapenko.consoleapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.telesens.ostapenko.conf.AppContext;
import ua.telesens.ostapenko.systemimitation.ImitationRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author root
 * @since 10.01.16
 */
@Configuration
@Import(AppContext.class)
public class Application {

    private static final String FATAL_ERROR_IN_CORE_OOPS = "Fatal error in core! OOPS";
    private static Logger log;

    public static void main(String[] args) throws IOException {
        new Bootstrap().installPatch();
        log = LoggerFactory.getLogger(Application.class);

        checkARGS(args);
        String path = "/home/greg/Program Data/WorkDirectory/IdeaProjects/public-transport-system-imitation/imitation/data/rout_2016-01-21T09:03:16.254Z.xml";
        run(path);
    }

    private static void checkARGS(String[] args) {
        int length = args.length;
        if (length == 0 || length > 1) {
            log.info("Non argument");
            System.exit(3);
        }
        Path file = Paths.get(args[0]);
        if (!Files.isRegularFile(file)) {
            log.info("File not regular");
            System.exit(3);
        }
        if (!Files.isReadable(file)) {
            log.info("File not readable");
            System.exit(3);
        }
        if (!Files.isExecutable(file)) {
            log.info("File not executable ");
            System.exit(3);
        }
    }

    private static void run(String path) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
            ImitationRunner runner = context.getBean(ImitationRunner.class);
            runner.run(path);
        } catch (Throwable e) {
            log.error(String.valueOf(e.getClass()), e);
            log.info(FATAL_ERROR_IN_CORE_OOPS);
        }
    }
}