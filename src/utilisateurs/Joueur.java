package utilisateurs;
import java.util.Date;

public class Joueur extends Utilisateur{
    private int id;
    private int niveau;
    Date dateDeNaissance;

    public Joueur(String em, String mdp, String n, String p, int id, int niv, Date date){
        super(em, mdp, n, p);
        this.id = id;
        this.niveau = niv;
        this.dateDeNaissance = date;
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
}
