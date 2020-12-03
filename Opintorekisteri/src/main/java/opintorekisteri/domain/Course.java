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
    private boolean active;
    private final User user;
    
    
    /**
     * Course-olion konstruktori, jossa id & käyttäjä attribuutit mukana.
     * @param name Kurssin nimi
     * @param credits Kurssin opintopisteet
     * @param active Onko kurssi aktviinen vai ei
     * @param user Keneen käyttäjään kurssi on liitetty
     */
    public Course(String name, int credits, boolean active, User user) {
        this.name = name;
        this.credits = credits;
        this.active = active;
        this.user = user;
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
    
    
    @Override
    public String toString() {
        return this.name + " " + this.credits + " " + this.active + " " + this.user.toString();
    }
}

