package opintorekisteri.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import opintorekisteri.dao.SqlCourseDao;
import opintorekisteri.dao.SqlUserDao;
import opintorekisteri.domain.Course;
import opintorekisteri.domain.CourseService;
import opintorekisteri.domain.User;
import opintorekisteri.domain.UserService;


/**
 * Luokka joka vastaa käyttöliittymästä.
 * @author Aleksi Suuronen
 */
public class StudyRegisterUi extends Application{
    private Scene mainScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene courseScene;
    private TableView activeCoursesTable;
    private TableView pastCoursesTable;
    private ObservableList<Course> activeCoursesAsObservableList;
    private ObservableList<Course> pastCoursesAsObservableList;
    private ArrayList<Course> helperList;
    private Course helperCourse;
    private final String db = "jdbc:sqlite:courses.db";
    private final SqlUserDao userDao = new SqlUserDao(db);
    private final SqlCourseDao courseDao = new SqlCourseDao(db);
    private final CourseService courseService = new CourseService(userDao, courseDao);
    private final UserService userService = new UserService(userDao, courseDao);

    
    /**
     * Funktio joka hakee jokaiselle käyttäjälle datan.
     * @param loggedUser kirjautunut käyttäjä
     * @throws java.sql.SQLException poikkeuskäsittely
     */
    public void refreshData(User loggedUser) throws SQLException {
       activeCoursesTable.getItems().clear();
       pastCoursesTable.getItems().clear();
       
       activeCoursesAsObservableList = FXCollections.observableArrayList();
       pastCoursesAsObservableList = FXCollections.observableArrayList();
       
       helperList = courseService.getCourses(loggedUser);
       if (helperList == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText(null);
            alert.setContentText("Virhe haettaessa käyttäjän tietoja!");
            alert.showAndWait();
       }
       activeCoursesAsObservableList.addAll(helperList);
       
       helperList = courseService.getUnactiveCourses(loggedUser);
       if (helperList == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText(null);
            alert.setContentText("Virhe haettaessa käyttäjän tietoja!");
            alert.showAndWait();
       }
       pastCoursesAsObservableList.addAll(helperList);
       
       activeCoursesTable.setItems(activeCoursesAsObservableList);
       pastCoursesTable.setItems(pastCoursesAsObservableList);
    }
    
    
    @Override
    public void start(Stage stage) throws SQLException {
        
       // CourseScene alkaa tästä
       //=======================================================================
       Label info = new Label("Syötä kurssin tiedot");
       Label courseName = new Label("Kurssin nimi");
       Label courseCredits = new Label("Kurssin laajuus");
       Label faculty = new Label("Kurssin tiedekunta");
       Label formOfStudy = new Label("Kurssin suoritustapa");
       Label formOfGrading = new Label("Kurssin arvosteluasteikko");
       
       TextField nameInput = new TextField();
       TextField creditsInput = new TextField();
       
       ListView<String> faculties = new ListView<>();
       ObservableList<String> facultyOptions = FXCollections.observableArrayList(
       "Bio- ja ympäristötieteellinen", "Eläinlääketieteellinen", "Farmasia", 
       "Humanistinen", "Kasvatustieteellinen", "Lääketieteellinen", "Maatalous-metsätieteellinen",
       "Matemaattis-luonnontieteellinen", "Oikeustieteellinen", "Svenska social- och kommunalhögskolan",
       "Teologinen", "Valtiotieteellinen", "Muu"
       );
       faculties.setItems(facultyOptions);
       
       ListView<String> formsOfStudy = new ListView<>();
       ObservableList<String> studyOptions = FXCollections.observableArrayList(
               "Luentokurssi", "Tentti", "Harjoitustyö/laboratoriotyö", "Essee", "Seminaari",
               "Luento- tai oppimispäiväkirja", "Suullinen esitys", "Harjoittelujakso", "Muu"
       );
       formsOfStudy.setItems(studyOptions);
       
       ListView<String> grading = new ListView<>();
       ObservableList<String> gradingOptions = FXCollections.observableArrayList(
               "1-5", "Hyväksytty-Hylätty"
       );
       grading.setItems(gradingOptions);
       
       info.setPadding(new Insets(10));
       courseName.setPadding(new Insets(10));
       courseCredits.setPadding(new Insets(10));
       faculty.setPadding(new Insets(10));
       formOfStudy.setPadding(new Insets(10));
       formOfGrading.setPadding(new Insets(10));
       nameInput.setPadding(new Insets(10));
       creditsInput.setPadding(new Insets(10));
       faculties.setPadding(new Insets(10));
       formsOfStudy.setPadding(new Insets(10));
       grading.setPadding(new Insets(10));
       info.setPadding(new Insets(10));
       info.setPadding(new Insets(10));
       info.setPadding(new Insets(10));
       info.setPadding(new Insets(10));
       
       Label courseAddedLabel = new Label();
       Button courseButton = new Button("Lisää kurssi");
       Button returnButton = new Button("Peruuta");
       
       returnButton.setOnAction((ActionEvent e) -> {
           nameInput.setText("");
           creditsInput.setText("");
           courseAddedLabel.setText("");
           stage.setScene(mainScene);
       });
       
       courseButton.setOnAction((ActionEvent e) -> {
           String name = nameInput.getText();
           String credits = creditsInput.getText();
           String courseFaculty = faculties.getSelectionModel().getSelectedItem();
           String courseFormOfStudy = formsOfStudy.getSelectionModel().getSelectedItem();
           String courseGrading = grading.getSelectionModel().getSelectedItem();
           if (courseFaculty == null || courseFaculty.equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText(null);
            alert.setContentText("Valitse tiedekunta!");
            alert.showAndWait();
           }
           else if (courseFormOfStudy == null || courseFormOfStudy.equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText(null);
            alert.setContentText("Valitse suoritustapa!");
            alert.showAndWait();
           }
           else if (courseGrading == null || courseGrading.equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText(null);
            alert.setContentText("Valitse arvosteluasteikko!");
            alert.showAndWait();
           }
           else {
               boolean added = false;
            try {
                added = courseService.createCourse(name, credits, courseFaculty, courseFormOfStudy, courseGrading, userService.getLoggedUser());
            } catch (SQLException ex) {
                Logger.getLogger(StudyRegisterUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (added) {
                try {
                     refreshData(userService.getLoggedUser());
                } catch (SQLException ex) {
                    Logger.getLogger(StudyRegisterUi.class.getName()).log(Level.SEVERE, null, ex);
                }
                courseAddedLabel.setText("Kurssi " + name + " on lisätty onnistuneesti!");
                nameInput.setText("");
                creditsInput.setText("");
            }
            else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Virhe");
                alert.setHeaderText("Kurssin lisäämisessä tapahtui virhe!");
                alert.setContentText("Mahdollisia virhetilanteita:\nKurssi on jo olemassa\ntila täynnä\nvirhe syötteessä");
                alert.showAndWait();
            }
           }
       });
       
       VBox courseVBox = new VBox();
       VBox buttonsVBox = new VBox();
       HBox coursePane = new HBox();
       HBox namingBox = new HBox();
       namingBox.getChildren().addAll(courseName, nameInput);
       HBox creditsBox = new HBox();
       creditsBox.getChildren().addAll(courseCredits, creditsInput);
       HBox facultiesBox = new HBox();
       facultiesBox.getChildren().addAll(faculty, faculties);
       HBox studyFormBox = new HBox();
       studyFormBox.getChildren().addAll(formOfStudy, formsOfStudy);
       HBox gradingBox = new HBox();
       gradingBox.getChildren().addAll(formOfGrading, grading);
       
       courseVBox.getChildren().addAll(info, namingBox, creditsBox, facultiesBox, studyFormBox, gradingBox);
       buttonsVBox.getChildren().addAll(courseButton, returnButton);
       coursePane.getChildren().addAll(buttonsVBox, courseVBox, courseAddedLabel);
       courseScene = new Scene(coursePane);
       
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
          Platform.exit();
       });
       
