package opintorekisteri.ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import opintorekisteri.domain.Course;
import opintorekisteri.domain.CourseService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Luokka joka vastaa käyttöliittymästä
 * ATM graafinen JavaFX-käyttöliittymä
 * @author Aleksi Suuronen
 */
public class StudyRegisterUi extends Application{
    private final CourseService service = new CourseService();
    
    @Override
    public void start(Stage stage) {
       BorderPane borderpane = new BorderPane();
       Insets padding = new Insets(10, 10, 10, 10);
       stage.setTitle("Opintorekisteri");
       stage.setWidth(1000);
       stage.setHeight(500);
        
       TableView activeCoursesTable = new TableView();
       TableView pastCoursesTable = new TableView();
       Label activeCoursesPlaceholder = new Label("Ei aktiivisia kursseja");
       activeCoursesTable.setPlaceholder(activeCoursesPlaceholder);
       Label pastCoursesPlaceholder = new Label("Ei epäaktiivisia kursseja");
       pastCoursesTable.setPlaceholder(pastCoursesPlaceholder);
       
       activeCoursesTable.setEditable(false);
       pastCoursesTable.setEditable(false);
       activeCoursesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       pastCoursesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       
       TableColumn courseNameColumn = new TableColumn("Kurssin nimi");
       TableColumn courseCreditsColumn = new TableColumn("Kurssin laajuus)");
       courseNameColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("name")
       );
       courseCreditsColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("credits")
       );
       
       activeCoursesTable.getColumns().addAll(courseNameColumn, courseCreditsColumn);
       
       TableColumn pastCoursesNameColumn = new TableColumn("Kurssin nimi");
       TableColumn pastCoursesCreditsColumn = new TableColumn("kurssin laajuus");
       pastCoursesNameColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("name")
       );
       pastCoursesCreditsColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("credits")
       );
       
       pastCoursesTable.getColumns().addAll(pastCoursesNameColumn,pastCoursesCreditsColumn);
       
       VBox leftSideVBox = new VBox();
       VBox rightSideVBox = new VBox();
       VBox middleVBox = new VBox();
       leftSideVBox.setPadding(padding);
       rightSideVBox.setPadding(padding);
       middleVBox.setPadding(padding);
       
       HBox bottomHBox = new HBox();
       bottomHBox.setPadding(padding);
       
       Label courseInfoLabel = new Label("Syötä kurssin tiedot");
       Label markAsDoneLabel = new Label("Merkitse kurssi tehdyksi");
       Label pastCoursesLabel = new Label("Menneet kurssit");
       Label activeCoursesLabel = new Label("Aktiiviset kurssit");
       Label insertCourseNameLabel = new Label("Kurssin nimi");
       Label insertCourseCreditsLabel = new Label("Kurssin laajuus (opintopisteinä)");
       courseInfoLabel.setPadding(padding);
       markAsDoneLabel.setPadding(padding);
       pastCoursesLabel.setPadding(padding);
       activeCoursesLabel.setPadding(padding);
       insertCourseNameLabel.setPadding(padding);
       insertCourseCreditsLabel.setPadding(padding);
       
       leftSideVBox.getChildren().addAll(activeCoursesLabel, activeCoursesTable);
       rightSideVBox.getChildren().addAll(pastCoursesLabel, pastCoursesTable);
       
       ObservableList<Course> activeCoursesAsObservableList = FXCollections.observableArrayList();
       ObservableList<Course> pastCoursesAsObservableList = FXCollections.observableArrayList();
       
       TextField courseNameInput = new TextField();
       TextField courseCreditsInput = new TextField();
       courseNameInput.setPadding(padding);
       courseCreditsInput.setPadding(padding);
       Button addCourseButton = new Button("Lisää kurssi");
       addCourseButton.setPadding(padding);
       Button removeCourseButton = new Button("Poista kurssi");
       removeCourseButton.setPadding(padding);

       removeCourseButton.setOnAction((ActionEvent e) -> {
          if (activeCoursesTable.getSelectionModel().getSelectedItem() == null) {
              Alert alert = new Alert(AlertType.INFORMATION);
              alert.setTitle("Virhe");
              alert.setHeaderText("Kurssin poisto epäonnistui!");
              alert.setContentText("Mahdollisia syitä:\nKurssia ei ole valittu");
              alert.showAndWait();
          }
          else {
              Course helperCourse = (Course) activeCoursesTable.getSelectionModel().getSelectedItem();
              activeCoursesTable.getItems().remove(activeCoursesTable.getSelectionModel().getSelectedItem());
              String courseName = helperCourse.getName();
              service.deleteCourse(courseName);
          }
       });
       
       addCourseButton.setOnAction((ActionEvent e) -> {
           String courseName = courseNameInput.getText();
           String courseCredits = courseCreditsInput.getText();
           boolean added = service.createCourse(courseName, courseCredits);
           if (added) {
               activeCoursesAsObservableList.add(service.findCourseByName(courseName));
               activeCoursesTable.setItems(activeCoursesAsObservableList);
           }
           else {
               Alert alert = new Alert(AlertType.ERROR);
               alert.setTitle("Virhe");
               alert.setHeaderText("Kurssin lisäämisessä tapahtui virhe!");
               alert.setContentText("Mahdollisia virhetilanteita:\nKurssi on jo olemassa\ntila täynnä\nvirhe syötteessä");
               alert.showAndWait();
           } 
       });
       
       bottomHBox.getChildren().addAll(courseInfoLabel, insertCourseNameLabel, courseNameInput, insertCourseCreditsLabel, courseCreditsInput, addCourseButton);
       
       Button markCourseUnactiveButton = new Button("Siirrä kurssi epäaktiiviseksi");
       markCourseUnactiveButton.setPadding(padding);
       markCourseUnactiveButton.setOnAction ((ActionEvent e) -> {
           if (activeCoursesTable.getSelectionModel().getSelectedItem() == null) {
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("Virhe!");
               alert.setHeaderText("Kurssin epäaktivointi epäonnistui!");
               alert.setContentText("Mahdollisia syitä:\nKurssia ei ole valittu");
               alert.showAndWait();
           }
           else {
               pastCoursesAsObservableList.add((Course)activeCoursesTable.getSelectionModel().getSelectedItem());
               pastCoursesTable.setItems(pastCoursesAsObservableList);
               activeCoursesTable.getItems().remove((Course)activeCoursesTable.getSelectionModel().getSelectedItem());
           }
       });
       
       middleVBox.getChildren().addAll(markAsDoneLabel, markCourseUnactiveButton, removeCourseButton);
 //      middleVBox.getChildren().addAll(markAsDoneLabel, markCourseUnactiveButton);
       borderpane.setLeft(leftSideVBox);
       borderpane.setRight(rightSideVBox);
       borderpane.setCenter(middleVBox);
       borderpane.setBottom(bottomHBox);
       
       Scene nakyma = new Scene(borderpane);
       stage.setScene(nakyma);
       stage.show();
    }
    
    
    /**
     * Käyttöliittymä-luokan pääohjelma
     * @param args Komentoriviparametrit
     */
    public static void main(String[] args) {
          launch(StudyRegisterUi.class);
    }
}