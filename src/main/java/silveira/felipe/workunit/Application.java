package silveira.felipe.workunit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

/**
 * This class is starts the Spring application.
 */
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    /**
     * Logger object used to log messages.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /**
     * Start Spring application.
     *
     * @param args with configurations.
     */
    public static void main(final String... args) {
        final ApplicationContext ctx = SpringApplication.run(Application.class, args);

        final String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (final String beanName : beanNames) {
            LOGGER.debug("Loaded bean: {}", beanName);
        }
    }
}
