package by.sivko.cashsaving;

import by.sivko.cashsaving.configs.DaoConfig;
import by.sivko.cashsaving.configs.PersistenceConfig;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;
import org.junit.Ignore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static by.sivko.cashsaving.AbstractDatabaseAnnotationInclude.*;

@Ignore
@TestPropertySource("jpa.properties")
@TestExecutionListeners(DbUnitTestExecutionListener.class)
@ContextConfiguration(classes = {PersistenceConfig.class, DaoConfig.class})
@DatabaseSetups({
        @DatabaseSetup(LINK_USERS_DATA_SET),
        @DatabaseSetup(LINK_AUTHORITIES_DATA_SET),
        @DatabaseSetup(LINK_USER_AUTHORITY_DATA_SET),
        @DatabaseSetup(LINK_CATEGORIES_DATA_SET),
        @DatabaseSetup(LINK_EVENTS_DATA_SET)
})
@DatabaseTearDowns({
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_EVENTS_DATA_SET),
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_CATEGORIES_DATA_SET),
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_USER_AUTHORITY_DATA_SET),
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_AUTHORITIES_DATA_SET),
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_USERS_DATA_SET)
})
public abstract class AbstractDatabaseAnnotationInclude extends AbstractTransactionalJUnit4SpringContextTests {

     static final String LINK_USERS_DATA_SET = "classpath:datasets/user-table-test.xml";
     static final String LINK_AUTHORITIES_DATA_SET = "classpath:datasets/authority-table-test.xml";
     static final String LINK_USER_AUTHORITY_DATA_SET = "classpath:datasets/user-authority-table-test.xml";
     static final String LINK_CATEGORIES_DATA_SET = "classpath:datasets/categories-table-test.xml";
     static final String LINK_EVENTS_DATA_SET = "classpath:datasets/events-table-test.xml";

}
