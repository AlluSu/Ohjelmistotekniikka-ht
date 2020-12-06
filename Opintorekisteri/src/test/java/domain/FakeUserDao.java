/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import opintorekisteri.domain.User;
import org.junit.Before;

/**
 * Testiluokka joka simuloi käyttäjien talletusta.
 * @author Aleksi Suuronen
 */
public class FakeUserDao {
    
    ArrayList<User> users = new ArrayList<>();
    
    public FakeUserDao() {
        users.add(new User("antti suuronen", "zezima"));
    }
    
    public ArrayList<User> getUsers() {
        return users;
    }
    
    public User getUserByUsername(String username) {
        for (User u:users) {
            if(u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }
    
    public boolean addUser(User user) {
        return users.add(user);
    
    }
    
    public boolean usernameExists(String username) {
        for (User u:users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    
}
