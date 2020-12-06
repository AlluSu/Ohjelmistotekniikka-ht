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
 * CourseService-luokan testiluokka
 * @author Aleksi Suuronen
 */
public class CourseServiceCourseTest {
    CourseService courseService;
    UserService userService;
    ArrayList<Course> active;
    ArrayList<Course> unactive;
    ArrayList<User> users;
    FakeCourseDao fakeCourseDao;
    FakeUserDao fakeUserDao;
    User user;
    
    @Before
    public void setup() {
        fakeUserDao = new FakeUserDao();
        fakeCourseDao= new FakeCourseDao();
        courseService = new CourseService();
        userService = new UserService();
    }
    
    @Test
    public void activeCoursesAreEmptyWhenStarted() throws SQLException{
        courseService = new CourseService();
        assertEquals(0, courseService.getCourses().size());
    }
    
    
    @Test
    public void addingCoursesIsSuccessful() throws SQLException {
        courseService = new CourseService();
        userService.createUser("tom cruise", "topgun");
        userService.login("topgun");
        assertTrue(userService.getLoggedUser() != null);
        courseService.createCourse("linis 1", "5");
        courseService.createCourse("Ohjelmistotekniikka", "5");
        courseService.createCourse("Pääsäiekimpuista ja Yang-Mills-teoriasta", "10");
        assertEquals(3, courseService.getCourses().size());
        assertEquals("linis 1", courseService.getCourses().get(0).getName());
    }
    
    
    @Test
    public void addingDuplicateCourseDoesNotChangeSize() throws SQLException {
        courseService = new CourseService();
        userService = new UserService();
        userService.createUser("lauri markkanen", "finnisher");
        userService.login("finnisher");
        assertTrue(userService.getLoggedUser() != null);
        courseService.createCourse("linis 1", "5");
        courseService.createCourse("Liquid milk products", "6");
        courseService.createCourse("linis 1", "5");
        assertEquals(2, courseService.getCourses().size());
    }
    
    
    @Test
    public void negativeCreditsAsInputDoNotPass() {
        courseService = new CourseService();
        String credits = "-10";
        assertEquals(-1, courseService.checkAndGetCredits("-10"));
        assertTrue(courseService.isNegative(Integer.parseInt(credits)));
    }
    
    
    @Test
    public void randomStringAsInputDoesNotPass() {
        courseService = new CourseService();
        String credits = "Kissa istuu puussa";
        assertEquals(-1, courseService.checkAndGetCredits(credits));
    }
    
    
    @Test
    public void emptyCourseNameDoesNotPass() throws SQLException {
        courseService = new CourseService();
        userService.createUser("lauri markkanen", "finnisher");
        userService.login("finnisher");
        String name = "";
        assertEquals(null, courseService.checkAndGetName(name, userService.getLoggedUser()));
        assertTrue(courseService.isEmptyString(name));
    }
    
    
    @Test
    public void courseIsDuplicate() throws SQLException {
        courseService = new CourseService();
        userService = new UserService();
        userService.createUser("teppo testaaja", "hackerman");
        boolean login = userService.login("hackerman");
        assertTrue(login);
        courseService.createCourse("JYM", "5");
        courseService.createCourse("Linis 2", "5");
        courseService.createCourse("JYM", "5");
        assertTrue(courseService.courseExists("JYM", userService.getLoggedUser()));
        assertFalse(courseService.createCourse("JYM", "5"));
    }
    
    
    @Test
    public void unactiveCoursesAreEmptyWhenStarted() throws SQLException {
        courseService = new CourseService();
        assertEquals(0, courseService.getUnactiveCourses().size());
    }
    
    
    @Test
    public void activeCourseIsSuccesfullySetToUnactive() throws SQLException {
        courseService = new CourseService();
        assertFalse(courseService == null);
        userService = new UserService();
        assertFalse(userService == null);
        userService.createUser("lauri markkanen", "finnisher");
        userService.login("finnisher");
        assertFalse(userService.getLoggedUser() == null);
        courseService.createCourse("linis", "5");
        Course linis = courseService.findCourseByName("linis");
        assertTrue(linis.isActive());
        linis.setUnactive();
        assertFalse(linis.isActive());
    }
    
}
