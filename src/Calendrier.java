import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Calendrier {
    private ArrayList<AnchorPaneNode> allMonths = new ArrayList<>(12);
    private ArrayList<AnchorPaneNode> allDays = new ArrayList<>(8);
    public Month currentMonth;
    public DayOfWeek currentDay;
    private HBox view;
    private static Text[] mois = new Text[] { new Text("Janvier"), new Text("Fevrier"), new Text("Mars"),
                            new Text("Avril"), new Text("Mai"), new Text("Juin"), new Text("Juillet"), 
                            new Text("Aout") , new Text("Septembre"), new Text("Octobre"), new Text("Novembre"),
                            new Text("Decembre")};
    private static Text[] jours = new Text[] { new Text("Lundi"), new Text("Mardi"), new Text("Mercredi"), 
                            new Text("Jeudi"), new Text("Vendredi"), new Text("Samedi"), new Text("Dimanche")};
    //private Date currentDate;

    public Calendrier(LocalDate date){
        LocalDate dateActuelle = LocalDate.now();
        //Créer l'affichage des mois
        GridPane monthBox = new GridPane();
        monthBox.setPrefSize(100, 600);
        monthBox.setGridLinesVisible(true);
        for (int i = 0; i < 12; i++){
            AnchorPaneNode ap = new AnchorPaneNode();
            ap.setOnKeyPressed(new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    if (ap.getStyle() == "-fx-background-color: #FFFFFF"){
                        ap.setStyle("-fx-background-color: #5D5BB9");
                        //opé
                        
                    }
                    else {
                        ap.setStyle("-fx-background-color: #FFFFFF");
                    }
                    ap.setStyle("-fx-background-color: #5D5BB9");
                }
                
            });
            ap.setPrefSize(100, 50);
            ap.setTopAnchor(mois[i], 10.0);
            ap.setLeftAnchor(mois[i], 10.0);
            ap.getChildren().add(mois[i]);
            monthBox.add(ap,0, i);
            
            allMonths.add(ap);
        }

        //Créer l'affichage des jours 
        ScrollPane scrollDays = new ScrollPane();
        scrollDays.setPrefSize(110, 600);
        GridPane dayBox = new GridPane();
        scrollDays.setContent(dayBox);
        //dayBox.setPrefSize(100, 600);
        dayBox.setGridLinesVisible(true);
        
        for (int i =0; i < 7; i++){
            AnchorPaneNode ap = new AnchorPaneNode();
            ap.setPrefSize(100, 100);
            ap.setTopAnchor(jours[i], 10.0);
            ap.setLeftAnchor(jours[i], 10.0);
            ap.getChildren().add(jours[i]);
            dayBox.add(ap, 0, i);
            allDays.add(ap);
        }

        //Affichage futur des reservations
        GridPane resaLayout = new GridPane();
        resaLayout.setPrefSize(600, 600);

        view = new HBox(monthBox, scrollDays, resaLayout);
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
