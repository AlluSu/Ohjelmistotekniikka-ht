/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintorekisteri.dao;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
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
    private String userDBName;
    private String courseDBName;
    
    /**
     * 
     * @return 
     * @throws SQLException 
     */
    public boolean createUsersTable() throws SQLException {
        File file = new File("users.db");
        if (file.exists()) {
            return true;
        }
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqlUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        if (connection == null) {
            System.out.println("Virhe");
        }
        statement = connection.createStatement();
        statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT, username TEXT, course_id REFERENCES Courses)");
        return false;
    }
    
    
    /**
     * 
     * @param user
     * @return 
     * @throws java.sql.SQLException 
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
    
    
    public boolean userNameExists(String username) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        if (connection == null) {
           return false; 
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM Users WHERE username=?");
        preparedStatement.setString(1, username);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException exception) {
            return false;
        }
    }
    
}
