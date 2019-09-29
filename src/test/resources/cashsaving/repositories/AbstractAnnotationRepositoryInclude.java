package by.sivko.cashsaving.repositories;

import by.sivko.cashsaving.configs.RepositoryConfig;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Ignore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;

@Ignore
@TestPropertySource("jpa.properties")
@TestExecutionListeners(DbUnitTestExecutionListener.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class AbstractAnnotationRepositoryInclude {
}
