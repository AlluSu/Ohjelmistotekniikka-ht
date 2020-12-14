/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import opintorekisteri.dao.SqlCourseDao;
import opintorekisteri.dao.SqlUserDao;
import opintorekisteri.domain.Course;
import opintorekisteri.domain.CourseService;
import opintorekisteri.domain.User;
import opintorekisteri.domain.UserService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Testiluokka tapauksille, jotka liittyvät kirjautuneena olevan käyttäjän ja sovelluslogiikan välisiin suhetisiin.
 * @author Aleksi Suuronen
 */
public class CourseServiceUserTest {
    
    private CourseService service;
    private UserService userService;
    private SqlUserDao sud;
    private SqlCourseDao scd;
    
    @Before
    public void setUp() throws SQLException {
        sud = new SqlUserDao("jdbc:sqlite::memory");
        scd = new SqlCourseDao("jdbc:sqlite::memory");
        userService = new UserService(sud, scd);
        service = new CourseService(sud, scd);
        User u1 = new User("tom cruise", "topgun");
        User u2 = new User("lauri markkanen", "finnisher");
    }
    
    
    @Test
    public void nonexistingUserCantLogin() throws SQLException {
        boolean login = userService.login("testuser");
        assertFalse(login);
        assertEquals(null, userService.getLoggedUser());
    }
    
    
    @Test
    public void createdUserCanLogin() throws SQLException {
        userService.createUser("Antti Suuronen", "Repa");
        boolean login = userService.login("Repa");
        assertTrue(login);
        User loggedIn = userService.getLoggedUser();
        assertEquals("Antti Suuronen", loggedIn.getName());
        assertEquals("Repa", loggedIn.getUsername());
    }
    
    
    @Test
    public void nonuniqueUsernameCantBeCreated() throws SQLException {
        boolean result2 = userService.createUser("Antti Suuronen", "Repa");
        assertFalse(result2);
    }
    
    
    @Test
    public void UserLoggedinCanLogout() throws SQLException {
        userService.login("Repa");
        userService.logout();
        assertEquals(null, userService.getLoggedUser());
    }
}
