package opintorekisteri.domain;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Luokka joka vastaa sovelluslogiikasta
 * @author Aleksi Suuronen
 */
public class CourseService {
    private Scanner reader;
    private ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<Course> unactive = new ArrayList<Course>();
    
    
    /**
     * CourseService-luokan konstruktori
     * @param courses Aktiiviset kurssit
     * @param unactive Epäaktiiviset kurssit
     */
    public CourseService(ArrayList<Course> courses, ArrayList<Course> unactive) {
        this.courses = courses;
        this.unactive = unactive;
    }
    
    
    /**
     * Parametriton konstruktori
     */
    public CourseService() {}
    
    
    /**
     * Funktio joka palauttaa listan aktiivisita kursseista.
     * @return Listan COurse-olioita
     */
    public ArrayList<Course> getCourses() {
       return courses;
    }
    
    
    /**
     * Funktio joka palauttaa listan epäaktiivisista kursseista.
     * @return Listan Course-olioita.
     */
    public ArrayList<Course> getUnactiveCourses() {
        return unactive;
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
        if (parsedCredits==-1) {
            return false;
        }
        Course newCourse = new Course(name, parsedCredits, true);
        return courses.add(newCourse);
    }
    
    
    /**
     * Funktio joka hoitaa kurssin nimen käsittelyn ja tutkimisen.
     * Tutkii onko tyhjä ja että onko saman nimistä kurssia olemassa.
     * @param courseName Kurssin nimi jota tutkitaan
     * @return kurssin nimi jos se ei ole tyhjä, muuten null.
     */
    public String checkAndGetName(String courseName) {
        ArrayList<Course> courses = new ArrayList<>();
        courses = getCourses();
        if (isEmptyString(courseName)) {
            System.out.println("Kurssin nimi ei saa olla tyhjä! Tarkista nimi");
            return null;
        }
        if (isDuplicateCourse(courseName)) {
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
    public boolean isDuplicateCourse(String name) {
        for (int i = 0; i < courses.size(); i++) {
            if (name.equals(courses.get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Funktio tutkii onko parametrina tuleva syöte negatiivinen (eli < 0)
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
}
