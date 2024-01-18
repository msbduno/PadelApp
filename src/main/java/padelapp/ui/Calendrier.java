package padelapp.ui;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import padelapp.interactions.Horaires;
import padelapp.interactions.Reservation;
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
    private DatabaseThread dbThread = new DatabaseThread();
    private static Text[] mois = new Text[] { new Text("Janvier"), new Text("Fevrier"), new Text("Mars"),
                            new Text("Avril"), new Text("Mai"), new Text("Juin"), new Text("Juillet"), 
                            new Text("Aout") , new Text("Septembre"), new Text("Octobre"), new Text("Novembre"),
                            new Text("Decembre")};             

    public Calendrier(LocalDate date, Moderateur moderateur){
        this.moderateur = moderateur;
        
        this.dbThread.run();
        
        this.reservations = loadReservationsFromJson("/padelapp/ressources/reservations.json");

        //System.out.println(this.moderateur.getEmail());

        //Affichage des mois        
        for (int i = 0; i < 12; i++){
            StackPane ap = new StackPane();
            //Lancement de l'application 
            if (i == date.getMonthValue() - 1){ 
                ap.getStyleClass().remove("month-pane");
                ap.getStyleClass().add("month-pane-highlighted");
                updateDaysDisplay(date, date.getMonthValue());
                setCurrentMonth(date.getMonthValue() - 1); 
            }
            else {
                ap.getStyleClass().remove("month-pane-highlighted");
                ap.getStyleClass().add("month-pane");
            }
            
            //Etape necessaire pour que la variable soit accessible dans la fonction handle
            final LocalDate finalDate = date;
            final int finalMonth = i+ 1;
            final StackPane finalAp = ap;
            ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                    //Surligner en blanc tous les mois
                    for (Node node : monthBox.getChildren()){
                        if (node instanceof StackPane){
                            node.getStyleClass().remove("month-pane-highlighted");
                            node.getStyleClass().add("month-pane");
                        }
                    }
                    //Mettre a jour l'affichage des jours du mois cliqué
                    updateDaysDisplay(finalDate, finalMonth); 

                    //Surligner en Bleu le mois cliqué
                    finalAp.getStyleClass().remove("month-pane");
                    finalAp.getStyleClass().add("month-pane-highlighted");
                    
                    //Definir le mois cliqué
                    setCurrentMonth(finalMonth - 1);
                    //System.out.println("Mois : " + mois[getCurrentMonth()].getText().toUpperCase());
                }  
            });

            ap.setPrefSize(140, 80);
            /*
            ap.setTopAnchor(mois[i], 0.0);
            ap.setLeftAnchor(mois[i], 0.0);
            ap.setBottomAnchor(mois[i], 0.0);
            ap.setRightAnchor(mois[i], 0.0);
            */
            ap.getChildren().add(mois[i]);
             
            monthBox.add(ap,0, i);
        }

        //Affichage des jours 
        scrollDays.setPrefSize(140, 720);
        scrollDays.setContent(dayBox);
        scrollDays.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollDays.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        dayBox.setGridLinesVisible(true);
        
        //Affichage des reservations
        scrollResa.setPrefSize(1000, 720);
        scrollResa.setContent(resaLayout);
        scrollResa.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollResa.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        resaLayout.setVgap(5);

        view = new HBox(monthBox, scrollDays, scrollResa);
    }

    private void updateDaysDisplay(LocalDate date, int month) {
        if (date.getMonth().getValue() == month){
            dayBox.getChildren().clear();
            daysInMonth.clear();
            daysInMonth.add(date.getDayOfWeek());

            for (int i = 1; i < (date.getMonth().length(true) - date.getDayOfMonth()) + 1; i++){
                daysInMonth.add(daysInMonth.get(i-1).plus(1));
            }
            int jourActuel = date.getDayOfMonth();
            for (int i = 0; i < daysInMonth.size(); i++){
                AnchorPane ap = new AnchorPane();
                //Lancement de l'application
                if (i == 0){
                    ap.getStyleClass().remove("day-pane");
                    ap.getStyleClass().add("day-pane-highlighted");
                    afficherReservations(date.getDayOfMonth());
                }
                else {
                    ap.getStyleClass().remove("day-pane-highlighted");
                    ap.getStyleClass().add("day-pane");
                }
                //etape necessaire pour que la variable soit accessible dans la fonction handle
                final int index = i;
                final AnchorPane finalAp = ap;
                ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //Surligner en blanc tous les jours
                        for (Node node : dayBox.getChildren()){
                            if (node instanceof AnchorPane){
                                node.getStyleClass().remove("day-pane-highlighted");
                                node.getStyleClass().add("day-pane");
                                dayBox.setGridLinesVisible(true);
                                scrollDays.setVvalue(0.0);
                            }
                        }

                        //Surligner en Bleu le mois cliqué
                        finalAp.getStyleClass().remove("day-pane");
                        finalAp.getStyleClass().add("day-pane-highlighted");
                        
                        //Definir le jour cliqué
                        setCurrentDay(daysInMonth.get(index));
                        //System.out.println("Jour : " + getCurrentDay().getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase());
                        
                        //afficher les reservations du jour cliqué
                        afficherReservations(date.getDayOfMonth() + index );
                    }  
                });
                
                ap.setPrefSize(240, 80);
                dayBox.add(ap,0,i);
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
        }
        else {
            dayBox.getChildren().clear();
            daysInMonth.clear();
            daysInMonth = getDaysOfMonth(date.getYear(), month);
            int jourDebut = 1;
            for (int i = 0; i < daysInMonth.size(); i++){
                AnchorPane ap = new AnchorPane();
                //Afficher le bon style au changement de mois
                ap.getStyleClass().remove("day-pane-highlighted");
                ap.getStyleClass().add("day-pane");
                //Etape necessaire pour que la variable soit accessible dans la fonction handle
                final int index = i;
                final AnchorPane finalAp = ap;
                
                ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //Surligner en blanc tous les jours
                        for (Node node : dayBox.getChildren()){
                            if (node instanceof AnchorPane){
                                node.getStyleClass().remove("day-pane-highlighted");
                                node.getStyleClass().add("day-pane");
                                scrollDays.setVvalue(scrollDays.getVmin()); //remet le scroll au debut (fonctionne pas)
                            }
                        }

                        //Surligner en Bleu le mois cliqué
                        finalAp.getStyleClass().remove("day-pane");
                        finalAp.getStyleClass().add("day-pane-highlighted");
                        
                        //Definir le jour cliqué
                        setCurrentDay(daysInMonth.get(index));
                        //System.out.println("Jour : " + getCurrentDay().getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase());
                        
                        //afficher les reservations du jour cliqué
                        afficherReservations(index + 1);
                    }  
                });
                ap.setPrefSize(240, 80);
                dayBox.add(ap,0,i);
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

    public List<Reservation> loadReservationsFromJson(String filename){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            InputStream is = getClass().getResourceAsStream(filename);
            return mapper.readValue(is, new TypeReference<List<Reservation>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void afficherReservations(int day){
        resaLayout.getChildren().clear();
        int m = 0;
        for (int i=0; i < Horaires.values().length; i++){ //Chaque horaire
            for (int l = 0; l < 4; l++){ //Pour chaque terrain par heure 
                AnchorPane ap = new AnchorPane();
                //Afficher une reservation
                ap.getStyleClass().add("resa-pane");
                ap.setPrefSize(1000,120);
                resaLayout.add(ap,0,m);
                m++;
                //Afficher l'horaire
                AnchorPane ap2 = new AnchorPane();
                Text horaire = new Text(Horaires.values()[i].getDebut().toString() + " - " + Horaires.values()[i].getFin().toString());
                ap2.getStyleClass().add("horaire-pane-non-reserve");
                ap2.setTopAnchor(horaire, 0.0);
                ap2.setLeftAnchor(horaire, 10.0);
                ap2.setRightAnchor(horaire, 10.0);
                ap.setTopAnchor(ap2, 20.0);
                ap.setLeftAnchor(ap2, 20.0);
                ap.getChildren().add(ap2);
                ap2.getChildren().add(horaire);

                //Afficher le terrain
                StackPane ap4 = new StackPane(); 
                Text terrain = new Text(" Terrain " + String.valueOf(l+1) + " ");
                ap4.getStyleClass().add("terrain-pane");
                ap.setTopAnchor(ap4, 70.0);
                ap.setLeftAnchor(ap4, 40.0);
                ap.getChildren().add(ap4);
                ap4.getChildren().add(terrain);

                //Afficher le bouton supprimer
                Button supprBtn = new Button("SUPPRIMER");
                supprBtn.getStyleClass().add("boutons-pane");
                AnchorPane.setTopAnchor(supprBtn, 20.0);
                AnchorPane.setLeftAnchor(supprBtn, 800.0);
                ap.getChildren().add(supprBtn);

                //Afficher le bouton modifier
                Button modifBtn = new Button("MODIFIER");
                modifBtn.getStyleClass().add("boutons-pane");
                AnchorPane.setTopAnchor(modifBtn, 70.0);
                AnchorPane.setLeftAnchor(modifBtn, 800.0);
                ap.getChildren().add(modifBtn);
                
                for (int j=0; j < reservations.size(); j++){
                    if (reservations.get(j).getHeureDebut().equals(Horaires.values()[i].getDebut()) 
                    && reservations.get(j).getDate().equals(LocalDate.of(2024, currentMonth + 1, day)) 
                    && reservations.get(j).getTerrain().getNumero() == l+1){
                        //affichage des joueurs
                        AnchorPane ap3 = new AnchorPane(); //Pane pour les joueurs
                        Text joueurs = new Text();
                        for (int k = 0; k < reservations.get(j).getJoueurs().size(); k++){
                            joueurs.setText(joueurs.getText() + reservations.get(j).getJoueurs().get(k).getNom().toUpperCase() + " " + reservations.get(j).getJoueurs().get(k).getPrenom() + "\n");      
                        }
                        ap3.getStyleClass().add("joueurs-pane");
                        ap3.setTopAnchor(joueurs, 10.0);
                        ap3.setLeftAnchor(joueurs, 10.0);
                        ap3.setRightAnchor(joueurs, 10.0);
                        ap.setTopAnchor(ap3, 10.0);
                        ap.setLeftAnchor(ap3, 400.0);
                        ap.getChildren().add(ap3);
                        ap3.getChildren().add(joueurs);

                        //Changement de l'horaire en reservé
                        ap2.getStyleClass().remove("horaire-pane-non-reserve");
                        ap2.getStyleClass().add("horaire-pane-reserve");
                    }
                }
            }
            
        }
    }

    public void updateReservationsBDD() throws SQLException{
        //Chargement des reservations
        String url = "jdbc:mysql://192.168.56.81/PadelApp";
        String username = "admin";
        String password = "network";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connexion a la BDD impossible");
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