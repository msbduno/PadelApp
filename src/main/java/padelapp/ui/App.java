package padelapp.ui;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import padelapp.utilisateurs.Moderateur;

public class App extends Application {
    private Calendrier calendrier;

    public App(Moderateur moderateur) {
        this.calendrier = new Calendrier(LocalDate.now(),moderateur);
    }

    public static void main(String[] args) {
        Application.launch(Connexion.class,args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("4GlassWalls");
        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Reservations");
        toolBar.getItems().add(button1);
        toolBar.getItems().add(new Separator());
        Button button2 = new Button("Blog");
        toolBar.getItems().add(button2);
        
        VBox vBox = new VBox(toolBar, this.calendrier.getView());
        
        Scene scene = new Scene(vBox, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        toolBar.getStyleClass().add("tool-bar");
        button1.getStyleClass().add("button");
        button2.getStyleClass().add("button");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
}