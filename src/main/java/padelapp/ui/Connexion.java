package padelapp.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import padelapp.utilisateurs.Moderateur;

public class Connexion extends Application {
    Moderateur moderateur;
    StackPane stackPane;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion");
        primaryStage.getIcons().add(new Image("padelapp/ressources/logo.jpg"));

        Label titleLabel = new Label("S'IDENTIFIER");

        Label emailLabel = new Label("Email:");

        TextField emailField = new TextField();

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Se connecter");
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            //Verifie les info de connexion
            if (checkLogin(email, password)) {
                try {
                    App mainApp = new App(this.moderateur);
                    mainApp.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                // Affiche un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de connexion");
                alert.setHeaderText(null);
                alert.setContentText("Email ou mot de passe incorrect.");
                emailField.clear();
                passwordField.clear();

                alert.showAndWait();
            }
        });

        
        VBox vbox = new VBox(10, titleLabel, emailLabel, emailField, passwordLabel, passwordField, loginButton);
        vbox.setPadding(new Insets(10));
        vbox.setMinSize(400, 400); 
        vbox.setMaxSize(400, 400); 

        AnchorPane anchorPane = new AnchorPane(vbox);
        
        AnchorPane.setTopAnchor(vbox, 160.0); 
        AnchorPane.setBottomAnchor(vbox, 160.0);
        AnchorPane.setLeftAnchor(vbox, 440.0);
        AnchorPane.setRightAnchor(vbox, 440.0);

        Scene scene = new Scene(anchorPane, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        anchorPane.getStyleClass().add("connexion-pane");
        emailLabel.getStyleClass().add("label-soft");
        passwordLabel.getStyleClass().add("label-soft");
        titleLabel.getStyleClass().add("label-bold");
        emailField.getStyleClass().add("box-connexion");
        passwordField.getStyleClass().add("box-connexion");
        loginButton.getStyleClass().add("login-button");
        //anchorPane.getStyleClass().add("background-connexion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Vérifie les informations de connexion
     * @param email
     * @param password
     * @return true si les informations sont correctes, false sinon
     */
    public boolean checkLogin(String email, String password) {
        List<Moderateur> moderateurs = loadModerateursFromJson("/padelapp/ressources/moderateurs.json");
        for (int i = 0; i < moderateurs.size(); i ++){
            if (moderateurs.get(i).getEmail().equals(email) && moderateurs.get(i).getMotDePasse().equals(password)){
                this.moderateur = moderateurs.get(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Charge les modérateurs depuis le fichier json
     * @param filename
     * @return Liste des modérateurs
     */
    public List<Moderateur> loadModerateursFromJson(String filename){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            InputStream is = getClass().getResourceAsStream(filename);
            return mapper.readValue(is, new TypeReference<List<Moderateur>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}