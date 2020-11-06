package opintorekisteri.domain;

import java.util.ArrayList;
import opintorekisteri.dao.CourseDao;
import opintorekisteri.dao.UserDao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Luokka joka vastaa sovelluslogiikasta
 * @author Aleksi Suuronen
 */
public class CourseService {
    
    private ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<Course> unactive = new ArrayList<Course>();
    
    public CourseService(ArrayList<Course> courses) {
        this.courses = courses;
    }
    
    
    public CourseService() {}
    
    
    public ArrayList<Course> getCourses() {
       return courses;
    }
    
    
    public boolean createCourse(String name, int credits) {
        Course course = new Course(name, credits, true);
        return courses.add(course);
    }
}
