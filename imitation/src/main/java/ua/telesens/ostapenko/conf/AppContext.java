package ua.telesens.ostapenko.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author root
 * @since 25.01.16
 */
@Configuration
@ComponentScan(basePackages = {"ua.telesens.ostapenko"})
@Import(PersistenceContext.class)
public class AppContext {
}
