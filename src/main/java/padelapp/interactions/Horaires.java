package padelapp.interactions;

import java.time.LocalTime;

public enum Horaires {
    CRENEAU1(LocalTime.of(9, 0), LocalTime.of(10, 30)),
    CRENEAU2(LocalTime.of(10, 30), LocalTime.of(12, 00)),
    CRENEAU3(LocalTime.of(12, 00), LocalTime.of(13, 30)),
    CRENEAU4(LocalTime.of(13, 30), LocalTime.of(15, 00)),
    CRENEAU5(LocalTime.of(15, 00), LocalTime.of(16, 30)),
    CRENEAU6(LocalTime.of(16, 30), LocalTime.of(18, 00)),
    CRENEAU7(LocalTime.of(18, 00), LocalTime.of(19, 30)),
    CRENEAU8(LocalTime.of(19, 30), LocalTime.of(21, 00)),
    CRENEAU9(LocalTime.of(21, 00), LocalTime.of(22, 30));
    
    private final LocalTime debut;
    private final LocalTime fin;

    Horaires(LocalTime debut, LocalTime fin) {
        this.debut = debut;
        this.fin = fin;
    }

    public LocalTime getDebut() {
        return debut;
    }

    public LocalTime getFin() {
        return fin;
    }
}
