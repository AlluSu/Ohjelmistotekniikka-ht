package domain;

import opintorekisteri.domain.User;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Testiluokka User-luokalle.
 * @author Aleksi Suuronen
 */
public class UserTest {
    
    @Test
    public void userExistsAfterCreating() {
        User user = new User("Keijo", "Zezima");
        assertTrue(null != user);
    }
    
    
    @Test
    public void twoUsersAreDifferentWithDifferentNames() {
        User user1 = new User("Keijo", "Zezima");
        User user2 = new User("Keijo", "Zezima123");
        assertTrue(user1 != user2);
    }
}
