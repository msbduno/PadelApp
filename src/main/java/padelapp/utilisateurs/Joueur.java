package padelapp.utilisateurs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Joueur extends Utilisateur{
    private int id;
    private int niveau;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;

    public Joueur(){
        super();
    }

    public Joueur(String em, String mdp, String n, String p, int id, int niv){
        super(em, mdp, n, p);
        this.id = id;
        this.niveau = niv;
    }

    public int getId() {
        return id;
    }

    public int getNiveau() {
        return niveau;
    }
    
    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getNomPrenom(){
        return this.getNom().toUpperCase() + " " + this.getPrenom();
    }
}
