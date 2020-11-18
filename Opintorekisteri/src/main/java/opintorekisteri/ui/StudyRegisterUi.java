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
        commands.put("poistu", "Poistu ohjelmasta");
        commands.put("apua", "Tulostaa ohjeet konsoliin");
        commands.put("lisaa", "Lisää kurssi aktiiviseksi");
        commands.put("listaa", "Listaa kaikki kurssit");
        commands.put("poista", "Poistaa aktiivisen kurssin listasta. HUOM! Ei merkitse sitä epäaktiiviseksi vaan poistaa kokonaan");
        commands.put("tehty", "Merkitsee kurssin valmiiksi ja siirtää sen epäaktiiviseksi");
    }

    
    /**
     * Funktio joka hoitaa ohjelman pyörittämisen ja käyttöliittymän.
     */
    public void run() {
        printWhenStarted();
        while (true) {
            System.out.println("Syötä komento:");
            String command = reader.nextLine();
            if (!commands.keySet().contains(command)) {
                System.out.println("Virheellinen komento!");
                printCommands();
            }
            else if (command.equals("poistu")) {
                break;
            }
            else if (command.equals("lisaa")) {
                addCourse();
            }
            else if (command.equals("listaa")) {
                listCourses();
                listUnactiveCourses();
            }
            else if (command.equals("apua")) {
                printCommands();
            }
            else if (command.equals("poista")) {
                deleteCourse();
            }
            else if (command.equals("tehty")) {
                markDone();
            }
            else {
                break;
            }
        }
        System.out.println("Poistutaan ohjelmasta!");
    }
    
    
    public void markDone() {
        System.out.println("==================");
        System.out.println("Anna suoritetun kurssin nimi joka muuttuu epäaktiiviseksi:");
        String name = reader.nextLine();
        boolean done = service.markCourseAsDone(name);
        if (done) {
            System.out.println("Kurssi merkitty onnistuneesti epäaktiiviseksi");
        } else {
            System.out.println("Kurssia ei voitu merkatao onnsituneesti epäaktiiviseksi");
        }
        System.out.println("==================");
    }
    
    
    /**
     * 
     */
    public void deleteCourse() {
        System.out.println("==================");
        System.out.println("Anna poistettavan kurssin nimi:");
        String name = reader.nextLine();
        boolean deleted = service.deleteCourse(name);
        if (deleted) {
            System.out.println("Poisto oli onnistunut!");
        } else {
            System.out.println("Poisto epäonnistui. Tarkista kurssin nimi!");
        }
        System.out.println("==================");
    }
    
    
    /**
     * Funktio joka hakee CourseService-luokasta aktiiviset kurssit ja listaa ne konsoliin.
     */
    public void listCourses() {
        System.out.println("Aktiiviset kurssit:");
        ArrayList<Course> courses = new ArrayList<>();
        courses = service.getCourses();
        if (courses.size() < 1) {
            System.out.println("Ei aktiivisia kursseja tällä hetkellä");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("==================");
            System.out.println("Nimi: " + courses.get(i).getName());
            System.out.println("Laajuus: " + courses.get(i).getCredits());
        }
    }
    
    
    /**
     * 
     */
    public void listUnactiveCourses() {
        System.out.println("Suoritetut kurssit:");
        ArrayList<Course> courses = new ArrayList<>();
        courses = service.getUnactiveCourses();
        if (courses.size() < 1) {
            System.out.println("Ei yhtään suoritettua kurssia");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("==================");
            System.out.println("Nimi: " + courses.get(i).getName());
            System.out.println("Laajuus: " + courses.get(i).getCredits());
        }
    }
    
    
    /**
     * Funktio joka lisää kurssin CourseService-luokan kursseihin aktiiviseksi
     */
    public void addCourse() {
        System.out.println("==================");
        System.out.println("Syötä kurssin tiedot:");
        System.out.println("Kurssin nimi:");
        String name = reader.nextLine();
        System.out.println("Kurssin laajuus opintopisteinä:");
        String credits = reader.nextLine();
        boolean added = service.createCourse(name, credits);
        if (added) {
            System.out.println("Kurssi on lisätty onnistuneesti");
        } else {
            System.out.println("Kurssin lisäys epäonnistui!");
        }
        System.out.println("==================");
    }
   
     
    /**
     * Funktio joka tulostaa sovelluksessa käytössä olevat komennot
     */
    public void printCommands() {
        commands.entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        });    
    }
    
    
    /**
     * Funktio joka suoritetaan sovelluksen käynnistyessä ja tulostaa konsoliin tietoja ohjelmasta
     */
    public void printWhenStarted() {
        System.out.println("==================");
        System.out.println("Tervetuloa Opintorekisteri-ohjelmaan!");
        System.out.println("Ohjelman tarkoitus on pitää käyttäjä kartalla omista aktiivisista opinnoistaan.");
        System.out.println("Tekstikäyttöliittymä on hyvin helppokäyttöinen ja toimii seuraavilla suomenkielisillä komennoilla.");
        System.out.println("==================");
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
