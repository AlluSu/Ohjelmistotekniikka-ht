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
     * 
     * @param courses
     * @param unactive 
     */
    public CourseService(ArrayList<Course> courses, ArrayList<Course> unactive) {
        this.courses = courses;
        this.unactive = unactive;
    }
    
    
    /**
     * 
     */
    public CourseService() {}
    
    
    /**
     * 
     * @return 
     */
    public ArrayList<Course> getCourses() {
       return courses;
    }
    
    
    /**
     * 
     * @return 
     */
    public ArrayList<Course> getUnactiveCourses() {
        return unactive;
    }
    
    
    /**
     * 
     * @param name
     * @param credits
     * @return 
     */
    public boolean createCourse(String name, int credits) {
        Course course = new Course(name, credits, true);
        return courses.add(course);
    }
    
    
       /**
     * Funktio joka hoitaa kurssin nimen käsittelyn
     * @return kurssin nimi jos se ei ole tyhjä, muuten nolla.
     */
    public String checkAndGetName(Scanner reader) {
        String name = reader.nextLine();
        ArrayList<Course> courses = new ArrayList<>();
        courses = getCourses();
        if (isEmptyString(name)) {
            System.out.println("Kurssin nimi ei saa olla tyhjä! Tarkista nimi");
            return null;
        }
        if (isDuplicateCourse(name)) {
            System.out.println("Ei voi olla kaksi samannimistä kurssia!");
            return null;
        }
        return name;
    }
    
    
    /**
     * 
     * @param name
     * @return 
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
     * Funktio saa parametrina merkkijonon ja tutkii onko se tyhjä vai ei joka määrää paluuarvon
     * @param name Parametrina tuleva merkkijono jota halutaan tarkastella
     * @return True jos merkkijono on tyhjä ja muuten false 
     */
    public boolean isEmptyString(String name) {
        return name.trim().equals("");
    }
    
    
    /**
     * Funktio joka hoitaa opintopisteiden käsittelyn käyttäjän syötteestä
     * @return Syötteenä tullut opintopisteiden määrä jos se on sallittu arvo, muuten -1
     */
    public int checkAndGetCredits(Scanner reader) {
        int credits = 0;
        try {
            credits = Integer.parseInt(reader.nextLine());
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
