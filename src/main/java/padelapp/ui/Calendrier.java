package padelapp.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Calendrier {
    private int currentMonth;
    private DayOfWeek currentDay;
    private HBox view;
    private List<DayOfWeek> daysInMonth = new ArrayList<DayOfWeek>();
    private ScrollPane scrollDays = new ScrollPane();
    private GridPane monthBox = new GridPane();
    private GridPane resaLayout = new GridPane();
    private GridPane dayBox = new GridPane();
    private static Text[] mois = new Text[] { new Text("Janvier"), new Text("Fevrier"), new Text("Mars"),
                            new Text("Avril"), new Text("Mai"), new Text("Juin"), new Text("Juillet"), 
                            new Text("Aout") , new Text("Septembre"), new Text("Octobre"), new Text("Novembre"),
                            new Text("Decembre")};             

    public Calendrier(LocalDate date){
        //Affichage des mois
               
        for (int i = 0; i < 12; i++){
            AnchorPaneNode ap = new AnchorPaneNode();
            if (i == 0){
                ap.setStyle("-fx-background-color: blue;");
                updateDaysDisplay(date, date.getMonthValue());
                setCurrentMonth(0); 
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
                            node.setStyle("-fx-background-color: white;");
                        }
                    }
                    //Mettre a jour l'affichage des jours du mois cliqué
                    updateDaysDisplay(finalDate, finalMonth); 

                    //Surligner en Bleu le mois cliqué
                    finalAp.setStyle("-fx-background-color: blue;");
                    
                    //Definir le mois cliqué
                    setCurrentMonth(finalMonth - 1);
                    System.out.println("Mois : " + mois[getCurrentMonth()].getText().toUpperCase());
                }  
            });

            ap.setPrefSize(100, 50);
            AnchorPaneNode.setTopAnchor(mois[i], 15.0);
            AnchorPaneNode.setLeftAnchor(mois[i], 18.0);
            ap.getChildren().add(mois[i]);
            monthBox.add(ap,0, i);
        }

        //Affichage des jours 
        scrollDays.setPrefSize(110, 600);
        

        //Affichage futur des reservations
        
        resaLayout.setPrefSize(600, 600);

        view = new HBox(monthBox, scrollDays, resaLayout);
    }

    private void updateDaysDisplay(LocalDate date, int month) {
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
                //etape necessaire pour que la variable soit accessible dans la fonction handle
                final int index = i;
                final AnchorPaneNode finalAp = ap;
                ap.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //Surligner en blanc tous les jours
                        for (Node node : dayBox.getChildren()){
                            if (node instanceof AnchorPaneNode){
                                node.setStyle("-fx-background-color: white;");
                                dayBox.setGridLinesVisible(true);
                                scrollDays.setVvalue(0.0);
                            }
                        }

                        //Surligner en Bleu le mois cliqué
                        finalAp.setStyle("-fx-background-color: blue;");
                        
                        //Definir le jour cliqué
                        setCurrentDay(daysInMonth.get(index));
                        System.out.println("Jour : " + getCurrentDay().getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase());
                    }  
                });
                
                ap.setPrefSize(100, 100);
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
                                node.setStyle("-fx-background-color: white;");
                                scrollDays.setVvalue(scrollDays.getVmin()); //remet le scroll au debut (fonctionne pas)
                            }
                        }

                        //Surligner en Bleu le mois cliqué
                        finalAp.setStyle("-fx-background-color: blue;");
                        
                        //Definir le jour cliqué
                        setCurrentDay(daysInMonth.get(index));
                        System.out.println("Jour : " + getCurrentDay().getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase());
                    }  
                });
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
