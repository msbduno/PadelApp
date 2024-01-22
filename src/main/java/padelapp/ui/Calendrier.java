package padelapp.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import padelapp.interactions.Horaires;
import padelapp.interactions.Reservation;
import padelapp.interactions.Terrain;
import padelapp.utilisateurs.Joueur;
import padelapp.utilisateurs.Moderateur;

public class Calendrier {
    private Moderateur moderateur;
    private int currentMonth;
    private DayOfWeek currentDay;
    private HBox view;
    private List<DayOfWeek> daysInMonth = new ArrayList<DayOfWeek>();
    private ScrollPane scrollDays = new ScrollPane();
    private GridPane monthBox = new GridPane();
    private GridPane resaLayout = new GridPane();
    private GridPane dayBox = new GridPane();
    private ScrollPane scrollResa = new ScrollPane();
    private List<Reservation> reservations;
    private Connection connection;
    private List<Joueur> listJoueurs;
    private String url;
    private String username;
    private String password;
    private static Text[] mois = new Text[] { new Text("Janvier"), new Text("Fevrier"), new Text("Mars"),
            new Text("Avril"), new Text("Mai"), new Text("Juin"), new Text("Juillet "),
            new Text("Aout"), new Text("Septembre"), new Text("Octobre"), new Text("Novembre"),
            new Text("Decembre") };

    public Calendrier(LocalDate date, Moderateur moderateur) {
        this.moderateur = moderateur;
        this.url = "jdbc:mysql://192.168.56.81/PadelApp";
        //this.url = "jdbc:mysql://192.168.4.202:6666/PadelApp";
        this.username = "admin";
        this.password = "network";

        try {
            this.connection = DriverManager.getConnection(url, username, password);

            this.reservations = DatabaseThread.fetchReservationFromDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des mois
        for (int i = 0; i < 12; i++) {
            StackPane ap = new StackPane();
            // Lancement de l'application
            if (i == date.getMonthValue() - 1) {
                ap.getStyleClass().remove("month-pane");
                ap.getStyleClass().add("month-pane-highlighted");
                updateDaysDisplay(date, date.getMonthValue());
                setCurrentMonth(date.getMonthValue() - 1);
            } else {
                ap.getStyleClass().remove("month-pane-highlighted");
                ap.getStyleClass().add("month-pane");
            }

            // Etape necessaire pour que la variable soit accessible dans la fonction handle
            final LocalDate finalDate = date;
            final int finalMonth = i + 1;
            final StackPane finalAp = ap;
            ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    // Surligner en blanc tous les mois
                    for (Node node : monthBox.getChildren()) {
                        if (node instanceof StackPane) {
                            node.getStyleClass().remove("month-pane-highlighted");
                            node.getStyleClass().add("month-pane");
                        }
                    }
                    // Mettre a jour l'affichage des jours du mois cliqué
                    updateDaysDisplay(finalDate, finalMonth);

                    // Surligner en Bleu le mois cliqué
                    finalAp.getStyleClass().remove("month-pane");
                    finalAp.getStyleClass().add("month-pane-highlighted");

                    // Definir le mois cliqué
                    setCurrentMonth(finalMonth - 1);
                }
            });

            ap.setPrefSize(140, 80);
            ap.getChildren().add(mois[i]);

