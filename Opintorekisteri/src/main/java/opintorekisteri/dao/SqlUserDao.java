/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintorekisteri.dao;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @throws SQLException 
     */
    public void createUsersTable() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqlUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection = DriverManager.getConnection("users.db");
        if (connection == null) {
            System.out.println("rikki");
        }
        statement = connection.createStatement();
        statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT, username TEXT, course_id REFERENCES Courses)");
    }
}
