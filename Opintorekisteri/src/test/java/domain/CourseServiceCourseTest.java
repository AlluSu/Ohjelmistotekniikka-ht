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
    FakeCourseDao fakeCourseDao;
    FakeUserDao fakeUserDao;
    User user;
    SqlUserDao sud;
    SqlCourseDao scd;
    
    @Before
    public void setup() throws SQLException {
        fakeUserDao = new FakeUserDao();
        fakeCourseDao= new FakeCourseDao();
        userService = new UserService();
        User u1 = new User("tom cruise", "topgun");
        User u2 = new User("lauri markkanen", "finnisher");
        fakeUserDao.addUser(u1);
        fakeUserDao.addUser(u2);
        fakeCourseDao.addCourse(new Course("Ohjelmointi 1", 5, true, new User("", "testaaja")));
        courseService = new CourseService();
        userService.login("topgun");
    }
    
    @Test
    public void activeCoursesAreEmptyWhenStarted() throws SQLException{
        courseService = new CourseService();
        userService = new UserService();
        userService.createUser("testaus man", "testaaja");;
        userService.login("testaaja");
        assertEquals(0, courseService.getCourses(userService.getLoggedUser()).size());
    }
    
    
    @Test
    public void cannotCreateCourseWithEmptyName() throws SQLException {
        courseService = new CourseService();
        assertFalse(courseService.createCourse("", "10", userService.getLoggedUser()));     
    }
    
    
    @Test
    public void cannotCreateCourseWithNegativeCredits() throws SQLException {
        courseService = new CourseService();
        assertFalse(courseService.createCourse("Kvanttifysiikka 1", "-10", userService.getLoggedUser()));     
    }
    
    @Test
    public void nullCourseCannotBeMarkedAsDone() throws SQLException {
        courseService = new CourseService();
        Course c = null;
        assertFalse(courseService.markCourseAsDone(c));
    }
    
    
    @Test
    public void courseWhichDoesNotExistCannotBeFound() throws SQLException {
        courseService = new CourseService();
        assertEquals(null, courseService.findCourseByName("nimetön", userService.getLoggedUser()));
    }
    
    
    @Test
    public void courseServiceWithSQLDaoExists() {
        sud = new SqlUserDao();
        scd = new SqlCourseDao();
        courseService = new CourseService(sud, scd);
        assertTrue(null != courseService);
    }
    
    @Test
    public void addingCoursesIsSuccessful() throws SQLException {
        courseService.createCourse("linis 1", "5", userService.getLoggedUser());
        assertTrue(courseService.courseExists("linis 1", userService.getLoggedUser()));
    }
    
    
    @Test
    public void addingDuplicateIsNotAllowed() throws SQLException {
        assertFalse(courseService.createCourse("linis 1", "5", userService.getLoggedUser()));
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
    public void cannotDeleteNullCourse() throws SQLException {
        courseService = new CourseService();
        userService.login("finnisher");
        Course c = null;
        assertFalse(courseService.deleteCourse(c));
    }
    
    @Test
    public void validInputPasses() {
        courseService = new CourseService();
        String credits = "2";
        assertEquals(2, courseService.checkAndGetCredits(credits));
    }
    
    
    @Test
    public void legitCourseNameIsReturnedSuccesfully() throws SQLException {
        courseService = new CourseService();
        userService.login("finnisher");
        String name = "lineaarialgebra ja matriisilaskenta 1";
        assertEquals("lineaarialgebra ja matriisilaskenta 1", courseService.checkAndGetName(name, userService.getLoggedUser()));
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
        courseService.createCourse("JYM", "5", userService.getLoggedUser());
        courseService.createCourse("Linis 2", "5", userService.getLoggedUser());
        courseService.createCourse("JYM", "5", userService.getLoggedUser());
        assertTrue(courseService.courseExists("JYM", userService.getLoggedUser()));
        assertFalse(courseService.createCourse("JYM", "5", userService.getLoggedUser()));
    }
    
    
    @Test
    public void unactiveCoursesAreEmptyWhenStarted() throws SQLException {
        courseService = new CourseService();
        assertEquals(0, courseService.getUnactiveCourses(userService.getLoggedUser()).size());
    }
    
    
    @Test
    public void activeCourseIsSuccesfullySetToUnactive() throws SQLException {
        ArrayList<Course> c = new ArrayList<>();
        c = courseService.getActiveCoursesByUser(userService.getLoggedUser());
        assertTrue(c.get(0).isActive());
        c.get(0).setUnactive();
        assertFalse(c.get(0).isActive());
    }
    
}
