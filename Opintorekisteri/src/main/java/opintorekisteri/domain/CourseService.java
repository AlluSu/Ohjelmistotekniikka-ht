package opintorekisteri.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import opintorekisteri.dao.SqlCourseDao;
import opintorekisteri.dao.SqlUserDao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Luokka joka vastaa kurssien sovelluslogiikasta.
 * @author Aleksi Suuronen
 */
public class CourseService {
    private ArrayList<Course> courses;
    private ArrayList<Course> unactive;
    private ArrayList<User> users;
    private User loggedIn;
    private SqlUserDao userDao;
    private SqlCourseDao courseDao;
    
    
    /**
     * CourseService-luokan konstruktori.
     * @param userDao userdao
     * @param courseDao coursedao
     */
    public CourseService(SqlUserDao userDao, SqlCourseDao courseDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
    }
    
    
    /**
     * CourseService-luokan konstruktori.
     * @param active aktiiviset
     * @param unactive epäaktiiviset
     */
    public CourseService(ArrayList<Course> active, ArrayList<Course> unactive) {
        this.courses = active;
        this.unactive = unactive;
    }
    
    /**
     * Parametriton konstruktori.
     */
    public CourseService() {}
    
    
    /**
     * Funktio joka palauttaa listan aktiivisita kursseista.
     * @return Listan Course-olioita
     * @throws SQLException Poikkeuskäsittely
     */
    public ArrayList<Course> getCourses() throws SQLException {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
        return getActiveCoursesByUser(loggedIn);
    }
    
    
    /**
     * Funktio joka palauttaa listan epäaktiivisista kursseista.
     * @return Listan Course-olioita.
     * @throws SQLException poikkeuskäsittely
     */
    public ArrayList<Course> getUnactiveCourses() throws SQLException {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
        return getUnactiveCoursesByUser(loggedIn);
    }
    
    
    /**
     * Funktio joka palauttaa listan parametrina tulevan käyttäjän kursseista.
     * @param user Käyttäjä kenen kurssit halutaan
     * @return Lista Course-olioita.
     * @throws SQLException Poikkeuskäsittely
     */
    public ArrayList<Course> getActiveCoursesByUser(User user) throws SQLException {
        courseDao = new SqlCourseDao();
        if (courseDao.creatingCoursesTableIsSuccesful()) {
            return courseDao.getActiveCoursesByUser(user);
        }
        return null;
    }
    
    
    /**
     * Funktio joka palauttaa listan parametrina tulevan käyttäjän epäaktiivista kursseista.
     * @param user Käyttäjä kenen epäaktiiviset kurssit halutaan
     * @return Lista käyttäjän epäaktiivista kursseista
     * @throws SQLException poikkeuskäsittely
     */
    public ArrayList<Course> getUnactiveCoursesByUser(User user) throws SQLException {
        courseDao = new SqlCourseDao();
        return courseDao.getUnactiveCoursesByUser(user);
    }
    
    
    /**
     * Funktio joka lisää kurssin aktiiviseksi.Funtio kutsuu paria eri apufunktiota jotka tarkastavat syötettä.
     * @param name Kurssin nimi joka halutaan lisätä
     * @param credits Kurssin laajuus
     * @return True jos lisäys onnistui, muuten false
     * @throws SQLException poikkeuskäsittely
     */
    public boolean createCourse(String name, String credits) throws SQLException {
        courseDao = new SqlCourseDao();
        if (!courseDao.creatingCoursesTableIsSuccesful()) {
            return false;
        }
        String course = checkAndGetName(name, loggedIn);
        if (course == null) {
            return false;
        }
        int parsedCredits = checkAndGetCredits(credits);
        if (parsedCredits == -1) {
            return false;
        }
        Course newCourse = new Course(name, parsedCredits, true, loggedIn);
        if (!courseDao.creatingCoursesTableIsSuccesful()) {
            return false;
        }
        return courseDao.addCourse(newCourse);
    }
    
    
    /**
     * Funktio joka poistaa kurssin nimen perusteella.Nimen perusteella poisto on tässä yhteydessä turvallista, koska nimi on yksilöivä.Ei saa siis tällä hetkellä olla kahta samannimistä kurssia.
     * @param course Kurssi joka poistetaan.
     * @return True jos poisto onnistui ja muuten false
     * @throws SQLException Poikkeuskäsittely
     */
    public boolean deleteCourse(Course course) throws SQLException {
        if (course == null) {
            return false;
        }
        courseDao = new SqlCourseDao();
        return courseDao.deleteCourse(course);
    }
    
    
    /**
     * Metodi joka palauttaa tiedon siitä onnistuttiinko muuttamaan kurssi aktiivisesta epäaktiiviseksi eli tehdyksi.
     * @param course Course-olio jonka status halutaan vaihtaa
     * @return True jos onnistui, muuten false
     * @throws SQLException Poikkeuskäsittely
     */
    public boolean markCourseAsDone(Course course) throws SQLException {
        courseDao = new SqlCourseDao();
        Course done = course;
        if (done == null) {
            return false;
        }
        done.setUnactive();
        return courseDao.changeActiveToUnactive(done);   
    }
    
