package opintorekisteri.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    public void printAll() {
        printCourses();
        printUsers();
        printUnactive();
    }
    
    public void printCourses() {
        for (Course c: courses) {
            System.out.println(c.toString());
        }
    }
    
    public void printUsers() {
        for (User u: users) {
            System.out.println(u.toString());
        }
    }
    
    
    public void printUnactive() {
        for (Course c: unactive) {
            System.out.println(c.toString());
        }
    }
    
    
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
     */
    public ArrayList<Course> getCourses() {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
        return getActiveCoursesByUser(loggedIn);
    }
    
    
    /**
     * Funktio joka palauttaa listan epäaktiivisista kursseista.
     * @return Listan Course-olioita.
     */
    public ArrayList<Course> getUnactiveCourses() {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
        return getUnactiveCoursesByUser(loggedIn);
    }
    
    
    /**
     * Funktio joka palauttaa listan parametrina tulevan käyttäjän kursseista.
     * @param user Käyttäjä kenen kurssit halutaan
     * @return Lista Course-olioita.
     */
    public ArrayList<Course> getActiveCoursesByUser(User user) {
        ArrayList<Course> usersActiveCourses = new ArrayList<>();
        for (Course course: courses) {
            if (course.getUser().getUsername().equals(user.getUsername())) {
                usersActiveCourses.add(course);
            }
        }
        return usersActiveCourses;
    }
    
    
    /**
     * Funktio joka palauttaa listan parametrina tulevan käyttäjän epäaktiivista kursseista.
     * @param user Käyttäjä kenen epäaktiiviset kurssit halutaan
     * @return Lista käyttäjän epäaktiivista kursseista
     */
    public ArrayList<Course> getUnactiveCoursesByUser(User user) {
        ArrayList<Course> usersUnactiveCourses = new ArrayList<>();
        for (Course course: unactive) {
            if (course.getUser().getUsername().equals(user.getUsername())) {
                usersUnactiveCourses.add(course);
            }
        }
        return usersUnactiveCourses;
    }
    
    
    /**
     * Funktio joka lisää kurssin aktiiviseksi.
     * Funtio kutsuu paria eri apufunktiota jotka tarkastavat syötettä.
     * @param name Kurssin nimi joka halutaan lisätä
     * @param credits Kurssin laajuus
     * @return True jos lisäys onnistui, muuten false
     */
    public boolean createCourse(String name, String credits) {
        String course = checkAndGetName(name);
        if (course == null) {
            return false;
        }
        int parsedCredits = checkAndGetCredits(credits);
        if (parsedCredits == -1) {
            return false;
        }
        Course newCourse = new Course(name, parsedCredits, true, loggedIn);
        courses.add(newCourse);
        System.out.println(courses.size());
        return true;
    }
    
    
    /**
     * Funktio joka poistaa kurssin nimen perusteella.
     * Nimen perusteella poisto on tässä yhteydessä turvallista, koska nimi on yksilöivä.
     * Ei saa siis tällä hetkellä olla kahta samannimistä kurssia.
     * @param name Kurssin nimi joka halutaan poistaa
     * @return True jos poisto onnistui ja muuten false
     */
    public boolean deleteCourse(String name) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(name)) {
                courses.remove(i);
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Metodi joka palauttaa tiedon siitä onnistuttiinko muuttamaan kurssi aktiivisesta epäaktiiviseksi eli tehdyksi.
     * @param data datapötkö
     * @return True jos onnistui, muuten false
     */
    public boolean markCourseAsDone(String data) {
        Course done = findCourseByName(data);
        if (done == null) {
            return false;
        }
        done.setUnactive();
        courses.remove(done);
        return unactive.add(done);   
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
     * Funktio joka hoitaa kurssin nimen käsittelyn ja tutkimisen.
     * Tutkii onko tyhjä ja että onko saman nimistä kurssia olemassa.
     * @param courseName Kurssin nimi jota tutkitaan
     * @return kurssin nimi jos se ei ole tyhjä, muuten null.
     */
    public String checkAndGetName(String courseName) {
        if (isEmptyString(courseName)) {
            System.out.println("Kurssin nimi ei saa olla tyhjä! Tarkista nimi");
            return null;
        }
        if (courseExists(courseName)) {
            System.out.println("Ei voi olla kaksi samannimistä kurssia!");
            return null;
        }
        return courseName;
    }
    
    
    /**
     * Funktio joka tutkii onko aktiivisissa kursseissa jo samanniminen kurssi.
     * @param name Kurssin nimi jota tutkitaan onko saman nimisiä.
     * @return True jos parametrina tullut kurssin nimi on olemassa ja muuten false.
     */
    public boolean courseExists(String name) {
        for (int i = 0; i < courses.size(); i++) {
            if (name.equals(courses.get(i).getName())) {
                return true;
            }
        }
        return false;
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
     */
    public boolean createUser(String name, String username) {
        if (!usernameExists(username)) {
            User user = new User(name, username);
            return users.add(user);
        }               
        return false;
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
     * Funktio joka palauttaa User-olion käyttäjänimen perusteella.
     * @param username Minkä nimistä käyttäjätunnusta etsitään.
     * @return User-olio jolla on etsttävä käyttäjätunnus ja jos ei löydy niin null
     */
    public User getUserByUsername(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    
    /**
     * Funktio joka hoitaa kirjautumisen.
     * Etsii siis onko käyttäjätunnus jolla yritetään kirjautua olemassa ja jos on niin
     * kyseisestä käyttäjästä tulee tällöin "loggedIn"
     * @param username Käyttäjätunnus joka yrittää kirjautua
     * @return True jos kirjautuminen onnistui, muuten false.
     */
    public boolean login(String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }
        loggedIn = user;
        return true;
    }
}