       helpButton.setOnAction((var e) -> {
           if( Desktop.isDesktopSupported()) {
                new Thread(() -> {
                    try {
                        Desktop.getDesktop().browse( new URI( "https://github.com/AlluSu/Ohjelmistotekniikka-ht/blob/master/dokumentaatio/kayttoohje.md" ) );
                        } catch (IOException | URISyntaxException e1) {
                    }
                }).start();
            }
//        Desktop desktop = Desktop.getDesktop();
//        try {
//            URI uri = new URI("https://www.google.com");
//            desktop.browse(uri);
//        } catch (URISyntaxException | IOException ex) {}
       });
       
       
       loginButton.setOnAction((ActionEvent action) -> {
           String username = usernameInput.getText();
           try {
               if (userService.login(username)) {
                   stage.setScene(mainScene);
                   usernameInput.setText("");
                   logged.setText("Kirjautuneena: " + userService.getLoggedUser().getUsername());
                   refreshData(userService.getLoggedUser());
               }
               else {
                 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Virhe");
                 alert.setHeaderText(null);
                 alert.setContentText("Käyttäjää ei löytynyt!");
                 alert.showAndWait();
               }
           } catch (SQLException ex) {
               Logger.getLogger(StudyRegisterUi.class.getName()).log(Level.SEVERE, null, ex);
           }
       });
       
