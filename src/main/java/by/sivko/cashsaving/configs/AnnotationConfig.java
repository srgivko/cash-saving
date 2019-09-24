package by.sivko.cashsaving.configs;

import by.sivko.cashsaving.annotations.LoggingAnnotationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfig {
    @Bean
    public LoggingAnnotationProcessor loggingAnnotationProcessor() {
        return new LoggingAnnotationProcessor();
    }
}
