package domain;

import opintorekisteri.domain.Course;
import opintorekisteri.domain.User;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
/**
 * Course-luokan testitiedosto.
 * @author Aleksi Suuronen
 */
public class CourseTest {
    User user;
    
    @Test
    public void courseExists() {
        user = new User("lauri markkanen", "finnisher");
        Course course = new Course("Ohjelmistotekniikka", 5, "Matemaattis-luonnontieteellinen", "Harjoitustyö", "1-5", true, user);
        assertTrue(course != null);
    }
    
}