       createButton.setOnAction((ActionEvent e) -> {
          stage.setScene(newUserScene);
       });
       
       HBox mainPane = new HBox();
       loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, createButton, helpButton, exitButton);
       mainPane.getChildren().addAll(loginPane);
       loginScene = new Scene(mainPane, 300, 300);
       
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
       
       Label userCreateMessage = new Label();
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
               notificationLabel.setText("Käyttäjätunnuksen minimipituus on 3 merkkiä!");
           }
           else if (name.trim().length() < 3) {
               notificationLabel.setText("Nimen minimipituus on 3 merkkiä!");
           }
           else {
               try {
                   if(userService.createUser(name, username)) {
                        loginMessage.setText("Käyttäjä " + username + " luotu onnistuneesti!");
                        newUsernameInput.setText("");
                        newNameInput.setText("");
                        stage.setScene(loginScene);
                   }
                   else {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Virhe");
                        alert.setHeaderText("Käyttäjän luonti epäonnistui!");
                        alert.setContentText("Käyttäjätunnus on varattu! Valitse jokin muu.");
                        alert.showAndWait();
                   }
               } catch (SQLException ex) {
                   Logger.getLogger(StudyRegisterUi.class.getName()).log(Level.SEVERE, null, ex);
               }

           }
       });
       
       newUserPane.getChildren().addAll(userCreateMessage, newUsernamePane, newNamePane, createNewUserButton, cancelButton, notificationLabel);
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
       
       TableColumn courseNameColumn = new TableColumn("nimi");
       TableColumn courseCreditsColumn = new TableColumn("laajuus");
       TableColumn courseFacultyColumn = new TableColumn("tiedekunta");
       TableColumn courseFormOfStudyColumn = new TableColumn("suoritustapa");
       TableColumn courseFormOfGrading = new TableColumn("arvostelu");
       
       courseNameColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("name")
       );
       courseCreditsColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("credits")
       );
       courseFacultyColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("faculty")
       );
       courseFormOfStudyColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("formOfStudy")
       );
       courseFormOfGrading.setCellValueFactory(
               new PropertyValueFactory<Course, String>("grading")
       );
       
       activeCoursesTable.getColumns().addAll(courseNameColumn, courseCreditsColumn,
               courseFacultyColumn, courseFormOfStudyColumn, courseFormOfGrading);
       
       TableColumn pastCoursesNameColumn = new TableColumn("nimi");
       TableColumn pastCoursesCreditsColumn = new TableColumn("laajuus");
       TableColumn pastCourseFacultyColumn = new TableColumn("tiedekunta");
       TableColumn pastCourseFormOfStudyColumn = new TableColumn("suoritustapa");
       TableColumn pastCourseFormOfGrading = new TableColumn("arvosteluasteikko");
       
       pastCoursesNameColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("name")
       );
       pastCoursesCreditsColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("credits")
       );
       pastCourseFacultyColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("faculty")
       );
       pastCourseFormOfStudyColumn.setCellValueFactory(
               new PropertyValueFactory<Course, String>("formOfStudy")
       );
       pastCourseFormOfGrading.setCellValueFactory(
               new PropertyValueFactory<Course, String>("grading")
       );
       
       pastCoursesTable.getColumns().addAll(pastCoursesNameColumn,pastCoursesCreditsColumn,
               pastCourseFacultyColumn, pastCourseFormOfStudyColumn, pastCourseFormOfGrading);
       
       VBox leftSideVBox = new VBox();
       VBox rightSideVBox = new VBox();
       VBox middleVBox = new VBox();
       leftSideVBox.setPadding(padding);
       rightSideVBox.setPadding(padding);
       middleVBox.setPadding(padding);
       
       HBox bottomHBox = new HBox();
       bottomHBox.setPadding(padding);
       
       Label pastCoursesLabel = new Label("Menneet kurssit");
       Label activeCoursesLabel = new Label("Aktiiviset kurssit");
       pastCoursesLabel.setPadding(padding);
       activeCoursesLabel.setPadding(padding);
       
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
           userService.logout();
           stage.setScene(loginScene);
       });

       removeCourseButton.setOnAction((ActionEvent e) -> {
          if (activeCoursesTable.getSelectionModel().getSelectedItem() == null) {
              if (pastCoursesTable.getSelectionModel().getSelectedItem() == null) {
              Alert alert = new Alert(AlertType.INFORMATION);
              alert.setTitle("Virhe");
              alert.setHeaderText("Kurssin poisto epäonnistui!");
              alert.setContentText("Mahdollisia syitä:\nKurssia ei ole valittu");
              alert.showAndWait();   
              }
              else {
                    helperCourse = (Course) pastCoursesTable.getSelectionModel().getSelectedItem();
              try {
                  if (courseService.deleteCourse(helperCourse)) {
                    pastCoursesTable.getItems().remove(pastCoursesTable.getSelectionModel().getSelectedItem());
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(StudyRegisterUi.class.getName()).log(Level.SEVERE, null, ex);
              }
              }
          }
          else {
              helperCourse = (Course) activeCoursesTable.getSelectionModel().getSelectedItem();
              try {
                  if (courseService.deleteCourse(helperCourse)) {
                    activeCoursesTable.getItems().remove(activeCoursesTable.getSelectionModel().getSelectedItem());
                  }
              } catch (SQLException ex) {
                  Logger.getLogger(StudyRegisterUi.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
       });
       
       addCourseButton.setOnAction((ActionEvent e) -> {
          stage.setScene(courseScene);  
       });
       
       Button markCourseUnactiveButton = new Button("epäaktivoi kurssi");
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
               helperCourse = (Course) activeCoursesTable.getSelectionModel().selectedItemProperty().get();
               try {
                   if (courseService.markCourseAsDone(helperCourse)) {
                       pastCoursesAsObservableList.add((Course)activeCoursesTable.getSelectionModel().getSelectedItem());
                       pastCoursesTable.setItems(pastCoursesAsObservableList);
                       activeCoursesTable.getItems().remove((Course)activeCoursesTable.getSelectionModel().getSelectedItem());
                   }
               } catch (SQLException ex) {
                   Logger.getLogger(StudyRegisterUi.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       });
       
       middleVBox.getChildren().addAll(markCourseUnactiveButton, removeCourseButton, addCourseButton, logoutButton);
       bottomHBox.getChildren().addAll(logged);
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