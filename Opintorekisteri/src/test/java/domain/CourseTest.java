/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import opintorekisteri.domain.Course;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
/**
 * Course-luokan testitiedosto
 * @author Aleksi Suuronen
 */
public class CourseTest {
    
    @Test
    public void courseExists() {
        Course course = new Course(1, "Ohjelmistotekniikka", 5, true, null);
        assertTrue(null != course);
    }
    
    
    @Test
    public void notEqualWhenDifferentId() {
        Course course1 = new Course(1, "Lineaarialgebra ja matriisilaskenta 1", 5, true, null);
        Course course2 = new Course(2, "Lineaarialgebra ja matriisilaskenta 1", 5, true, null);
        assertFalse(course1.equals(course2));
    }
    
}
