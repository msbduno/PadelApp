import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Calendrier {
    public Month currentMonth;
    public DayOfWeek currentDay;
    private HBox view;
    List<DayOfWeek> daysInMonth = new ArrayList<DayOfWeek>();
    int jourActuel;
    int moisActuel;
    int longueurMois;
    DayOfWeek jourPrecis;
    private static Text[] mois = new Text[] { new Text("Janvier"), new Text("Fevrier"), new Text("Mars"),
                            new Text("Avril"), new Text("Mai"), new Text("Juin"), new Text("Juillet"), 
                            new Text("Aout") , new Text("Septembre"), new Text("Octobre"), new Text("Novembre"),
                            new Text("Decembre")};             

    public Calendrier(LocalDate date){
        //Initialisation des variables
        this.jourActuel = date.getDayOfMonth();
        this.moisActuel = date.getMonthValue();
        this.longueurMois = date.getMonth().length(true);
        this.jourPrecis = date.getDayOfWeek();

        //Affichage des mois
        GridPane monthBox = new GridPane();
        monthBox.setPrefSize(100, 600);
        monthBox.setGridLinesVisible(true);
        for (int i = 0; i < 12; i++){
            AnchorPaneNode ap = new AnchorPaneNode();
            /*
            ap.setOnKeyPressed(new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    if (ap.getStyle() == "-fx-background-color: #FFFFFF"){
                        ap.setStyle("-fx-background-color: #5D5BB9");                        
                    }
                    else {
                        ap.setStyle("-fx-background-color: #FFFFFF");
                    }
                    ap.setStyle("-fx-background-color: #5D5BB9");
                }
                
            });
             */
            ap.setPrefSize(100, 50);
            ap.setTopAnchor(mois[i], 10.0);
            ap.setLeftAnchor(mois[i], 10.0);
            ap.getChildren().add(mois[i]);
            monthBox.add(ap,0, i);
        }

        //Affichage des jours 
        setDaysInMonth(date); //Initialise les jours correspondant au mois
        System.out.println(daysInMonth.get((daysInMonth.size()-1)).getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase());
        for (int i = 0; i < daysInMonth.size(); i++){
            System.out.println(daysInMonth.get(i).getDisplayName(TextStyle.FULL, Locale.FRENCH));
        } 
        ScrollPane scrollDays = new ScrollPane();
        scrollDays.setPrefSize(110, 600);
        GridPane dayBox = new GridPane();
        scrollDays.setContent(dayBox);
        dayBox.setGridLinesVisible(true);
        for (int i = 0; i < daysInMonth.size(); i++){
            AnchorPaneNode ap = new AnchorPaneNode();
            ap.setPrefSize(100, 100);
            dayBox.add(ap,0,i);
            Text jourNom = new Text(daysInMonth.get(i).getDisplayName(TextStyle.FULL, Locale.FRENCH));
            ap.setTopAnchor(jourNom, 10.0);
            ap.setLeftAnchor(jourNom, 10.0);
            ap.getChildren().add(jourNom);
            Text jourNumero = new Text(String.valueOf(jourActuel));
            jourActuel += 1;
            ap.setBottomAnchor(jourNumero, 15.0);
            ap.setLeftAnchor(jourNumero, 10.0);
            ap.getChildren().add(jourNumero);
        }

        //Affichage futur des reservations
        GridPane resaLayout = new GridPane();
        resaLayout.setPrefSize(600, 600);

        view = new HBox(monthBox, scrollDays, resaLayout);
    }

    public void setDaysInMonth(LocalDate date) {
        daysInMonth.add(jourPrecis);
        for (int i = 1; i < (longueurMois - jourActuel) + 1; i++){
            daysInMonth.add(daysInMonth.get(i-1).plus(1));
        }

    }

    public HBox getView() {
        return view;
    }

    public Month getCurrentMonth() {
        return currentMonth;
    }

    public DayOfWeek getCurrentDay() {
        return currentDay;
    }

    public void setCurrentMonth(Month currentMonth) {
        this.currentMonth = currentMonth;
    }

    public void setCurrentDay(DayOfWeek currentDay) {
        this.currentDay = currentDay;
    }

}
