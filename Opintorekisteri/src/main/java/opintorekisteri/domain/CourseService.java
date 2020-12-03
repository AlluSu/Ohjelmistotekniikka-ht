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
 * Luokka joka vastaa sovelluslogiikasta.
 * @author Aleksi Suuronen
 */
public class CourseService {
    private ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<Course> unactive = new ArrayList<Course>();
    private ArrayList<User> users = new ArrayList<User>();
    private User loggedIn;
    private SqlUserDao userDao;
    private SqlCourseDao courseDao;
    private Course helperCourse;
    
    
    /**
     * CourseService-luokan konstruktori.
     * @param courses Aktiiviset kurssit
     * @param unactive Epäaktiiviset kurssit
     */
    public CourseService(ArrayList<Course> courses, ArrayList<Course> unactive) {
        this.courses = courses;
        this.unactive = unactive;
    }
    
    
    /**
     * Parametriton konstruktori.
     */
    public CourseService() {}
    
    
    /**
     * Funktio joka palauttaa listan aktiivisita kursseista.
     * @return Listan Course-olioita
     * @throws java.sql.SQLException Poikkeuskäsittely
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
     * @throws java.sql.SQLException poikkeuskäsittely
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
     * @throws java.sql.SQLException Poikkeuskäsittely
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
     * @throws java.sql.SQLException poikkeuskäsittely
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
     * @throws java.sql.SQLException poikkeuskäsittely
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
     * @throws java.sql.SQLException Poikkeuskäsittely
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
     * @throws java.sql.SQLException Poikkeuskäsittely
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
     */
    public Course findCourseByName(String name) {
        for (Course course:courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }
    
    
    /**
     * Funktio joka hoitaa kurssin nimen käsittelyn ja tutkimisen.Tutkii onko tyhjä ja että onko saman nimistä kurssia olemassa.
     * @param courseName Kurssin nimi jota tutkitaan
     * @param loggedUser kirjautuneena olevak äyttäjä
     * @return kurssin nimi jos se ei ole tyhjä, muuten null.
     * @throws java.sql.SQLException poikkeuskäsittely
     */
    public String checkAndGetName(String courseName, User loggedUser) throws SQLException {
        if (isEmptyString(courseName)) {
            System.out.println("Kurssin nimi ei saa olla tyhjä! Tarkista nimi");
            return null;
        }
        if (courseExists(courseName, loggedUser)) {
            System.out.println("Ei voi olla kaksi samannimistä kurssia!");
            return null;
        }
        return courseName;
    }
    
    
    /**
     * Funktio joka tutkii onko aktiivisissa kursseissa jo samanniminen kurssi.
     * @param name Kurssin nimi jota tutkitaan onko saman nimisiä.
     * @param loggedUser kirjautuneena oleva käyttäjä
     * @return True jos parametrina tullut kurssin nimi on olemassa ja muuten false.
     * @throws java.sql.SQLException poikkeuskäsittely
     */
    public boolean courseExists(String name, User loggedUser) throws SQLException {
        courseDao = new SqlCourseDao();
        return courseDao.courseExistsWithUser(name, loggedUser);
    }
    
    
    /**
     * Funktio joka saa parametrina kurssin nimen ja palauttaa missä kohtaa listaa se sijaitsee.
     * @param name Kurssin nimi jonka indeksi halutaan
     * @return indeksi missä kohtaa kurssi sijaitsee
     */
    public int getIndexOf(String name) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
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
     * Funktio joka hoitaa opintopisteiden käsittelyn käyttäjän syötteestä.
     * @param creditsAsString Merkkijonona tuleva opintopisteiden määrä
     * @return Syötteenä tullut opintopisteiden määrä jos se on sallittu arvo, muuten -1
     */
    public int checkAndGetCredits(String creditsAsString) {
        int credits = 0;
        try {
            credits = Integer.parseInt(creditsAsString);
            if (isNegative(credits)) {
                System.out.println("Opintopistemäärä ei voi olla 0 tai negatiivinen! Tarkista opintopisteet");
                return -1;
            }
        } catch (NumberFormatException exception) {
            System.out.println("Anna opintopisteet numeroina! Tarkista syöte!");
            return -1;
        }
        return credits;
    }
    
    
    /**
     * Funktio joka luo uuden käyttäjän sovellukseen.
     * @param username Käyttäjän käyttäjätunnus
     * @param name Käyttäjän oikea nimi
     * @return True jos käyttäjän luonti onnistui, muuten false
     * @throws java.sql.SQLException
     */
    public boolean createUser(String name, String username) throws SQLException {
        userDao = new SqlUserDao();
        if (!userDao.creatingUsersTableIsSuccesful()) {
            return false;
        }
        if (userDao.usernameExists(username)) {
            return false;
        }               
        User user = new User(name, username);
        return userDao.addUser(user);
    }
    
    
    /**
     * Palauttaa listan sovellukseen luoduista käyttäjistä.
     * @return Lista User-olioita
     */
    public ArrayList<User> getUsers() {
        return users;
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
     * Funktio joka tutkii onko parametrina tuleva käyttäjätunnus uniikki vai ei.
     * @param username Käyttäjätunnus jonka yksikäsitteisyys tahdotaan selvittää
     * @return True jos käyttäjätunnus ei ole varattu, muuten false.
     */
    public boolean usernameExists(String username) {
        for (User user: users) {
            if (user.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Funktio joka hoitaa kirjautumisen.Etsii siis onko käyttäjätunnus jolla yritetään kirjautua olemassa ja jos on niin
        kyseisestä käyttäjästä tulee tällöin "loggedIn"
     * @param username Käyttäjätunnus joka yrittää kirjautua
     * @return True jos kirjautuminen onnistui, muuten false.
     * @throws java.sql.SQLException
     */
    public boolean login(String username) throws SQLException {
        userDao = new SqlUserDao();
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

    public Object checkAndGetName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
