package pe.com.galaxy.enterprise.java.libs.lib_date_spring_wrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Spring configuration for date wrapper services.
 *
 * @since 1.0.0
 */
@Configuration
public class DateWrapperConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public DateService dateService(
            Clock clock
    ) {
        return new DateServiceImpl(
                clock
        );
    }
}