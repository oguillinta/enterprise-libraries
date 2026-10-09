package pe.com.galaxy.enterprise.java.libs.lib_string_spring_wrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringWrapperConfig {

    @Bean
    StringService stringService() {
        return new StringServiceImpl();
    }

}
