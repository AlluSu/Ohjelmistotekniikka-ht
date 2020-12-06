/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.SQLException;
import java.util.ArrayList;
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
    
    CourseService service;
    UserService userService;
    private ArrayList<Course> courses;
    private ArrayList<Course> unactive;
    private ArrayList<User> users;
    FakeCourseDao fcd;
    FakeUserDao fud;
    
    @Before
    public void setUp() {
        service = new CourseService(courses, unactive);
        courses = new ArrayList<>();
        unactive = new ArrayList<>();
        users = new ArrayList<>();
        userService = new UserService();
        fcd = new FakeCourseDao();
        fud = new FakeUserDao();
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
