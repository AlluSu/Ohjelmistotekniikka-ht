/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import opintorekisteri.domain.User;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * @author Aleksi Suuronen
 */
public class UserTest {
    
    @Test
    public void userExists() {
        User user = new User("Keijo", "Zezima", 1);
        assertTrue(null != user);
    }
    
    
    @Test
    public void twoUsersWithDifferentNames() {
        User user1 = new User("Keijo", "Zezima");
        User user2 = new User("Keijo", "Zezima123");
        assertTrue(user1 != user2);
    }
}
