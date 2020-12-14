/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintorekisteri.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import opintorekisteri.domain.User;

/**
 * Luokka joka vastaa User-luokkaan kohdistuvista tietokantaoperaatioista.
 * @author Aleksi Suuronen
 */
public class SqlUserDao {
    
    private Connection connection;
    private Statement statement;
    private String db = "jdbc:sqlite:courses.db";
    
    public SqlUserDao(String db) {
        this.db = db;
    }
    
    
    /**
     * Funktio joka luo tietokantataulun käyttäjille.
     * @return True jos taulun lunoti onnistui, muuten false
     * @throws SQLException poikkeuskäsittely
     */
    public boolean creatingUsersTableIsSuccesful() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqlUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            connection.close();
            return false;
        }
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, name TEXT, username TEXT)");
        connection.close();
        return true;
    }
    
    
    /**
     * Funktio joka lisää parametrina tulevan käyttäjän tietokantaan.
     * @param user Käyttäjä joka halutaan lisätä
     * @return True jos lisääminen onnistui, muuten false
     * @throws SQLException Poikkeuskäsittely
     */
    public boolean addUser(User user) throws SQLException {
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return false;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users(name, username) VALUES (?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getUsername());
        try {
            int n = preparedStatement.executeUpdate();
            if (n == 1) {
                connection.close();
                return true;
            }
        } catch (SQLException exception) {
            connection.close();
            return false;
        }
        return false;
    }
    
    
    /**
     * Funktio joka etsii käyttäjätunnuksen tietokannasta.
     * @param username Käyttäjätunnus jota etsitään
     * @return User-olio jos löytyi, muuten null
     * @throws SQLException Poikkeuskäsittely
     */
    public User getUserByUsername(String username) throws SQLException {
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return null;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, username FROM Users WHERE username=?");
        preparedStatement.setString(1, username);
        try {
            ResultSet result = preparedStatement.executeQuery();
            User user = new User(result.getString("name"), result.getString("username"));
            connection.close();
            return user;
        } catch (SQLException exception) {
            connection.close();
            return null;
        }       
    }
    
    
    /**
     * Funktio joka etsii onko tietokannassa kyseistä käyttäjätunnusta.
     * @param username Käyttäjätunnus jota etsitään
     * @return True jos käyttäjä on olemassa ja muuten false
     * @throws SQLException Poikkeuskäsittely jos jokin menee pieleen
     */
    public boolean usernameExists(String username) throws SQLException {
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return false;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM Users WHERE username=?");
        preparedStatement.setString(1, username);
        try {
            ResultSet result = preparedStatement.executeQuery();
            connection.close();
            return result.next();
        } catch (SQLException exception) {
            connection.close();
            return false;
        }
    }
    
    
    /**
     * Funktio joka palauttaa käyttäjät jotka ovat sovelluksen tietokannassa.
     * @return Lista User-olioita
     * @throws SQLException Poikkeuskäsittely
     */
    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return users;
        }
        Statement s = connection.createStatement();
        try {
            ResultSet results = s.executeQuery("SELECT username FROM Users");
            while (results.next()) {
                User user = new User(results.getString("name"), results.getString("username"));
                users.add(user);
            }
            connection.close();
            return users;
        } catch (SQLException exception) {
            connection.close();
            return users;
        }
    }
}