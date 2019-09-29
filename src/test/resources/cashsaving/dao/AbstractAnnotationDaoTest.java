package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.AbstractDatabaseAnnotationInclude;
import by.sivko.cashsaving.configs.DaoConfig;
import by.sivko.cashsaving.configs.PersistenceConfig;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {PersistenceConfig.class, DaoConfig.class})
public class AbstractAnnotationDaoTest extends AbstractDatabaseAnnotationInclude {
}
