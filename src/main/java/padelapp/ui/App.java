package padelapp.ui;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import padelapp.utilisateurs.Moderateur;

public class App extends Application {
    private Calendrier calendrier;
    private Blog blog;

    public App(Moderateur moderateur) {
        this.calendrier = new Calendrier(LocalDate.now(),moderateur);
        this.blog = new Blog();
    }

    public static void main(String[] args) {
        Application.launch(Connexion.class,args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("4GlassWalls");
        primaryStage.getIcons().add(new Image("padelapp/ressources/logo.jpg"));
        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Reservations");
        toolBar.getItems().add(button1);
        toolBar.getItems().add(new Separator());
        Button button2 = new Button("Blog");
        toolBar.getItems().add(button2);
        
        VBox vBox = new VBox(toolBar, this.calendrier.getView());
        
        Scene scene = new Scene(vBox, 1280, 720);
        button1.setOnAction(event -> {
            // Créez et affichez la scène de réservation ici
            Scene reservationScene = new Scene(new VBox(toolBar, this.calendrier.getView()), 1280, 720);
            reservationScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            toolBar.getStyleClass().add("tool-bar");
            button1.getStyleClass().add("button");
            button2.getStyleClass().add("button");
            primaryStage.setScene(reservationScene);
        });

        button2.setOnAction(event -> {
            // Créez et affichez la scène de blog ici
            Scene blogScene = new Scene(new VBox(toolBar, this.blog.getView()), 1280, 720);
            blogScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            toolBar.getStyleClass().add("tool-bar");
            button1.getStyleClass().add("button");
            button2.getStyleClass().add("button");
            primaryStage.setScene(blogScene);
        });
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        toolBar.getStyleClass().add("tool-bar");
        button1.getStyleClass().add("button");
        button2.getStyleClass().add("button");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
}