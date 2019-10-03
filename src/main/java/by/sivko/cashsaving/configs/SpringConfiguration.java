package by.sivko.cashsaving.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        PersistenceConfig.class,
        ServiceConfig.class,
        WebSecurityConfig.class,
        //LiquibaseConfig.class,
        AnnotationConfig.class,
        RepositoryConfig.class,
        DaoConfig.class,
})
public class SpringConfiguration {
}
