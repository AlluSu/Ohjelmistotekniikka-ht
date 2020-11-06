package opintorekisteri.domain;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Yksittäistä kurssia kuvaava luokka
 * @author Aleksi Suuronen
 */
public class Course {
    
    
    private int id;
    private String name;
    private int credits;
    private boolean active;
    private User user;
    
    
    public Course(int id, String name, int credits, boolean active, User user) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.active = active;
        this.user = user;
    }
    
    
    public Course(String name, int credits, boolean active) {
        this.name = name;
        this.credits = credits;
        this.user = user;
    }
    
    
    public void setId(int id) {
        this.id = id;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public int getCredits() {
        return credits;
    }
    
    
    public boolean isActive() {
        return active;
    }
    
    
    public void setUnactive() {
        active = false;
    }
    
    public User getUser() {
        return user;
    }
    
    
}

