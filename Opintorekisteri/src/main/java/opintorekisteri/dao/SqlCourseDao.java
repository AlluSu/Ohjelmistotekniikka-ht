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
import opintorekisteri.domain.Course;
import opintorekisteri.domain.User;

/**
 * Luokka joka vastaa Course-luokkaan kohdistuvista tietokantaoperaatioista.
 * @author Aleksi Suuronen
 */
public class SqlCourseDao {
    
    private Connection connection;
    private Statement statement;
    private String db = "jdbc:sqlite:courses.db";
    
    /**
     * Konstruktori
     * @param db tietokannan nimi
     */
    public SqlCourseDao(String db) {
        this.db = db;
    }
    
    
    /**
     * Funktio joka luo tietokantaan Courses-taulun kursseja varten.
     * @return True jos luonti onnistuu, muute false
     * @throws SQLException poikkeuskäsittely
     */
    public boolean creatingCoursesTableIsSuccesful() throws SQLException {
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
        statement.execute("CREATE TABLE IF NOT EXISTS Courses (id INTEGER PRIMARY KEY, name TEXT, credits INTEGER, faculty TEXT, form_of_Study TEXT, form_of_grading TEXT, active BOOLEAN, owner_name REFERENCES Users)");
        connection.close();
        return true;
    }
    
    
    /**
     * Funktio joka tarkastaa, onko kirjautuneella käyttäjällä olemassa samanniminen kurssi kuin mitä yritetään lisätä.
     * @param courseName Kurssin nimi jota etsitään
     * @param user Kirjautuneena oleva käyttäjä
     * @return True, jos samanniminen kurssi on jo liitetty käyttäjään, muuten false
     * @throws SQLException poikkeuskäsittely
     */
    public boolean courseExistsWithUser(String courseName, User user) throws SQLException {
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return false;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Courses WHERE name=? AND owner_name=?");
        preparedStatement.setString(1, courseName);
        preparedStatement.setString(2, user.getUsername());
        try {
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                connection.close();
                return true;
            }
        } catch (SQLException exception) {
            connection.close();
            return false;
        }
        connection.close();
        return false;
    }
    
    
    /**
     * Funktio joka vaihtaa parametrina tulevan kurssin statuksen aktiivisesta epäaktiiviseksi.
     * @param course Course-olio jonka status halutaan vaihtaa
     * @return True jos vaihto onnistui, muuten false
     * @throws SQLException poikkeuskäsittely
     */
    public boolean changeActiveToUnactive(Course course) throws SQLException {
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return false;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Courses SET active=FALSE WHERE name=? AND owner_name=?");
        preparedStatement.setString(1, course.getName());
        preparedStatement.setString(2, course.getUser().getUsername());
        try {
            int n = preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException exception) {
            connection.close();
            return false;
        }
    }
    
    
    /**
     * Funktio joka poistaa Course-olion tietokannasta.
     * @param course Course-olio joka poistetaan
     * @return True jos poistaminen onnistui ja muuten false
     * @throws SQLException Poikkeuskäsittely
     */
    public boolean deleteCourse(Course course) throws SQLException {
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return false;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Courses WHERE name=? AND owner_name=?");
        preparedStatement.setString(1, course.getName());
        preparedStatement.setString(2, course.getUser().getUsername());
        try {
            int n = preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException exception) {
            connection.close();
            return false;
        }
    }
    
    
    /**
     * Funktio joka lisää kurssin tietokantaan kirjautuneelle käyttäjälle.
     * @param course Kurssi joka lisätään tietokantaan
     * @return TRue jos onnistui, muuten false
     * @throws SQLException poikkeuskäsittely
     */
    public boolean addCourse(Course course) throws SQLException {
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return false;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Courses(name, credits, faculty, form_of_study, form_of_grading, active, owner_name) VALUES (?,?,?,?,?,?,?)");
        preparedStatement.setString(1, course.getName());
        preparedStatement.setInt(2, course.getCredits());
        preparedStatement.setString(3, course.getFaculty());
        preparedStatement.setString(4, course.getFormOfStudy());
        preparedStatement.setString(5, course.getGrading());
        preparedStatement.setBoolean(6, course.isActive());
        preparedStatement.setString(7, course.getUser().getUsername());
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
     * Funktio joka hakee tietokannasta annetun käyttäjän tiedot.
     * @param user Käyttäjä-olio kenen tiedot halutaan
     * @return Lista Course-olioita
     * @throws SQLException Poikkeuskäsittely
     */
    public ArrayList<Course> getActiveCoursesByUser(User user) throws SQLException {
        ArrayList<Course> activeCourses = new ArrayList<>();
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return null;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Courses WHERE owner_name=? AND active=1");
        preparedStatement.setString(1, user.getUsername());
        try {
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Course course  = new Course(results.getString("name"), results.getInt("credits"), results.getString("faculty"),
                        results.getString("form_of_study"), results.getString("form_of_grading"), results.getBoolean("active"), user);
                activeCourses.add(course);
            }
            connection.close();
            return activeCourses;
        } catch (SQLException exception) {
            return null;
        }
    }

    
    /**
     * Hakee tietokannasta halutun käyttäjän epäaktiiviset kurssit.
     * @param user User-olio kenen epäaktiiviset kurssit halutaan
     * @return Lista Course-olioita
     * @throws SQLException Poikkeuskäsittely
     */
    public ArrayList<Course> getUnactiveCoursesByUser(User user) throws SQLException {
        ArrayList<Course> unactiveCourses = new ArrayList<>();
        connection = DriverManager.getConnection(db);
        if (connection == null) {
            return null;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Courses WHERE owner_name=? AND active=0");
        preparedStatement.setString(1, user.getUsername());
        try {
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Course course = new Course(results.getString("name"), results.getInt("credits"), results.getString("faculty"),
                    results.getString("form_of_study"), results.getString("form_of_grading"), results.getBoolean("active"), user);
                unactiveCourses.add(course);
            }
            connection.close();
            return unactiveCourses;
        } catch (SQLException exception) {
            return null;
        }
    }
   
}