            monthBox.add(ap, 0, i);
        }

        // Affichage des jours
        scrollDays.setPrefSize(140, 720);
        scrollDays.setContent(dayBox);
        scrollDays.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollDays.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        dayBox.setGridLinesVisible(true);

        // Affichage des reservations
        scrollResa.setPrefSize(1000, 720);
        scrollResa.setContent(resaLayout);
        scrollResa.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollResa.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        resaLayout.setVgap(5);

        this.view = new HBox(monthBox, scrollDays, scrollResa);
    }

    /**
     * Affiche les jours du mois cliqué
     * 
     * @param date
     * @param month
     */
    private void updateDaysDisplay(LocalDate date, int month) {
        if (date.getMonth().getValue() == month) {
            dayBox.getChildren().clear();
            daysInMonth.clear();
            daysInMonth.add(date.getDayOfWeek());

            for (int i = 1; i < (date.getMonth().length(true) - date.getDayOfMonth()) + 1; i++) {
                daysInMonth.add(daysInMonth.get(i - 1).plus(1));
            }
            int jourActuel = date.getDayOfMonth();
            for (int i = 0; i < daysInMonth.size(); i++) {
                AnchorPane ap = new AnchorPane();
                // Lancement de l'application
                if (i == 0) {
                    ap.getStyleClass().remove("day-pane");
                    ap.getStyleClass().add("day-pane-highlighted");
                    afficherReservations(date.getDayOfMonth());
                } else {
                    ap.getStyleClass().remove("day-pane-highlighted");
                    ap.getStyleClass().add("day-pane");
                }
                // etape necessaire pour que la variable soit accessible dans la fonction handle
                final int index = i;
                final AnchorPane finalAp = ap;
                ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Surligner en blanc tous les jours
                        for (Node node : dayBox.getChildren()) {
                            if (node instanceof AnchorPane) {
                                node.getStyleClass().remove("day-pane-highlighted");
                                node.getStyleClass().add("day-pane");
                                dayBox.setGridLinesVisible(true);
                                scrollDays.setVvalue(0.0);
                            }
                        }

                        // Surligner en Bleu le mois cliqué
                        finalAp.getStyleClass().remove("day-pane");
                        finalAp.getStyleClass().add("day-pane-highlighted");

                        // Definir le jour cliqué
                        setCurrentDay(daysInMonth.get(index));

                        // Afficher les reservations du jour cliqué
                        afficherReservations(date.getDayOfMonth() + index);
                    }
                });

                ap.setPrefSize(240, 80);
                dayBox.add(ap, 0, i);
                Text jourNom = new Text(daysInMonth.get(i).getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase());
                AnchorPane.setTopAnchor(jourNom, 15.0);
                AnchorPane.setLeftAnchor(jourNom, 15.0);
                ap.getChildren().add(jourNom);
                Text jourNumero = new Text(String.valueOf(jourActuel));
                jourActuel += 1;
                AnchorPane.setBottomAnchor(jourNumero, 15.0);
                AnchorPane.setLeftAnchor(jourNumero, 10.0);
                ap.getChildren().add(jourNumero);
            }
        } else {
            dayBox.getChildren().clear();
            daysInMonth.clear();
            daysInMonth = getDaysOfMonth(date.getYear(), month);
            int jourDebut = 1;
            for (int i = 0; i < daysInMonth.size(); i++) {
                AnchorPane ap = new AnchorPane();
                // Afficher le bon style au changement de mois
                ap.getStyleClass().remove("day-pane-highlighted");
                ap.getStyleClass().add("day-pane");
                // Etape necessaire pour que la variable soit accessible dans la fonction handle
                final int index = i;
                final AnchorPane finalAp = ap;

                ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Surligner en blanc tous les jours
                        for (Node node : dayBox.getChildren()) {
                            if (node instanceof AnchorPane) {
                                node.getStyleClass().remove("day-pane-highlighted");
                                node.getStyleClass().add("day-pane");
                                scrollDays.setVvalue(scrollDays.getVmin()); // remet le scroll au debut (fonctionne pas)
                            }
                        }

                        // Surligner en Bleu le mois cliqué
                        finalAp.getStyleClass().remove("day-pane");
                        finalAp.getStyleClass().add("day-pane-highlighted");

                        // Definir le jour cliqué
                        setCurrentDay(daysInMonth.get(index));

                        // Afficher les reservations du jour cliqué
                        afficherReservations(index + 1);
                    }
                });
                ap.setPrefSize(240, 80);
                dayBox.add(ap, 0, i);
                Text jourNom = new Text(daysInMonth.get(i).getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase());
                AnchorPane.setTopAnchor(jourNom, 15.0);
                AnchorPane.setLeftAnchor(jourNom, 15.0);
                ap.getChildren().add(jourNom);
                Text jourNumero = new Text(String.valueOf(jourDebut));
                jourDebut += 1;
                AnchorPane.setBottomAnchor(jourNumero, 15.0);
                AnchorPane.setLeftAnchor(jourNumero, 10.0);
                ap.getChildren().add(jourNumero);
            }
        }

    }

    /**
     * Retourne les jours du mois voulu
     * 
     * @param year
     * @param month
     * @return list des jours du mois
     */
    public List<DayOfWeek> getDaysOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        LocalDate lastOfMonth = yearMonth.atEndOfMonth();

        List<DayOfWeek> days = new ArrayList<>();
        for (LocalDate date = firstOfMonth; !date.isAfter(lastOfMonth); date = date.plusDays(1)) {
            days.add(date.getDayOfWeek());
        }

        return days;
    }

    /**
     * Affiche toutes les reservations de la base de donnée et gère les évenements
     * liés aux boutons
     * 
     * @param day
     */
    private void afficherReservations(int day) {
        try {

            this.reservations = DatabaseThread.fetchReservationFromDatabase(this.connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resaLayout.getChildren().clear();
        int m = 0;
        for (int i = 0; i < Horaires.values().length; i++) { // Chaque horaire
            for (int l = 0; l < 4; l++) { // Pour chaque terrain par heure
                AnchorPane ap = new AnchorPane();
                // Afficher une reservation
                ap.getStyleClass().add("resa-pane");
                ap.setPrefSize(1000, 120);
                resaLayout.add(ap, 0, m);
                m++;
                // Afficher l'horaire
                AnchorPane ap2 = new AnchorPane();
                Text horaire = new Text(
                        Horaires.values()[i].getDebut().toString() + " - " + Horaires.values()[i].getFin().toString());
                LocalTime heureA = Horaires.values()[i].getDebut();
                String horaireString = Horaires.values()[i].getDebut().toString() + " - "
                        + Horaires.values()[i].getFin().toString();
                ap2.getStyleClass().add("horaire-pane-non-reserve");
                ap2.setTopAnchor(horaire, 0.0);
                ap2.setLeftAnchor(horaire, 10.0);
                ap2.setRightAnchor(horaire, 10.0);
                ap.setTopAnchor(ap2, 20.0);
                ap.setLeftAnchor(ap2, 20.0);
                ap.getChildren().add(ap2);
                ap2.getChildren().add(horaire);

                // Afficher le terrain
                StackPane ap4 = new StackPane();
                Text terrain = new Text(" Terrain " + String.valueOf(l + 1) + " ");
                int numTerrain = l + 1;
                ap4.getStyleClass().add("terrain-pane");
                ap.setTopAnchor(ap4, 70.0);
                ap.setLeftAnchor(ap4, 40.0);
                ap.getChildren().add(ap4);
                ap4.getChildren().add(terrain);

                // Afficher le bouton supprimer
                Button supprBtn = new Button("SUPPRIMER");
                supprBtn.getStyleClass().add("boutons-normal");
                supprBtn.setVisible(false);
                AnchorPane.setTopAnchor(supprBtn, 40.0);
                AnchorPane.setLeftAnchor(supprBtn, 800.0);
                ap.getChildren().add(supprBtn);

                // Afficher le bouton modifier
                Button modifBtn = new Button("AJOUTER");
                modifBtn.getStyleClass().add("boutons-normal");
                modifBtn.setOnAction(e -> {
                    modifBtn.getStyleClass().remove("boutons-normal");
                    modifBtn.getStyleClass().add("boutons-clique");

                    Stage stage = new Stage();
                    stage.setTitle("Ajouter une réservation");
                    stage.getIcons().add(new Image("padelapp/ressources/logo.jpg"));
                    VBox vbox = new VBox();
                    vbox.setPadding(new Insets(10));
                    vbox.setSpacing(10.0);

                    // Ajoutez des champs pour entrer les détails de la réservation
                    Label dateField = new Label("Date : " + LocalDate.of(2024, currentMonth + 1, day));
                    dateField.getStyleClass().add("text-form");
                    Label timeField = new Label("Heure : " + horaireString);
                    timeField.getStyleClass().add("text-form");
                    Label terrainField = new Label("Terrain : " + numTerrain);
                    terrainField.getStyleClass().add("text-form");
                    ComboBox<String> userComboBox = new ComboBox<>();
                    userComboBox.setPromptText("Utilisateur");
                    userComboBox.getStyleClass().add("box-form");

                    CheckBox payeCheckBox = new CheckBox("Payé");
                    payeCheckBox.getStyleClass().add("text-form");
                    CheckBox publiqueCheckBox = new CheckBox("Publique");
                    publiqueCheckBox.getStyleClass().add("text-form");

                    // Remplir la liste déroulante avec les noms des utilisateurs
                    try {
                        this.listJoueurs = fetchJoueursFromDatabase(connection);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    for (Joueur j : this.listJoueurs) {
                        userComboBox.getItems().add(j.stringNomPrenom());
                    }

                    Button creerUtilisateurButton = new Button("Créer un utilisateur");
                    creerUtilisateurButton.getStyleClass().add("submit-button");
                    creerUtilisateurButton.setOnAction(e2 -> {
                        Stage stage2 = new Stage();
                        stage2.setTitle("Créer un nouvel utilisateur");
                        stage2.getIcons().add(new Image("padelapp/ressources/logo.jpg"));
                        VBox vbox2 = new VBox();
                        vbox2.setPadding(new Insets(10));
                        vbox2.setSpacing(8.0);

                        // Ajouter des champs pour entrer les détails de l'utilisateur
                        Label emailT = new Label("Email");
                        emailT.getStyleClass().add("text-form");
                        TextField emailField = new TextField();
                        emailField.setPromptText("Email");
                        emailField.getStyleClass().add("box-form");
                        Label passwordT = new Label("Mot de passe");
                        passwordT.getStyleClass().add("text-form");
                        TextField passwordField = new TextField();
                        passwordField.setPromptText("Mot de passe");
                        passwordField.getStyleClass().add("box-form");
                        Label nameT = new Label("Nom");
                        nameT.getStyleClass().add("text-form");
                        TextField nameField = new TextField();
                        nameField.setPromptText("Nom");
                        nameField.getStyleClass().add("box-form");
                        Label prenomT = new Label("Prenom");
                        prenomT.getStyleClass().add("text-form");
                        TextField prenomField = new TextField();
                        prenomField.setPromptText("Prenom");
                        prenomField.getStyleClass().add("box-form");
                        Label niveauT = new Label("Niveau");
                        niveauT.getStyleClass().add("text-form");

                        ComboBox<String> niveauComboBox = new ComboBox<>();
                        for (int p = 0; p < 11; p++) {
                            niveauComboBox.getItems().add(String.valueOf(p));
                        }
                        niveauComboBox.getStyleClass().add("box-form");

                        Button submitButton = new Button("Enregistrer nouvel utilisateur");
                        submitButton.getStyleClass().add("submit-button");
                        submitButton.setOnAction(e3 -> {
                            // Valider les entrées et créez un nouvel utilisateur
                            String email = emailField.getText();
                            String password = passwordField.getText();
                            String nom = nameField.getText();
                            String prenom = prenomField.getText();
                            int level = Integer.parseInt(niveauComboBox.getSelectionModel().getSelectedItem());

                            Joueur joueur = new Joueur(email, password, nom, prenom, listJoueurs.size() + 1, level);
                            this.listJoueurs.add(joueur);

                            // Ajouter l'utilisateur à la base de données
                            addUserToDatabase(joueur);

                            // Mettre à jour la liste déroulante des utilisateurs
                            userComboBox.getItems().add(nom.toUpperCase() + " " + prenom);

                            stage2.close();
                        });

                        vbox2.getChildren().addAll(emailT, emailField, passwordT, passwordField, nameT,
                                nameField, prenomT, prenomField, niveauT, niveauComboBox, submitButton);
                        Scene scene2 = new Scene(vbox2, 400, 500);
                        scene2.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                        stage2.setScene(scene2);
                        stage2.show();
                    });

                    HBox hboxUtilisateurs = new HBox();
                    hboxUtilisateurs.getChildren().addAll(userComboBox, creerUtilisateurButton);
                    hboxUtilisateurs.setSpacing(10.0);

                    Button submitButton = new Button("Enregistrer");
                    submitButton.getStyleClass().add("submit-button");
                    submitButton.setOnAction(e4 -> {
                        // Valider les entrées et créer une nouvelle réservation
                        if (!userComboBox.getSelectionModel().isEmpty()) {
                            int idRes = reservations.size() + 1;
                            List<Joueur> joueurs = new ArrayList<>();
                            joueurs.add(listJoueurs.get(userComboBox.getSelectionModel().getSelectedIndex()));
                            boolean estPayeR = payeCheckBox.isSelected();
                            LocalDate dateR = LocalDate.of(2024, currentMonth + 1, day);
                            LocalTime heureDebutR = heureA;
                            Terrain terrainR = new Terrain(numTerrain, numTerrain);

                            Reservation reservation = new Reservation(idRes, joueurs, estPayeR, estPayeR, heureDebutR,
                                    dateR, terrainR);
                            // Ajouter la réservation à la base de données
                            try {
                                addReservationToDatabase(reservation);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                            // Mettre à jour l'affichage du calendrier
                            afficherReservations(day);

                            modifBtn.getStyleClass().remove("boutons-clique");
                            modifBtn.getStyleClass().add("boutons-normal");

                            stage.close();
                        } else {
                            //Si aucun utilisateur est selectionné
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur de création");
                            alert.setContentText("Vous devez sélectionner un utilisateur.");

                            alert.showAndWait(); 
                        }

                    });

                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            modifBtn.getStyleClass().remove("boutons-clique");
                            modifBtn.getStyleClass().add("boutons-normal");
                        }
                    });

                    vbox.getChildren().addAll(dateField, timeField, terrainField, hboxUtilisateurs, payeCheckBox,
                            publiqueCheckBox, submitButton);
                    Scene scene = new Scene(vbox, 600, 600);
                    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                });
                AnchorPane.setTopAnchor(modifBtn, 40.0);
                AnchorPane.setLeftAnchor(modifBtn, 800.0);
                ap.getChildren().add(modifBtn);

                for (int j = 0; j < reservations.size(); j++) {
                    if (reservations.get(j).getHeureDebut().equals(Horaires.values()[i].getDebut())
                            && reservations.get(j).getDate().equals(LocalDate.of(2024, currentMonth + 1, day))
                            && reservations.get(j).getTerrain().getNumero() == l + 1) {
                        // Pour que les variables soient tjrs accessibles
                        final boolean finalEstPaye = reservations.get(j).getEstPaye();
                        final int indexRes = j;

                        // Affichage des joueurs
                        AnchorPane ap3 = new AnchorPane(); // Pane pour les joueurs
                        Text joueurs = new Text();
                        for (int k = 0; k < reservations.get(j).getJoueurs().size(); k++) {
                            joueurs.setText(
                                    joueurs.getText() + reservations.get(j).getJoueurs().get(k).getNom().toUpperCase()
                                            + " " + reservations.get(j).getJoueurs().get(k).getPrenom() + "\n");
                        }
                        ap3.getStyleClass().add("joueurs-pane");
                        ap3.setTopAnchor(joueurs, 10.0);
                        ap3.setLeftAnchor(joueurs, 10.0);
                        ap3.setRightAnchor(joueurs, 10.0);
                        ap.setTopAnchor(ap3, 10.0);
                        ap.setLeftAnchor(ap3, 250.0);
                        ap.getChildren().add(ap3);
                        ap3.getChildren().add(joueurs);

                        // Changement de l'horaire en reservé
                        ap2.getStyleClass().remove("horaire-pane-non-reserve");
                        ap2.getStyleClass().add("horaire-pane-reserve");

                        // Affichage du statut (payé ou non)
                        Button payeBtn = new Button(); // Bouton pour le statut
                        if (reservations.get(j).getEstPaye() == true) {
                            payeBtn.setText("Statut : Payé");
                            payeBtn.getStyleClass().remove("non-paye-button");
                            payeBtn.getStyleClass().add("paye-button");
                        } else {
                            payeBtn.setText("Statut : Non Payé");
                            payeBtn.getStyleClass().remove("paye-button");
                            payeBtn.getStyleClass().add("non-paye-button");
                        }

                        payeBtn.setOnAction(event -> {
                            updatePaye(payeBtn, finalEstPaye, indexRes + 1);
                        });
                        AnchorPane.setTopAnchor(payeBtn, 30.0);
                        AnchorPane.setLeftAnchor(payeBtn, 500.0);
                        ap.getChildren().add(payeBtn);

                        // Event pour le bouton supprimer
                        supprBtn.setVisible(true);
                        supprBtn.setOnAction(event -> {
                            deleteReservation(supprBtn, reservations.get(indexRes).getIdReservation(), day);
                        });

                        modifBtn.setVisible(false);
                    }
                }
            }

        }
    }

    /**
     * Retourne une liste des utilisateurs joueurs de la base de donnée
     * 
     * @param connection
     * @return liste des joueurs
     * @throws SQLException
     */
    private List<Joueur> fetchJoueursFromDatabase(Connection connection) throws SQLException {
        List<Joueur> joueurs = new ArrayList<>();

        String query = "SELECT * FROM utilisateur ORDER BY idUtilisateur ASC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String query2 = "SELECT * FROM utilisateur WHERE idUtilisateur = "
                        + String.valueOf(resultSet.getInt("idUtilisateur"));
                try (PreparedStatement stmt2 = connection.prepareStatement(query2)) {
                    ResultSet resultSet2 = stmt2.executeQuery();
                    if (resultSet2.next()) {
                        Joueur joueur = new Joueur();
                        joueur.setEmail(resultSet2.getString("email"));
                        joueur.setMotDePasse(resultSet2.getString("motDePasse"));
                        joueur.setNom(resultSet2.getString("nom"));
                        joueur.setPrenom(resultSet2.getString("prenom"));
                        joueur.setId(resultSet2.getInt("idUtilisateur"));
                        joueur.setNiveau(resultSet2.getInt("niveau"));

                        joueurs.add(joueur);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueurs;
    }

    /**
     * Lance une popup pour valider la suppression d'une reservation
     * 
     * @param bouton
     * @param idReservation
     * @param day
     */
    public void deleteReservation(Button bouton, int idReservation, int day) {
        bouton.getStyleClass().remove("boutons-normal");
        bouton.getStyleClass().add("boutons-clique");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression de la réservation " + idReservation);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette réservation ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteReservationInDB(idReservation, day);
            System.out.println("Reservation " + idReservation + " supprimée");
            bouton.getStyleClass().remove("boutons-clique");
            bouton.getStyleClass().add("boutons-normal");
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            System.out.println("Reservation " + idReservation + " gardée");
            bouton.getStyleClass().remove("boutons-clique");
            bouton.getStyleClass().add("boutons-normal");
        }
    }

    /**
     * Met à jour le statut de paiement d'une reservation dans la base de donnée
     * 
     * @param bouton
     * @param estPaye
     * @param idReservation
     */
    public void updatePaye(Button bouton, boolean estPaye, int idReservation) {
        if (bouton.getText().equals("Statut : Payé")) {
            bouton.setText("Statut : Non Payé");
            bouton.getStyleClass().remove("paye-button");
            bouton.getStyleClass().add("non-paye-button");
            System.out.println("Reservation " + idReservation + " non payée");
            try {
                Connection connexion = DriverManager.getConnection(this.url, this.username, this.password);
                String requete = "UPDATE reservation SET estPaye = 0 WHERE idReservation = ?";
                try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                    preparedStatement.setInt(1, idReservation);
                    preparedStatement.executeUpdate();
                }
                connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            bouton.setText("Statut : Payé");
            bouton.getStyleClass().remove("non-paye-button");
            bouton.getStyleClass().add("paye-button");
            System.out.println("Reservation " + idReservation + " payée");
            try {
                Connection connexion = DriverManager.getConnection(this.url, this.username, this.password);
                String requete = "UPDATE reservation SET estPaye = 1 WHERE idReservation = ?";
                try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                    preparedStatement.setInt(1, idReservation);
                    preparedStatement.executeUpdate();
                }
                connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ajoute un utilisateur à la base de donnée
     * 
     * @param joueur
     */
    public void addUserToDatabase(Joueur joueur) {
        String query = "INSERT INTO utilisateur (idUtilisateur, email, motDePasse, nom, prenom, niveau, estModerateur) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, joueur.getId());
            stmt.setString(2, joueur.getEmail());
            stmt.setString(3, joueur.getMotDePasse());
            stmt.setString(4, joueur.getNom());
            stmt.setString(5, joueur.getPrenom());
            stmt.setInt(6, joueur.getNiveau());
            stmt.setBoolean(7, false);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute une reservation à la base de donnée
     * 
     * @param reservation
     * @throws SQLException
     */
    public void addReservationToDatabase(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservation (idReservation, idUtilisateur, idTerrain, heureDebut, estPublique, dateRes, nombreDeJoueurs, estPaye) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, reservation.getIdReservation());
            stmt.setInt(2, reservation.getJoueurs().get(0).getId());
            stmt.setInt(3, reservation.getTerrain().getIdTerrain());
            stmt.setTime(4, Time.valueOf(reservation.getHeureDebut()));
            ;
            stmt.setBoolean(5, reservation.getPublique());
            stmt.setDate(6, Date.valueOf(reservation.getDate()));
            ;
            stmt.setInt(7, 4);
            stmt.setBoolean(8, reservation.getEstPaye());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String query2 = "INSERT INTO joueurs (idReservation, idUtilisateur) VALUES (?, ?)";
        try (PreparedStatement stmt2 = connection.prepareStatement(query2)) {
            for (Joueur joueur : reservation.getJoueurs()) {
                stmt2.setInt(1, reservation.getIdReservation());
                stmt2.setInt(2, joueur.getId());

                stmt2.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Supprime une reservation de la base de donnée
     * 
     * @param idResa
     * @param day
     */
    public void deleteReservationInDB(int idResa, int day) {
        try {
            // Étape 1 : Établir une connexion à la base de données (remplacez les détails
            // de connexion)
            Connection connexion = DriverManager.getConnection(this.url, this.username, this.password);

            // Étape 2 : Construire la requête de suppression
            String requete = "DELETE FROM joueurs WHERE idReservation = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                // Étape 3 : Paramétrer la valeur de la condition
                preparedStatement.setInt(1, idResa);

                // Étape 4 : Exécuter la requête de suppression
                preparedStatement.executeUpdate();
            }

            String requete2 = "DELETE FROM reservation WHERE idReservation = ?";
            try (PreparedStatement preparedStatement2 = connexion.prepareStatement(requete2)) {
                // Étape 3 : Paramétrer la valeur de la condition
                preparedStatement2.setInt(1, idResa);

                // Étape 4 : Exécuter la requête de suppression
                preparedStatement2.executeUpdate();
            }
            // Étape 5 : Fermer la connexion
            connexion.close();

            afficherReservations(day);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HBox getView() {
        return view;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public DayOfWeek getCurrentDay() {
        return currentDay;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;

    }

    public void setCurrentDay(DayOfWeek currentDay) {
        this.currentDay = currentDay;
    }

    public void setModerateur(Moderateur moderateur) {
        this.moderateur = moderateur;
    }

    public Moderateur getModerateur() {
        return moderateur;
    }

}