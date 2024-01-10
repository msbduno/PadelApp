package padelapp.utilisateurs;

public class Utilisateur {
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;

    public Utilisateur(){}

    public Utilisateur(String em, String mdp, String n, String p){
        this.email = em;
        this.motDePasse = mdp;
        this.nom = n;
        this.prenom = p;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
