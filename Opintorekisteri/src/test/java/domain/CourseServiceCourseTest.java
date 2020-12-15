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
 * CourseService-luokan testiluokka
 * @author Aleksi Suuronen
 */
public class CourseServiceCourseTest {
    CourseService courseService;
    UserService userService;
    ArrayList<Course> active;
    ArrayList<Course> unactive;
    ArrayList<User> users;
    User user;
    SqlUserDao sud;
    SqlCourseDao scd;
    
    @Before
    public void setup() throws SQLException {
        sud = new SqlUserDao("jdbc:sqlite:memory:");
        scd = new SqlCourseDao("jdbc:sqlite:memory:");
        userService = new UserService(sud, scd);
        courseService = new CourseService(sud, scd);
        userService.createUser("teppo testaaja", "testaaja");
        userService.login("testaaja");
        courseService.createCourse("Ohjelmointi 1", "5", "Informaatioteknologian tiedekunta", "Luentokurssi", "1-5", userService.getLoggedUser());
    }
    
    
    @Test
    public void courseIsFoundByName() throws SQLException {
        userService.logout();
        userService.login("testaaja");
        Course course = courseService.findCourseByName("Ohjelmointi 1", userService.getLoggedUser());
        assertTrue(course != null);
        assertEquals("Ohjelmointi 1", course.getName());
    }
    
    
    @Test
    public void courseIsNotFoundByName() throws SQLException {
        userService.logout();
        userService.login("testaaja");
        Course course = courseService.findCourseByName("Säieteoria", userService.getLoggedUser());
        assertEquals(null, course);
    }
    
    
    @Test
    public void activeCoursesAreEmptyWhenStarted() throws SQLException{
        userService.createUser("lars ahlfors", "testaaja2");
        userService.login("testaaja2");
        assertEquals(0, courseService.getActiveCoursesByUser(userService.getLoggedUser()).size());
    }
    
    
    @Test
    public void cannotCreateCourseWithEmptyName() throws SQLException {
        assertFalse(courseService.createCourse("", "10", null, null, null, userService.getLoggedUser()));     
    }
    

    @Test
    public void nullCourseCannotBeMarkedAsDone() throws SQLException {
        Course c = null;
        assertFalse(courseService.markCourseAsDone(c));
    }
    
    
    @Test
    public void courseIsSetUnactive() throws SQLException {
        userService.logout();
        userService.login("testaaja");
        boolean courseAdded = courseService.createCourse("säieteoria", "10", "matemaattis-luonnontieteellinen", "Luentokurssi", "1-5", userService.getLoggedUser());
        assertTrue(courseAdded);
        Course c = courseService.findCourseByName("säieteoria", userService.getLoggedUser());
        boolean done = courseService.markCourseAsDone(c);
        c.setUnactive();
        assertFalse(c.isActive());
        assertTrue(done);
        courseService.deleteCourse(c);
    }
       
    
    @Test
    public void courseServiceWithSQLDaoExists() {
        courseService = new CourseService(sud, scd);
        assertTrue(null != courseService);
    }
    
    
    @Test
    public void negativeCreditsAsInputDoNotPass() {
        String credits = "-10";
        assertEquals(-1, courseService.checkAndGetCredits("-10"));
        assertTrue(courseService.isNegative(Integer.parseInt(credits)));
    }
    
    
    @Test
    public void creatingCourseWithNegativeCreditsAndLegitNameDoesNotPass() throws SQLException {
        userService.logout();
        userService.login("testaaja");
        boolean result = courseService.createCourse("taloustieteen perusteet", "-5", "valtiotieteellinen", "luentokurssi", "1-5", userService.getLoggedUser());
        assertFalse(result);
    }
    
    
    @Test
    public void randomStringAsInputDoesNotPass() {
        String credits = "Kissa istuu puussa";
        assertEquals(-1, courseService.checkAndGetCredits(credits));
    }
    
    
    @Test
    public void cannotDeleteNullCourse() throws SQLException {
        userService.login("testaaja");
        Course c = null;
        assertFalse(courseService.deleteCourse(c));
    }
    
    
    @Test
    public void validInputPasses() {
        String credits = "2";
        assertEquals(2, courseService.checkAndGetCredits(credits));
    }
    
    
    @Test
    public void emptyCourseNameDoesNotPass() throws SQLException {
        userService.login("testaaja");
        String name = "";
        assertEquals(null, courseService.checkAndGetName(name, userService.getLoggedUser()));
        assertTrue(courseService.isEmptyString(name));
    }
    
    
    @Test
    public void activecoursesAreNullWhenNotLoggedIn() throws SQLException {
        userService.logout();
        ArrayList<Course> courses = courseService.getActiveCoursesByUser(userService.getLoggedUser());
        assertEquals(null, userService.getLoggedUser());
        assertEquals(0, courses.size());
    }
    
    
    @Test
    public void unactiveCoursesAreEmptyWhenStarted() throws SQLException {
        userService.logout();
        userService.login("testaaja2");
        assertEquals(0, courseService.getUnactiveCoursesByUser(userService.getLoggedUser()).size());
    }
    
}