    /**
     * Metodi joka etsii kurssi-olion nimen perusteella.
     * @param name Kurssin nimi jota etsitään.
     * @return Nimeä vastaava kurssi-olio
     * @throws SQLException poikkeuskäsittely
     */
    public Course findCourseByName(String name) throws SQLException {
        courseDao = new SqlCourseDao();
        courses = courseDao.getActiveCoursesByUser(loggedIn);
        for (Course course: courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }
    
        
    /**
     * Funktio tutkii onko parametrina tuleva syöte negatiivinen (eli < 0).
     * @param credits Syötteenä tuleva opintopistemäärä
     * @return True jos oli negatiivinen arvo ja muuten false
     */
    public boolean isNegative(int credits) {
        return credits < 1;
    }
   
    
    /**
     * Funktio saa parametrina merkkijonon ja tutkii onko se tyhjä vai ei joka määrää paluuarvon.
     * @param name Parametrina tuleva merkkijono jota halutaan tarkastella
     * @return True jos merkkijono on tyhjä ja muuten false 
     */
    public boolean isEmptyString(String name) {
        return name.trim().equals("");
    }
    
    
    /**
     * Funktio joka hoitaa kurssin nimen käsittelyn ja tutkimisen.Tutkii onko tyhjä ja että onko saman nimistä kurssia olemassa.
     * @param courseName Kurssin nimi jota tutkitaan
     * @param loggedUser kirjautuneena olevak äyttäjä
     * @return kurssin nimi jos se ei ole tyhjä, muuten null.
     * @throws SQLException poikkeuskäsittely
     */
    public String checkAndGetName(String courseName, User loggedUser) throws SQLException {
        if (isEmptyString(courseName)) {
            return null;
        }
        if (courseExists(courseName, loggedUser)) {
            return null;
        }
        return courseName;
    }
    
    /**
     * Funktio joka tutkii onko aktiivisissa kursseissa jo samanniminen kurssi.
     * @param name Kurssin nimi jota tutkitaan onko saman nimisiä.
     * @param loggedUser kirjautuneena oleva käyttäjä
     * @return True jos parametrina tullut kurssin nimi on olemassa ja muuten false.
     * @throws SQLException poikkeuskäsittely
     */
    public boolean courseExists(String name, User loggedUser) throws SQLException {
        courseDao = new SqlCourseDao();
        return courseDao.courseExistsWithUser(name, loggedUser);
    }
    
    
        /**
     * Funktio joka hoitaa opintopisteiden käsittelyn käyttäjän syötteestä.
     * @param creditsAsString Merkkijonona tuleva opintopisteiden määrä
     * @return Syötteenä tullut opintopisteiden määrä jos se on sallittu arvo, muuten -1
     */
    public int checkAndGetCredits(String creditsAsString) {
        int credits = 0;
        try {
            credits = Integer.parseInt(creditsAsString);
            if (isNegative(credits)) {
                return -1;
            }
        } catch (NumberFormatException exception) {
            return -1;
        }
        return credits;
    }

}
