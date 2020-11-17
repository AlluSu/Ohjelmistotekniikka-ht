/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintorekisteri;
import java.util.Scanner;
import opintorekisteri.domain.CourseService;
import opintorekisteri.ui.StudyRegisterUi;

/**
 *
 * @author Aleksi Suuronen
 */
public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner (System.in);
        CourseService courseService = new CourseService();
        StudyRegisterUi studyRegister;
        studyRegister = new StudyRegisterUi(reader, courseService);
        StudyRegisterUi.main(args);
    }
}
