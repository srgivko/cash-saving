package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.models.Authority;
import by.sivko.cashsaving.models.AuthorityType;
import by.sivko.cashsaving.models.User;
import com.github.springtestdbunit.annotation.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static by.sivko.cashsaving.dao.AbstractDatabaseAnnotationInclude.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@DatabaseSetups({
        @DatabaseSetup(LINK_USERS_DATA_SET),
        @DatabaseSetup(LINK_AUTHORITIES_DATA_SET),
        @DatabaseSetup(LINK_USER_AUTHORITY_DATA_SET)
})
@DatabaseTearDowns({
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_USER_AUTHORITY_DATA_SET),
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_AUTHORITIES_DATA_SET),
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_USERS_DATA_SET)
})
@DbUnitConfiguration
public class UserDaoTest extends AbstractDatabaseAnnotationInclude {

    private static final User EXIST_USER_1 = new User(100000L, "username-100000", "password-100000", "email-100000", null, null);
    private static final User EXIST_USER_2 = new User(100001L, "username-100001", "password-100001", "email-100001", null, null);
    private static final User EXIST_USER_3 = new User(100002L, "username-100001", "password-100002", "email-100002", null, null);

    private static final long NOT_EXIST_USER_ID = 100500L;
    private static final int USER_LIST_SIZE = 3;

    private static final User NEW_USER = new User();
    private static final String NEW_USER_USERNAME_PASSWORD_EMAIL_VALUE = "newUser";

    @Autowired
    UserDao userDao;

    @Before
    public void initBefore() {
        NEW_USER.setId(null);
        NEW_USER.setUsername(NEW_USER_USERNAME_PASSWORD_EMAIL_VALUE);
        NEW_USER.setPassword(NEW_USER_USERNAME_PASSWORD_EMAIL_VALUE);
        NEW_USER.setEmail(NEW_USER_USERNAME_PASSWORD_EMAIL_VALUE);
    }

    @Test
    public void getExistUser() {
        Optional<User> userOptional = this.userDao.getById(EXIST_USER_1.getId());
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        Set<Authority> authorities = user.getAuthorities();
        assertTrue(authorities.stream().anyMatch(authority -> authority.getName().equals(AuthorityType.ROLE_USER)));
        assertTrue(authorities.stream().anyMatch(authority -> authority.getName().equals(AuthorityType.ROLE_ADMIN)));
        assertThat(userOptional.get().getUsername(), is(EXIST_USER_1.getUsername()));
        assertThat(userOptional.get().getPassword(), is(EXIST_USER_1.getPassword()));
        assertThat(userOptional.get().getEmail(), is(EXIST_USER_1.getEmail()));
    }

    @Test
    public void getAllUsers() {
        List<User> userList = this.userDao.getAll();
        assertTrue(userList.stream().anyMatch(user -> user.getId().equals(EXIST_USER_1.getId())));
        assertTrue(userList.stream().anyMatch(user -> user.getId().equals(EXIST_USER_2.getId())));
        assertTrue(userList.stream().anyMatch(user -> user.getId().equals(EXIST_USER_3.getId())));
        assertFalse(userList.stream().anyMatch(user -> user.getId().equals(NOT_EXIST_USER_ID)));
        assertEquals(userList.size(), USER_LIST_SIZE);
    }

    @Test
    public void getNotExistUser() {
        Optional<User> optionalUser = this.userDao.getById(NOT_EXIST_USER_ID);
        assertEquals(optionalUser, Optional.empty());
    }

    @Test
    public void saveUser() {
        assertNull(NEW_USER.getId());
        this.userDao.add(NEW_USER);
        assertNotNull(NEW_USER.getId());
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE + 1);
    }

    @Test
    public void savePersistenceUser() {
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE);
        User user = this.userDao.getById(EXIST_USER_1.getId()).orElseThrow(RuntimeException::new);
        long remainderUserId = user.getId();
        this.userDao.add(user);
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE);
        assertEquals((long) user.getId(), remainderUserId);
    }

    @Test
    public void updateUser() {
        User user = this.userDao.getById(EXIST_USER_1.getId()).orElseThrow(RuntimeException::new);
        assertThat(user.getUsername(), is(EXIST_USER_1.getUsername()));
        user.setUsername("updateUser");
        this.userDao.edit(user);
        assertThat(user.getUsername(), is("updateUser"));
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE);
    }

    @Test
    public void updateTransientUser() {
        assertNull(NEW_USER.getId());
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE);
        this.userDao.edit(NEW_USER);
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE + 1);
    }

    @Test
    public void deleteUser() {
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE);
        User user = this.userDao.getById(EXIST_USER_1.getId()).orElseThrow(RuntimeException::new);
        this.userDao.delete(user);
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNotExistsUser() {
        NEW_USER.setId(NOT_EXIST_USER_ID);
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE);
        this.userDao.delete(NEW_USER);
        assertEquals(this.userDao.getAll().size(), USER_LIST_SIZE);
    }

}
