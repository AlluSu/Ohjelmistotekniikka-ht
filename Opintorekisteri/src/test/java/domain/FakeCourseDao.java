/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import opintorekisteri.domain.Course;
import opintorekisteri.domain.User;

/**
 * Testiluokka joka simuloi kurssien talletusta ilman tietokantaa.
 * @author Aleksi Suuronen
 */
public class FakeCourseDao {
    ArrayList<Course> active = new ArrayList<>();
    ArrayList<Course> unactive = new ArrayList<>();
    
    public FakeCourseDao() {}
    
    public FakeCourseDao(ArrayList<Course> active, ArrayList<Course> unactive) {
        this.active = active;
        this.unactive = unactive;
    }
    
    public boolean CourseExistsWithUser(String coursename, User user) {
        for (Course c:active) {
            if (c.getName().equals(coursename) && c.getUser().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean changeActiveToUnactive(Course course) {
        if (!course.isActive()) {
            return false;
        }
        course.setUnactive();
        active.remove(course);
        unactive.add(course);
        return true;
    }
    
    public boolean deleteCourse(Course course) {
        return active.remove(course);
    }
    
    public boolean addCourse(Course course) {
        return active.add(course);
    }
    
    public ArrayList<Course> getActiveCoursesByUser(User user) {
        ArrayList<Course> c = new ArrayList<>();
        for (Course course: active) {
            if (course.getUser().equals(user)) {
                c.add(course);
            }
        }
        return c;
    }
    
    public ArrayList<Course> getUnactiveCoursesByUser(User user) {
    ArrayList<Course> c = new ArrayList<>();
    for (Course course: unactive) {
        if (course.getUser().equals(user)) {
            c.add(course);
        }
    }
    return c;
}
}
