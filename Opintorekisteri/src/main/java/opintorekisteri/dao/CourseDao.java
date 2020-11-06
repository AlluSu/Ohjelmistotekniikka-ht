/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintorekisteri.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import opintorekisteri.domain.Course;

/**
 *
 * @author aleksi
 */
public class CourseDao {
    
    private String databaseUrl = "jdbc:sqlite:/Opintorekisteri/Courses.db";
    
    public void initialize() throws SQLException {
        try (Connection connection = DriverManager.getConnection(databaseUrl)) {
            if (connection != null) {
                DatabaseMetaData metadata = connection.getMetaData();
                createCourseTables(connection);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    
    public void createCourseTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE Courses (id INTEGER PRIMARY KEY, name TEXT NOT NULL, credits INTEGER NOT NULL, CHECK (credits > 0), active BOOLEAN DEFAULT true, user_id REFERENCES Users)");
    }
    
    public void create(Course course) {
        //
    }
    
}
