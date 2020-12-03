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
 * CourseService-luokan testiluokka
 * @author Aleksi Suuronen
 */
public class CourseServiceCourseTest {
    CourseService service;
    ArrayList<Course> courses;
    ArrayList<Course> unactive;  
    
    @Before
    public void setUp() throws SQLException {
        service = new CourseService();
        courses = new ArrayList<>();
        unactive = new ArrayList<>();
        service.createUser("keijo kesämies", "markkanen");
        service.createUser("Kari Grandi", "kartsa");
    }
    
    
    @Test
    public void activeCoursesAreEmptyWhenStarted() throws SQLException {
        service = new CourseService();
        assertEquals(0, service.getCourses().size());
    }
    
    
    @Test
    public void addingCoursesIsSuccessful() throws SQLException {
        service = new CourseService();
        service.createUser("tom cruise", "topgun");
        service.login("topgun");
        assertTrue(service.getLoggedUser() != null);
        service.createCourse("linis 1", "5");
        service.createCourse("Ohjelmistotekniikka", "5");
        service.createCourse("Pääsäiekimpuista ja Yang-Mills-teoriasta", "10");
        assertEquals(3, service.getCourses().size());
        assertEquals("linis 1", service.getCourses().get(0).getName());
    }
    
    
    @Test
    public void addingDuplicateCourseDoesNotChangeSize() throws SQLException {
        service = new CourseService();
        service.createUser("lauri markkanen", "finnisher");
        service.login("finnisher");
        assertTrue(service.getLoggedUser() != null);
        service.createCourse("linis 1", "5");
        service.createCourse("Liquid milk products", "6");
        service.createCourse("linis 1", "5");
        assertEquals(2, service.getCourses().size());
    }
    
    
    @Test
    public void negativeCreditsAsInputDoNotPass() {
        service = new CourseService();
        String credits = "-10";
        assertEquals(-1, service.checkAndGetCredits("-10"));
        assertTrue(service.isNegative(Integer.parseInt(credits)));
    }
    
    
    @Test
    public void randomStringAsInputDoesNotPass() {
        service = new CourseService();
        String credits = "Kissa istuu puussa";
        assertEquals(-1, service.checkAndGetCredits(credits));
    }
    
    
    @Test
    public void emptyCourseNameDoesNotPass() throws SQLException {
        service = new CourseService();
        String name = "";
        assertEquals(null, service.checkAndGetName(name, service.getLoggedUser()));
        assertTrue(service.isEmptyString(name));
    }
    
    
    @Test
    public void courseIsDuplicate() throws SQLException {
        service = new CourseService();
        service.createCourse("JYM", "5");
        service.createCourse("Linis 2", "5");
        service.createCourse("JYM", "5");
        assertTrue(service.courseExists("JYM", service.getLoggedUser()));
        assertFalse(service.createCourse("JYM", "5"));
    }
    
    
    @Test
    public void unactiveCoursesAreEmptyWhenStarted() throws SQLException {
        service = new CourseService();
        assertEquals(0, service.getUnactiveCourses().size());
    }
    
    
    @Test
    public void activeCourseIsSuccesfullySetToUnactive() throws SQLException {
        service = new CourseService();
        service.createCourse("linis", "5");
        Course linis = service.findCourseByName("linis");
        assertTrue(linis.isActive());
        linis.setUnactive();
        assertFalse(linis.isActive());
    }
    
}
