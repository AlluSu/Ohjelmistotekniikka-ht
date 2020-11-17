/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import opintorekisteri.domain.Course;
import opintorekisteri.domain.CourseService;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * CourseService-luokan testiluokka
 * @author Aleksi Suuronen
 */
public class CourseServiceCourseTest {
    CourseService service;
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Course> unactive = new ArrayList<>();
    
    
    @Test
    public void activeCoursesAreEmptyWhenStarted() {
        CourseService courseService = new CourseService();
        assertEquals(0, courseService.getCourses().size());
    }
    
    
    @Test
    public void addingCoursesIsSuccessful() {
        CourseService courseService = new CourseService();
        courseService.createCourse("linis 1", "5");
        courseService.createCourse("Ohjelmistotekniikka", "5");
        courseService.createCourse("Pääsäiekimpuista ja Yang-Mills-teoriasta", "10");
        assertEquals(3, courseService.getCourses().size());
        assertEquals("linis 1", courseService.getCourses().get(0).getName());
    }
    
}
