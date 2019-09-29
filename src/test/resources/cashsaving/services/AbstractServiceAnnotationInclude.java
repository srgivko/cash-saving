package by.sivko.cashsaving.services;

import by.sivko.cashsaving.configs.RepositoryConfig;
import by.sivko.cashsaving.configs.ServiceConfig;
import by.sivko.cashsaving.DefaultDataSetLoader;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;
import org.junit.Ignore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

@Ignore
@TestExecutionListeners(DbUnitTestExecutionListener.class)
@ContextConfiguration(classes = {RepositoryConfig.class, ServiceConfig.class})

@DbUnitConfiguration(dataSetLoader = DefaultDataSetLoader.class)
abstract class AbstractServiceAnnotationInclude {

}
