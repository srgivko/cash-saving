package by.sivko.cashsaving.configs.PropertyReaders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@Profile("dev")
@Slf4j
public class DevPropertyReader {

    @Bean
    public static PropertyPlaceholderConfigurer properties() {
        log.info("-------- LOAD DEV PROFILE --------");
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[] { new ClassPathResource("properties/application.properties"), new ClassPathResource("properties/application-dev.properties") };
        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }
}

