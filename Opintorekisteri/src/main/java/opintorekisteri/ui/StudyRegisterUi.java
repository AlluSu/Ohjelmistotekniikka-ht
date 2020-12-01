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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private Scene mainScene;
    private Scene newUserScene;
    private Scene loginScene;
    private TableView activeCoursesTable;
    private TableView pastCoursesTable;
    private ObservableList<Course> activeCoursesAsObservableList;
    private ObservableList<Course> pastCoursesAsObservableList;
    
    
    /**
     * Funktio joka aina kirjautumisen yhdessä tyhjentää TableView-komponentit, 
     * että uuden kirjautujan data tulee oikein näkyviin.
     */
    public void refreshData() {
       activeCoursesTable.getItems().clear();
       pastCoursesTable.getItems().clear();
       activeCoursesAsObservableList = FXCollections.observableArrayList();
       pastCoursesAsObservableList = FXCollections.observableArrayList();
       activeCoursesAsObservableList.addAll(service.getCourses());
       pastCoursesAsObservableList.addAll(service.getUnactiveCourses());
       activeCoursesTable.setItems(activeCoursesAsObservableList);
       pastCoursesTable.setItems(pastCoursesAsObservableList);
    }
    
    
    @Override
    public void start(Stage stage) {
  
       // loginScene alkaa tästä eli ikkuna jossa kirjaudutaan sisään tai vaihtoehtoisesti luodaan uusi käyttäjä.
       //=======================================================================
       Label logged = new Label("");
       VBox loginPane = new VBox(10);
       HBox inputPane = new HBox(10);
       loginPane.setPadding(new Insets(10));
       Label loginLabel = new Label("Käyttäjätunnus");
       TextField usernameInput = new TextField();
       
       inputPane.getChildren().addAll(loginLabel, usernameInput);
       Label loginMessage = new Label();
       
       Button loginButton = new Button("Kirjaudu");
       Button createButton = new Button("Luo uusi käyttäjä");
       Button helpButton = new Button("Ohje");
       Button exitButton = new Button("Poistu");
       
       exitButton.setOnAction((ActionEvent e) -> {
          stage.close(); 
       });
       
       helpButton.setOnAction((ActionEvent e) -> {
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Opintorekisterin ohjeet");
           alert.setHeaderText(null);
           alert.setContentText("Tervetuloa opintorekisteri-ohjelmaan.\n" +  
                   "Tämän hetkisessä versiossa kirjautuminen on jo käytössä.\n" + 
                   "Tämä versio ei vielä käytä tietokantaa, joten käyttäjät ja tiedot häviävät joka ajon jälkeen.\n" +
                   "Luo ensin käyttäjä, ja sitten kirjaudu sillä käyttäjällä sisään. Voit luoda useita käyttäjiä.\n" + 
                   "Lisäksi tietokannan puuttumisen takia, usealla eri käyttäjällä ei voi olla aktiivisena tismalleen samannimistä kurssia.");
           alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
           alert.showAndWait();
       });
       
       loginButton.setOnAction((ActionEvent e) -> {
           String username = usernameInput.getText();
           if (username.trim().equals("")) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Virhe kirjautumisessa");
                alert.setHeaderText(null);
                alert.setContentText("Syötä käyttäjätunnus!");
                alert.showAndWait();
                usernameInput.setText("");
           }
           if (service.login(username)) {
                stage.setScene(mainScene);
                usernameInput.setText("");
                logged.setText("Kirjautuneena: " + service.getLoggedUser().getUsername());
                refreshData();
           }
       });
       
       createButton.setOnAction((ActionEvent e) -> {
          stage.setScene(newUserScene);
       }); 
       
       Label userCreateMessage = new Label();
       
       loginPane.getChildren().addAll(userCreateMessage, inputPane, loginButton, createButton, helpButton, exitButton);
       loginScene = new Scene(loginPane, 300, 300);
       
       // newUserScene alkaa tämän jälkeen eli ikkuna jossa käyttäjä voi luoda uuden käyttäjän sovellukseen.
       //=======================================================================
       
       VBox newUserPane = new VBox(10);
       HBox newUsernamePane = new HBox(10);
       newUsernamePane.setPadding(new Insets(10));
       TextField newUsernameInput = new TextField();
       Label newUsernameLabel = new Label("Käyttäjätunnus");
       newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);
       
       HBox newNamePane = new HBox(10);
       newNamePane.setPadding(new Insets(10));
       TextField newNameInput = new TextField();
       Label newNameLabel = new Label("Nimi");
       newNamePane.getChildren().addAll(newNameLabel, newNameInput);
       
       Button createNewUserButton = new Button("Luo uusi käyttäjä");
       createNewUserButton.setPadding(new Insets(10));
       
       Button cancelButton = new Button("Peruuta");
       cancelButton.setPadding(new Insets(10));
       
       Label notificationLabel = new Label();
       
       cancelButton.setOnAction((ActionEvent e) -> {
           stage.setScene(loginScene); 
        });
       
       createNewUserButton.setOnAction((ActionEvent e) -> {
           String username = newUsernameInput.getText();
           String name = newNameInput.getText();
           if (username.trim().length() < 3) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Virhe käyttäjän luomisessa");
                alert.setHeaderText(null);
                alert.setContentText("Käyttäjätunnus on oltava vähintään 3 merkkiä pitkä");
                alert.showAndWait();
                newUsernameInput.setText("");
                newNameInput.setText("");
           }
           else if (name.trim().length() < 3) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Virhe käyttäjän luomisessa");
                alert.setHeaderText(null);
                alert.setContentText("Nimi on oltava vähintään 3 merkkiä pitkä");
                alert.showAndWait();
                newUsernameInput.setText("");
                newNameInput.setText("");
           }
           else {
               if (service.createUser(name, username)) {
                    newUsernameInput.setText("");
                    newNameInput.setText("");
                    userCreateMessage.setText("Käyttäjä " + username + " luotu onnistuneesti");
               }
               else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Virhe");
                    alert.setHeaderText("Käyttäjän luonti epäonnistui!");
                    alert.setContentText("Mahdollisia syitä:\nKäyttäjäntunnus on jo varattu!! Valitse jokin muu!");
                    alert.showAndWait();
               }
           }
           stage.setScene(loginScene);
       });
       
       newUserPane.getChildren().addAll(newUsernamePane, newNamePane, createNewUserButton, cancelButton, notificationLabel);
       newUserScene = new Scene(newUserPane, 300, 300);
       
       // mainScene alkaa tämän jälkeen eli siis ikkuna jossa suurin osa toiminnallisuudesta tapahtuu
       //=======================================================================
       
       BorderPane borderpane = new BorderPane();
       Insets padding = new Insets(10, 10, 10, 10);
       stage.setTitle("Opintorekisteri");
       stage.setWidth(1000);
       stage.setHeight(500);
              
       activeCoursesTable = new TableView();
       pastCoursesTable = new TableView();
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
       
       TextField courseNameInput = new TextField();
       TextField courseCreditsInput = new TextField();
       courseNameInput.setPadding(padding);
       courseCreditsInput.setPadding(padding);
       Button addCourseButton = new Button("Lisää kurssi");
       addCourseButton.setPadding(padding);
       Button removeCourseButton = new Button("Poista kurssi");
       removeCourseButton.setPadding(padding);
       Button logoutButton = new Button("Kirjaudu ulos");
       logoutButton.setPadding(padding);
       
       logoutButton.setOnAction ((ActionEvent e) -> {
           service.logout();
           stage.setScene(loginScene);
       });

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
               courseNameInput.setText("");
               courseCreditsInput.setText("");
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
               Course course = (Course) activeCoursesTable.getSelectionModel().selectedItemProperty().get();
               String data = (String) course.getName();
               service.markCourseAsDone(data);
               pastCoursesAsObservableList.add((Course)activeCoursesTable.getSelectionModel().getSelectedItem());
               pastCoursesTable.setItems(pastCoursesAsObservableList);
               activeCoursesTable.getItems().remove((Course)activeCoursesTable.getSelectionModel().getSelectedItem());       
           }
       });
       middleVBox.getChildren().addAll(markAsDoneLabel, markCourseUnactiveButton, removeCourseButton, logoutButton, logged);
       borderpane.setLeft(leftSideVBox);
       borderpane.setRight(rightSideVBox);
       borderpane.setCenter(middleVBox);
       borderpane.setBottom(bottomHBox);
       
       mainScene = new Scene(borderpane);
       stage.setScene(loginScene);
       stage.show();
    }
    
    
    /**
     * Käyttöliittymä-luokan pääohjelma
     * @param args Komentoriviparametrit
     */
    public static void main(String[] args) {
          launch(args);
    }
}