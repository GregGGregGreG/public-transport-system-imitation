package ua.telesens.ostapenko;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.telesens.ostapenko.conf.PersistenceContext;
import ua.telesens.ostapenko.systemimitation.ImitationRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author root
 * @since 10.01.16
 */
@Configuration
@ComponentScan(basePackages = {"ua.telesens.ostapenko"})
@Import(PersistenceContext.class)
public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        ImitationRunner runner = context.getBean(ImitationRunner.class);

        LocalDateTime STARTING = LocalDateTime.of(2015, Month.NOVEMBER, 12, 6, 0);
        LocalDateTime END = LocalDateTime.of(2015, Month.NOVEMBER, 15, 23, 50);
        runner.run("imitation/data/rout_2016-01-15T11:08:31.471Z.xml", STARTING, END);
//        runner.run("imitation/data/rout_2016-01-14T13:05:23.851Z.xml", STARTING, END);
//        runner.run("imitation/data/rout_2016-01-16T19:23:48.535Z.xml", STARTING, END);
    }
}