package domain;

import java.sql.SQLException;
import opintorekisteri.dao.SqlCourseDao;
import opintorekisteri.dao.SqlUserDao;
import opintorekisteri.domain.CourseService;
import opintorekisteri.domain.User;
import opintorekisteri.domain.UserService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Testiluokka joka testaa Käyttäjä-toimintoja.
 * @author Aleksi Suuronen
 */
public class UserServiceUserTest {
    
    private CourseService service;
    private UserService userService;
    private SqlUserDao sud;
    private SqlCourseDao scd;
    
    @Before
    public void setUp() throws SQLException {
        sud = new SqlUserDao("jdbc:sqlite:memory:");
        scd = new SqlCourseDao("jdbc:sqlite:memory:");
        userService = new UserService(sud, scd);
        service = new CourseService(sud, scd);
    }
    
    
    @Test
    public void nonexistingUserCantLogin() throws SQLException {
        boolean login = userService.login("testuser");
        assertFalse(login);
        assertEquals(null, userService.getLoggedUser());
    }
    
    
    @Test
    public void createdUserCanLogin() throws SQLException {
        userService.createUser("Antti Suuronen", "testaaja3");
        boolean login = userService.login("testaaja3");
        assertTrue(login);
        User loggedIn = userService.getLoggedUser();
        assertEquals("Antti Suuronen", loggedIn.getName());
        assertEquals("testaaja3", loggedIn.getUsername());
    }
    
    
    @Test
    public void nonuniqueUsernameCantBeCreated() throws SQLException {
        boolean result2 = userService.createUser("Antti Suuronen", "testaaja3");
        assertFalse(result2);
    }
    
    
    @Test
    public void UserLoggedinCanLogout() throws SQLException {
        userService.login("testaaja3");
        userService.logout();
        assertEquals(null, userService.getLoggedUser());
    }
}
