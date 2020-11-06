package opintorekisteri.ui;


import java.util.ArrayList;
import java.util.Iterator;
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
    
    public StudyRegisterUi(Scanner reader, CourseService service) {
        this.reader = reader;
        this.service = service;
        
        service = new CourseService();
        commands = new TreeMap<>();
        commands.put("Lisaa", "Lisää kurssi aktiiviseksi");
        commands.put("Listaa", "Listaa aktiiviset kurssit");
    }

    
    public void run() {
        printWhenStarted();
        while (true) {
            System.out.println("Syötä komento\n");
            String command = reader.nextLine();
            if (!commands.keySet().contains(command)) {
                System.out.println("Virheellinen komento!");
                printCommands();
            }
            else if (command.toLowerCase().equals("lisaa")) {
                addCourse();
            }
            else if (command.toLowerCase().equals("listaa")) {
                listCourses();
            }
            else {
                break;
            }
        }
    }
    
    
    public void listCourses() {
        System.out.println("Aktiiviset kurssit:");
        ArrayList<Course> courses = new ArrayList<Course>();
        courses = service.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            System.out.print("Nimi " + courses.get(i).getName() + " ");
            System.out.print("Laajuus " + courses.get(i).getCredits());
        }
    }
    
    
    public void addCourse() {
        System.out.println("Syötä kurssin tiedot:");
        System.out.println();
        System.out.println("Kurssin nimi:");
        String name = reader.nextLine();
        System.out.println("Kurssin laajuus opintopisteinä:");
        int credits = Integer.parseInt(reader.nextLine());
        boolean added = service.createCourse(name, credits);
        if (added) {
            System.out.println("Kurssi on lisätty onnistuneesti");
        } else {
            System.out.println("Kurssin lisäys epäonnistui!");
        }
    }
    
    
    public void printCommands() {
        for (Map.Entry<String, String> entry: commands.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }    
    }
    
    
    public void printWhenStarted() {
        System.out.println("Tervetuloa Opintorekisteri-ohjelmaan!\n");
        System.out.println("Ohjelman tarkoitus on pitää käyttäjä kartalla omista aktiivisista opinnoistaan.\n");
        System.out.println("Tekstikäyttöliittymä on hyvin helppokäyttöinen ja toimii seuraavilla suomenkielisillä komennoilla\n");
        printCommands();
    }
    
    
    public void main(String[] args) {
        run();
    }
}
