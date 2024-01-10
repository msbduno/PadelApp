package padelapp.interactions;

import java.time.LocalDate;
import java.time.LocalTime;

import padelapp.utilisateurs.Joueur;

public class Reservation {
    private Joueur[] joueurs;
    private boolean estPaye;
    private boolean publique; 
    private LocalTime heureDebut;
    private LocalDate date;
    private Terrain terrain;

    public Reservation(Joueur[] joueurs, boolean estPaye, boolean publique, LocalTime heureDebut, LocalDate date, Terrain terrain){
        this.joueurs = joueurs;
        this.estPaye = estPaye;
        this.publique = publique;
        this.heureDebut = heureDebut;
        this.date = date;
        this.terrain = terrain;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public boolean getEstPaye() {
        return estPaye;
    }

    public boolean getPublique() {
        return publique;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalDate getDate() {
        return date;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setJoueurs(Joueur[] joueurs) {
        this.joueurs = joueurs;
    }

    public void setEstPaye(boolean estPaye) {
        this.estPaye = estPaye;
    }

    public void setPublique(boolean publique) {
        this.publique = publique;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
