package opintorekisteri.domain;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Luokka joka kuvaa yksittäistä käyttäjää.
 * @author Aleksi Suuronen 
 */
public class User {
    
    private final String name;
    private final String username;
    
    /**
     * User-luokan konstruktori.
     * @param name Käyttäjän oikea nimi
     * @param username Käyttäjän käyttäjänimi
     */
    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }
    
    
    /**
     * Metodi joka palauttaa käyttäjän nimen.
     * @return käyttäjän oikea nimi
     */
    public String getName() {
        return name;
    }
    
    
    @Override
    public String toString() {
        return this.name + " " + this.username;
    }
    
    
    /**
     * Metodi joka palauttaa käyttäjän käyttäjätunnuksen.
     * @return käyttäjän käyttäjätunnus
     */
    public String getUsername() {
        return username;
    }   
}
