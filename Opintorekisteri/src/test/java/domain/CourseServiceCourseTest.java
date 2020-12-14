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
        sud = new SqlUserDao("jdbc:sqlite::memory");
        scd = new SqlCourseDao("jdbc:sqlite::memory");
        userService = new UserService(sud, scd);
        courseService = new CourseService(sud, scd);
        User u1 = new User("tom cruise", "topgun");
        User u2 = new User("lauri markkanen", "finnisher");
        userService.login("topgun");
        courseService.createCourse("Ohjelmointi 1", "5", "Informaatioteknologian tiedekunta", "Luentokurssi", "1-5", new User("teppo testaaja", "testaaja"));
    }
    
    @Test
    public void activeCoursesAreEmptyWhenStarted() throws SQLException{
        userService.createUser("lars ahlfors", "testman");
        userService.login("testman");
        assertEquals(0, courseService.getCourses(userService.getLoggedUser()).size());
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
    public void randomStringAsInputDoesNotPass() {
        String credits = "Kissa istuu puussa";
        assertEquals(-1, courseService.checkAndGetCredits(credits));
    }
    
    
    @Test
    public void cannotDeleteNullCourse() throws SQLException {
        userService.login("finnisher");
        Course c = null;
        assertFalse(courseService.deleteCourse(c));
    }
    
    @Test
    public void validInputPasses() {
        String credits = "2";
        assertEquals(2, courseService.checkAndGetCredits(credits));
    }
    
    
//    @Test
//    public void legitCourseNameIsReturnedSuccesfully() throws SQLException {
//        String name = "lineaarialgebra ja matriisilaskenta 1";
//        assertEquals("lineaarialgebra ja matriisilaskenta 1", courseService.checkAndGetName(name, userService.getLoggedUser()));
//    }
    
    @Test
    public void emptyCourseNameDoesNotPass() throws SQLException {
        userService.createUser("lauri markkanen", "finnisher");
        userService.login("finnisher");
        String name = "";
        assertEquals(null, courseService.checkAndGetName(name, userService.getLoggedUser()));
        assertTrue(courseService.isEmptyString(name));
    }
    
    
    @Test
    public void unactiveCoursesAreEmptyWhenStarted() throws SQLException {
        assertEquals(0, courseService.getUnactiveCourses(userService.getLoggedUser()).size());
    }
    
}
