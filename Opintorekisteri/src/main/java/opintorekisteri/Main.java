/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintorekisteri;

/**
 * Main-luokka, josta koko sovellus lähtee liikkeelle ja käyntiin.
 * @author Aleksi Suuronen
 */
public class Main {
    
    
    /**
     * Main-luokka ja pääohjelma joka kutsuu käyttöliittymä-luokan Main-luokkaa.
     * @param args Komentoriviargumentit
     */
    public static void main(String[] args) {
        opintorekisteri.ui.StudyRegisterUi.main(args);
    }
}