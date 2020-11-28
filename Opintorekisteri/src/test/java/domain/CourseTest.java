/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import opintorekisteri.domain.Course;
import opintorekisteri.domain.User;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
/**
 * Course-luokan testitiedosto
 * @author Aleksi Suuronen
 */
public class CourseTest {
    User user;
    
    @Test
    public void courseExists() {
        user = new User("keijo", "Johtaja88");
        Course course = new Course("Ohjelmistotekniikka", 5, true, user);
        assertTrue(null != course);
    }
    
}
