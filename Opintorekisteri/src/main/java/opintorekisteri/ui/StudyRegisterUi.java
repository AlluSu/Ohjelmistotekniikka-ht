package opintorekisteri.ui;


import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import opintorekisteri.domain.Course;
import opintorekisteri.domain.CourseService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Luokka joka vastaa käyttöliittymästä
 * ATM tekstikäyttöliittymä
 * @author Aleksi Suuronen
 */
public class StudyRegisterUi {
    private Scanner reader;
    private Map<String, String> commands;
    private CourseService service;
    
    
    /**
     * Käyttöliittymän konstruktori
     * @param reader Scanner-olio joka hoitaa syötteen lukemisen käyttäjältä
     * @param service CourseService-luokan olio joka "keskustelee" domain-kansion puolella olevien luokkien kanssa
     */
    public StudyRegisterUi(Scanner reader, CourseService service) {
        this.reader = reader;
        this.service = service;
        
        service = new CourseService();
        commands = new TreeMap<>();
        commands.put("Poistu", "Poistu ohjelmasta");
        commands.put("Apua", "Tulostaa ohjeet konsoliin");
        commands.put("Lisaa", "Lisää kurssi aktiiviseksi");
        commands.put("Listaa", "Listaa aktiiviset kurssit");
    }

    
    /**
     * Funktio joka hoitaa ohjelman pyörittämisen ja käyttöliittymän.
     */
    public void run() {
        printWhenStarted();
        while (true) {
            System.out.println("\n");
            System.out.println("Syötä komento:");
            String command = reader.nextLine();
            if (!commands.keySet().contains(command)) {
                System.out.println("Virheellinen komento!");
                printCommands();
            }
            else if (command.equals("Poistu")) {
                break;
            }
            else if (command.equals("Lisaa")) {
                addCourse();
            }
            else if (command.equals("Listaa")) {
                listCourses();
            }
            else if (command.equals("Apua")) {
                printCommands();
            }
            else {
                break;
            }
        }
        System.out.println("Poistutaan ohjelmasta!");
    }
    
    
    /**
     * Funktio joka hakee CourseService-luokasta aktiiviset kurssit ja listaa ne konsoliin.
     */
    public void listCourses() {
        System.out.println("Aktiiviset kurssit:");
        ArrayList<Course> courses = new ArrayList<Course>();
        courses = service.getCourses();
        if (courses.size() < 1) {
            System.out.println("Ei aktiivisia kursseja tällä hetkellä");
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("\n");
            System.out.println("Nimi: " + courses.get(i).getName() + " ");
            System.out.println("Laajuus: " + courses.get(i).getCredits());
        }
    }
    
    
    /**
     * Funktio joka lisää kurssin CourseService-luokan kursseihin aktiiviseksi
     */
    public void addCourse() {
        System.out.println("Syötä kurssin tiedot:");
        System.out.println("Kurssin nimi:");
        String name = service.checkAndGetName(reader);
        if (name == null) {
            return;
        }
        System.out.println("Kurssin laajuus opintopisteinä:");
        int credits = service.checkAndGetCredits(reader);
        if (credits == -1) {
            return;
        }
        boolean added = service.createCourse(name, credits);
        if (added) {
            System.out.println("Kurssi on lisätty onnistuneesti");
        } else {
            System.out.println("Kurssin lisäys epäonnistui!");
        }
    }
   
     
    /**
     * Funktio joka tulostaa sovelluksessa käytössä olevat komennot
     */
    public void printCommands() {
        for (Map.Entry<String, String> entry: commands.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }    
    }
    
    
    /**
     * Funktio joka suoritetaan sovelluksen käynnistyessä ja tulostaa konsoliin tietoja ohjelmasta
     */
    public void printWhenStarted() {
        System.out.println("Tervetuloa Opintorekisteri-ohjelmaan!\n");
        System.out.println("Ohjelman tarkoitus on pitää käyttäjä kartalla omista aktiivisista opinnoistaan.\n");
        System.out.println("Tekstikäyttöliittymä on hyvin helppokäyttöinen ja toimii seuraavilla suomenkielisillä komennoilla\n");
        printCommands();
    }
    
    
    /**
     * Käyttöliittymä-luokan pääohjelma
     * @param args Komentoriviparametrit
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        CourseService courseService = new CourseService();
        StudyRegisterUi application = new StudyRegisterUi(reader, courseService);
        application.run();
    }
}
