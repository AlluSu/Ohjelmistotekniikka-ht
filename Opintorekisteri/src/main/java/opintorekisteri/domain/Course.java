package opintorekisteri.domain;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Yksittäistä kurssia kuvaava luokka.
 * @author Aleksi Suuronen
 */
public class Course {
    
    
    private int id;
    private String name;
    private int credits;
    private boolean active;
    private User user;
    
    
    /**
     * Course-olion konstruktori, jossa id & käyttäjä attribuutit mukana.
     * @param id Kurssin id, jolla se voidaan yksikäsitteisesti tunnistaa
     * @param name Kurssin nimi
     * @param credits Kurssin opintopisteet
     * @param active Onko kurssi aktviinen vai ei
     * @param user Keneen käyttäjään kurssi on liitetty
     */
    public Course(int id, String name, int credits, boolean active, User user) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.active = active;
        this.user = user;
    }
    
    
    /**
     * Course-olion konstruktori.
     * @param name Kurssin nimi
     * @param credits Kurssin opintopisteet
     * @param active Onko kurssi aktiivinen vai ei
     */
    public Course(String name, int credits, boolean active) {
        this.name = name;
        this.credits = credits;
        this.active = active;
    }
    
    
    /**
     * Metodi joka asettaa id:n kurssioliolle.
     * @param id id-numero jonka kurssi-olio saa
     */
    public void setId(int id) {
        this.id = id;
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

