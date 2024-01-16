package padelapp.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private static Text[] mois = new Text[] { new Text("Janvier"), new Text("Fevrier"), new Text("Mars"),
                            new Text("Avril"), new Text("Mai"), new Text("Juin"), new Text("Juillet"), 
                            new Text("Aout") , new Text("Septembre"), new Text("Octobre"), new Text("Novembre"),
                            new Text("Decembre")};             

    public Calendrier(LocalDate date,Moderateur moderateur){
        this.moderateur = moderateur;
        //Chargement des reservations
        this.reservations = loadReservationsFromJson("/padelapp/ressources/reservations.json");

        System.out.println(this.moderateur.getEmail());

        //Affichage des mois        
        for (int i = 0; i < 12; i++){
            AnchorPaneNode ap = new AnchorPaneNode();
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
            final AnchorPaneNode finalAp = ap;
            ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                    //Surligner en blanc tous les mois
                    for (Node node : monthBox.getChildren()){
                        if (node instanceof AnchorPaneNode){
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
            AnchorPaneNode.setTopAnchor(mois[i], 15.0);
            AnchorPaneNode.setLeftAnchor(mois[i], 18.0);
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
                AnchorPaneNode ap = new AnchorPaneNode();
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
                final AnchorPaneNode finalAp = ap;
                ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //Surligner en blanc tous les jours
                        for (Node node : dayBox.getChildren()){
                            if (node instanceof AnchorPaneNode){
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
                AnchorPaneNode.setTopAnchor(jourNom, 15.0);
                AnchorPaneNode.setLeftAnchor(jourNom, 20.0);
                ap.getChildren().add(jourNom);
                Text jourNumero = new Text(String.valueOf(jourActuel));
                jourActuel += 1;
                AnchorPaneNode.setBottomAnchor(jourNumero, 10.0);
                AnchorPaneNode.setLeftAnchor(jourNumero, 30.0);
                ap.getChildren().add(jourNumero);
            }
        }
        else {
            dayBox.getChildren().clear();
            daysInMonth.clear();
            daysInMonth = getDaysOfMonth(date.getYear(), month);
            int jourDebut = 1;
            for (int i = 0; i < daysInMonth.size(); i++){
                AnchorPaneNode ap = new AnchorPaneNode();
                //Etape necessaire pour que la variable soit accessible dans la fonction handle
                final int index = i;
                final AnchorPaneNode finalAp = ap;
                ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //Surligner en blanc tous les jours
                        for (Node node : dayBox.getChildren()){
                            if (node instanceof AnchorPaneNode){
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
                AnchorPaneNode.setTopAnchor(jourNom, 15.0);
                AnchorPaneNode.setLeftAnchor(jourNom, 15.0);
                ap.getChildren().add(jourNom);
                Text jourNumero = new Text(String.valueOf(jourDebut));
                jourDebut += 1;
                AnchorPaneNode.setBottomAnchor(jourNumero, 15.0);
                AnchorPaneNode.setLeftAnchor(jourNumero, 10.0);
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
        //System.out.println(resaLayout.isVisible());
        for (int i=0; i < Horaires.values().length; i++){
            AnchorPaneNode ap = new AnchorPaneNode();
            ap.getStyleClass().add("resa-pane");
            ap.setPrefSize(1000,120);
            resaLayout.add(ap,0,i);
            Text horaire = new Text(Horaires.values()[i].getDebut().toString() + " - " + Horaires.values()[i].getFin().toString());
            AnchorPaneNode.setTopAnchor(horaire, 10.0);
            AnchorPaneNode.setLeftAnchor(horaire, 10.0);
            ap.getChildren().add(horaire);
            //System.out.println(Horaires.values()[i].getDebut().toString() + " - " + Horaires.values()[i].getFin().toString());
            for (int j=0; j < reservations.size(); j++){
                //Verification de la date et de l'heure
                //System.out.println("1) expect : " + reservations.get(j).getHeureDebut() + " get : " + Horaires.values()[i].getDebut());
                //System.out.println("2) expect : " + reservations.get(j).getDate() + " get : " + LocalDate.of(2024, currentMonth + 1, day));
                if (reservations.get(j).getHeureDebut().equals(Horaires.values()[i].getDebut()) && reservations.get(j).getDate().equals(LocalDate.of(2024, currentMonth + 1, day))){
                    //affichage des joueurs
                    Text joueurs = new Text();
                    for (int k = 0; k < reservations.get(j).getJoueurs().size(); k++){
                        joueurs.setText(joueurs.getText() + reservations.get(j).getJoueurs().get(k).getNom().toUpperCase() + " " + reservations.get(j).getJoueurs().get(k).getPrenom() + "\n");      
                    }
                    AnchorPaneNode.setTopAnchor(joueurs, 10.0);
                    AnchorPaneNode.setLeftAnchor(joueurs, 300.0);
                    ap.getChildren().add(joueurs);
                    Text terrain = new Text("Terrain : " + String.valueOf(reservations.get(j).getTerrain().getNumero()));
                    AnchorPaneNode.setTopAnchor(terrain, 30.0);
                    AnchorPaneNode.setLeftAnchor(terrain, 10.0);
                    ap.getChildren().add(terrain);
                }
            }
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
