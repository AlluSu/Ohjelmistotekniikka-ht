package opintorekisteri.domain;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Luokka joka kuvaa yksittäistä kurssia.
 * @author Aleksi Suuronen
 */
public class Course {
    
    private final String name;
    private final int credits;
    private String faculty;
    private String formOfStudy;
    private String grading;
    private boolean active;
    private final User user;
    
    
    /**
     * Course-olion konstruktori
     * @param name Kurssin nimi
     * @param credits Kurssin opintopisteet
     * @param faculty Kurssin tiedekunta
     * @param formOfStudy Kurssin suoritusmuoto
     * @param grading Kurssin arvosteluasteikko
     * @param active Onko kurssi aktviinen vai ei
     * @param user Keneen käyttäjään kurssi on liitetty
     */
    public Course(String name, int credits, String faculty, String formOfStudy, String grading, boolean active, User user) {
        this.name = name;
        this.credits = credits;
        this.faculty = faculty;
        this.formOfStudy = formOfStudy;
        this.grading = grading;
        this.active = active;
        this.user = user;
    }
    
    
    /**
     * Palauttaa kurssin tiedekunnan.
     * @return Kurssin tiedekunta
     */
    public String getFaculty() {
        return faculty;
    }
    
    
    /**
     * Palauttaa kurssin suoritusmuodon.
     * @return Kurssin suoritusmuoto
     */
    public String getFormOfStudy() {
        return formOfStudy;
    }
    
    
    /**
     * Palauttaa kurssin arvosteluasteikon.
     * @return Kurssin arvosteluasteikko
     */
    public String getGrading() {
        return grading;
    }
    
    
    /**
     * Palauttaa kurssi-olion nimen.
     * @return kurssi-olion nimi
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * Palauttaa kurssi-olion opintopistemäärän.
     * @return kurssi-olion opintopistemäärän
     */
    public int getCredits() {
        return credits;
    }
    
    
    /**
     * Palauttaa tiedon siitä onko kurssi-olio aktiivinen vai ei.
     * @return True jos kurssi on aktiivinen, muuten false
     */
    public boolean isActive() {
        return active;
    }
    
    
    /**
     * Metodi joka muuttaa kurssi-olion tilan aktiivisesta epäaktiiviseksi.
     */
    public void setUnactive() {
        active = false;
    }
  
    
    /**
     * Palauttaa käyttäjän joka on liitetty kyseiseen kurssiin.
     * @return Käyttäjä-olio joka on liitetty kurssiin.
     */
    public User getUser() {
        return user;
    }
}

