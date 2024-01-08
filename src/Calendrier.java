import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Calendrier {
    public int currentMonth;
    public DayOfWeek currentDay;
    private HBox view;
    List<DayOfWeek> daysInMonth = new ArrayList<DayOfWeek>();
    ScrollPane scrollDays = new ScrollPane();
    private static Text[] mois = new Text[] { new Text("Janvier"), new Text("Fevrier"), new Text("Mars"),
                            new Text("Avril"), new Text("Mai"), new Text("Juin"), new Text("Juillet"), 
                            new Text("Aout") , new Text("Septembre"), new Text("Octobre"), new Text("Novembre"),
                            new Text("Decembre")};             

    public Calendrier(LocalDate date){
        //Affichage des mois
        GridPane monthBox = new GridPane();
        monthBox.setPrefSize(100, 600);
        monthBox.setGridLinesVisible(true);

        for (int i = 0; i < 12; i++){
            AnchorPaneNode ap = new AnchorPaneNode();
            int month = i + 1;
            ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //Surligner en blanc tous les mois
                    for (Node node : monthBox.getChildren()){
                        if (node instanceof AnchorPaneNode){
                            node.setStyle("-fx-background-color: white;");
                            monthBox.setGridLinesVisible(true);
                        }
                    }

                    //Mettre a jour l'affichage des jours du mois cliqué
                    updateDaysDisplay(date, month); 

                    //Surligner en Bleu le mois cliqué
                    ap.setStyle("-fx-background-color: blue;");
                    monthBox.setGridLinesVisible(true);
                }  
            });

            ap.setPrefSize(100, 50);
            AnchorPaneNode.setTopAnchor(mois[i], 10.0);
            AnchorPaneNode.setLeftAnchor(mois[i], 10.0);
            ap.getChildren().add(mois[i]);
            monthBox.add(ap,0, i);
        }

        //Affichage des jours 
        scrollDays.setPrefSize(110, 600);
        updateDaysDisplay(date, date.getMonthValue());
        

        //Affichage futur des reservations
        GridPane resaLayout = new GridPane();
        resaLayout.setPrefSize(600, 600);

        view = new HBox(monthBox, scrollDays, resaLayout);
    }

    private void updateDaysDisplay(LocalDate date, int month) {
        GridPane dayBox = new GridPane();
        scrollDays.setContent(dayBox);
        dayBox.setGridLinesVisible(true);
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
                ap.setPrefSize(100, 100);
                dayBox.add(ap,0,i);
                Text jourNom = new Text(daysInMonth.get(i).getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase());
                AnchorPaneNode.setTopAnchor(jourNom, 15.0);
                AnchorPaneNode.setLeftAnchor(jourNom, 15.0);
                ap.getChildren().add(jourNom);
                Text jourNumero = new Text(String.valueOf(jourActuel));
                jourActuel += 1;
                AnchorPaneNode.setBottomAnchor(jourNumero, 15.0);
                AnchorPaneNode.setLeftAnchor(jourNumero, 10.0);
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
                ap.setPrefSize(100, 100);
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

}
