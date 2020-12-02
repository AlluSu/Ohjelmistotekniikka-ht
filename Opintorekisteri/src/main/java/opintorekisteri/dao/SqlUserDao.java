/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintorekisteri.dao;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import opintorekisteri.domain.User;

/**
 * Luokka joka vastaa User-luokkaan kohdistuvista tietokantaoperaatioista.
 * ATM EI KÄYTÖSSÄ (Viikko5)
 * @author Aleksi Suuronen
 */
public class SqlUserDao {
    
    private Connection connection;
    private Statement statement;
    private String userDBName;
    private String courseDBName;
    
    /**
     * Funktio joka muodostaa tietokantayhteyden ja luo Users-taulun jos sitä ei ole olemassa.
     * @return True, jos luonti onnistui, muuten false.
     * @throws SQLException Poikkeuskäsittely
     */
    public boolean createUsersTable() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqlUserDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        connection = DriverManager.getConnection("jdbc:sqlite:courses.db");
        if (connection == null) {
            System.out.println("Virhe");
            return false;
        }
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, name TEXT, username TEXT, course_id REFERENCES Courses)");
        return true;
    }
    
    
    /**
     * Funktio joka lisää parametrina tulevan käytttäjän tietokantaan.
     * @param user User-olio joka halutaan lisätä tietokantaan.
     * @return True jos lisäys onnistui, muuten false.
     * @throws SQLException Poikkeuskäsittely
     */
    public boolean addUserToUserTable(User user) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        if (connection == null) {
            return false;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users(name, username) VALUES (?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getName());
        try {
            ResultSet resultset = preparedStatement.executeQuery();
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }
    
    
    /**
     * Funktio joka tarkastaa käyttäjätunnuksen perusteella, onko käyttäjätunnus jo tietokannassa ja siten varattu.
     * @param username Käyttäjätunnus jota selvitetään onko varattu
     * @return True jos on varattu, muuten false
     * @throws SQLException Poikkeuskäsittely
     */
    public boolean userNameExists(String username) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        if (connection == null) {
            return false; 
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM Users WHERE username=?");
        preparedStatement.setString(1, username);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            return false;
        }
    }
    
}
