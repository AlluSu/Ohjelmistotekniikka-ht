/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintorekisteri.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import opintorekisteri.dao.SqlCourseDao;
import opintorekisteri.dao.SqlUserDao;

/**
 * Luokka joka vastaa käyttäjän sovelluslogiikasta.
 * @author Aleksi Suuronen
 */
public class UserService {
    
    private User loggedIn;
    private SqlUserDao userDao;
    private final SqlCourseDao courseDao;
    private String db = "jdbc:sqlite:courses.db";
    
    /**
     * Konstruktori jolla on argumenttina Dao-luokat.
     * @param userDao SqlUserDao-luokan olio
     * @param courseDao SqlCourseDao-luokan olio
     */
    public UserService(SqlUserDao userDao, SqlCourseDao courseDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
    }
    
    
    /**
     * Funktio joka luo uuden käyttäjän sovellukseen.
     * @param username Käyttäjän käyttäjätunnus
     * @param name Käyttäjän oikea nimi
     * @return True jos käyttäjän luonti onnistui, muuten false
     * @throws SQLException poikkeuskäsittely
     */
    public boolean createUser(String name, String username) throws SQLException {
        userDao = new SqlUserDao(db);
        if (userDao.creatingUsersTableIsSuccesful()) {
            if (userDao.usernameExists(username)) {
                return false;
            }
            User user = new User(name, username);
            return userDao.addUser(user);
        }
        return false;
    }
    
    
    /**
     * Funktio palauttaa kirjautuneena olevan käyttäjä-olion.
     * @return kirjautuneena oleva käyttäjä-olio
     */
    public User getLoggedUser() {
        return loggedIn;
    }
    
    
    /**
     * Funktio joka hoitaa käyttäjän uloskirjautumisen.
     */
    public void logout() {
        loggedIn = null;
    }
    
    
    /**
     * Funktio joka hoitaa kirjautumisen.Etsii siis onko käyttäjätunnus jolla yritetään kirjautua olemassa ja jos on niin
        kyseisestä käyttäjästä tulee tällöin "loggedIn".
     * @param username Käyttäjätunnus joka yrittää kirjautua
     * @return True jos kirjautuminen onnistui, muuten false.
     * @throws SQLException poikkeuskäsittely
     */
    public boolean login(String username) throws SQLException {
        userDao = new SqlUserDao(db);
        if (userDao.creatingUsersTableIsSuccesful()) {
            User user = userDao.getUserByUsername(username);
            if (user == null) {
                return false;
            }
            loggedIn = user;
            return true;
        }
        return false;
    }
}
