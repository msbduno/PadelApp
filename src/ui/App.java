package ui;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(args);
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
        
        VBox vBox = new VBox(toolBar, new Calendrier(LocalDate.now()).getView());
        
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
    }

    
    
}