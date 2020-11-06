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
    
    public User(String name, String username, int id) {
        this.name = name;
        this.username = username;
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
}
