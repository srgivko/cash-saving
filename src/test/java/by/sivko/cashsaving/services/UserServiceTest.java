package by.sivko.cashsaving.services;

import by.sivko.cashsaving.dao.AuthorityDao;
import by.sivko.cashsaving.dao.UserDao;
import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;

// TODO: 9/24/19 Add tests
public class UserServiceTest extends AbstractServiceAnnotationInclude {

    @Mock
    UserDao userDao;

    @Mock
    AuthorityDao useAuthorityDao;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
}