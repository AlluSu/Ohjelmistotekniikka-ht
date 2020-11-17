package opintorekisteri.domain;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Luokka joka kuvaa yksittäistä käyttäjää
 * @author Aleksi Suuronen 
 */
public class User {
    
    private String name;
    private String username;
    private int id;
    
    /**
     * User-luokan konstruktori
     * @param name Käyttäjän oikea nimi
     * @param username Käyttäjän laatima käyttäjänimi ohjelmaan
     * @param id Käyttäjän id
     */
    public User(String name, String username, int id) {
        this.name = name;
        this.username = username;
        this.id = id;
    }
    
    
    /**
     * Metodi joka palauttaa käyttäjän nimen
     * @return käyttäjän oikea nimi
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * Metodi joka palauttaa käyttäjän id:n
     * @return käyttäjän id
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Metodi joka palauttaa käyttäjän käyttäjätunnuksen
     * @return käyttäjän käyttäjätunnus
     */
    public String getUsername() {
        return username;
    }
    
}