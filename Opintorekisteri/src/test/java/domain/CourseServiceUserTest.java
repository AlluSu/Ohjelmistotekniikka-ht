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
    private ArrayList<Course> courses;
    private ArrayList<Course> unactive;
    private ArrayList<User> users;
    
    
    @Before
    public void setUp() {
        service = new CourseService(courses, unactive);
        courses = new ArrayList<>();
        unactive = new ArrayList<>();
        users = new ArrayList<>();   
    }
    
    
    @Test
    public void nonexistingUserCantLogin() throws SQLException {
        boolean login = service.login("testuser");
        assertFalse(login);
        assertEquals(null, service.getLoggedUser());
    }
    
    
    @Test
    public void createdUserCanLogin() throws SQLException {
        boolean succesfullyCreated = service.createUser("Antti Suuronen", "Repa");
        assertTrue(succesfullyCreated);
        boolean login = service.login("Repa");
        assertTrue(login);
        User loggedIn = service.getLoggedUser();
        assertEquals("Antti Suuronen", loggedIn.getName());
        assertEquals("Repa", loggedIn.getUsername());
    }
    
    
    @Test
    public void nonuniqueUsernameCantBeCreated() throws SQLException {
        boolean result1 = service.createUser("Reima Kuisla", "Repa");
        assertTrue(result1);
        boolean result2 = service.createUser("Antti Suuronen", "Repa");
        assertFalse(result2);
    }
    
    
    @Test
    public void UserLoggedinCanLogout() throws SQLException {
        service.login("Repa");
        service.logout();
        assertEquals(null, service.getLoggedUser());
    }
}
