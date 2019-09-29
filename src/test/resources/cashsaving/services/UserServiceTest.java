package by.sivko.cashsaving.services;

import by.sivko.cashsaving.dao.AuthorityDao;
import by.sivko.cashsaving.dao.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

// TODO: 9/24/19 Add tests
public class UserServiceTest extends AbstractServiceAnnotationInclude {

    private static Logger logger = LoggerFactory.getLogger(EventServiceTest.class);

    @Mock
    UserDao userDao;

    @Mock
    AuthorityDao useAuthorityDao;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setUp() {
        userService.setLogger(logger);
    }

    @Test
    public void checkInit() {
        assertNotNull(userDao);
        assertNotNull(useAuthorityDao);
        assertNotNull(userService);
    }
}