package by.sivko.cashsaving.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"by.sivko.cashsaving.models", "by.sivko.cashsaving.repositories"})
public class RepositoryConfig {
}
