package padelapp.ui;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import padelapp.interactions.Reservation;
import padelapp.interactions.Terrain;
import padelapp.utilisateurs.Joueur;

public class DatabaseThread extends Thread {
    String url; 
    String username; //ID de connexion    
    String password; //MDP de connexion
    String outputPath;
    List<Reservation> reservations;
    private CountDownLatch latch;
    public boolean isDone;

    public DatabaseThread() {
        super();
        this.url = "jdbc:mysql://192.168.56.81/PadelApp";//Url de connexion a la BDD
        this.username = "admin"; //ID de connexion
        this.password = "network"; //MDP de connexion
        this.outputPath = "src/main/java/padelapp/ressources/reservations.json";
        this.reservations = new ArrayList<Reservation>();
    }

    public DatabaseThread(CountDownLatch ct) {
        //TODO Auto-generated constructor stub
        super();
        this.url = "jdbc:mysql://192.168.56.81/PadelApp";//Url de connexion a la BDD
        this.username = "admin"; //ID de connexion
        this.password = "network"; //MDP de connexion
        this.outputPath = "src/main/java/padelapp/ressources/reservations.json";
        this.reservations = new ArrayList<Reservation>();
        this.latch = ct;
        this.isDone = false;
    }

    @Override
    public void run() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            // Etape 3 : Extraction des données
            this.reservations = fetchReseravationFromDatabase(connection);

            // Etape 4 : Ecriture des données au format JSON dans un fichier
            writeReservationsToJsonFile(this.reservations);

            System.out.println("Données extraites de la base de données et écrites dans le fichier " + outputPath);

            // Fermeture de la connexion
            connection.close();

            latch.countDown();

            this.isDone = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private List<Reservation> fetchReseravationFromDatabase(Connection connection) throws SQLException {
        List<Reservation> resaList = new ArrayList<>();

        // Utilisez PreparedStatement pour éviter les attaques par injection SQL
        String query = "SELECT * FROM reservation";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Récupération des données de la base de données et création d'objets
                Reservation resa = new Reservation();

                // Initialiser les propriétés de l'objet en fonction des colonnes de la base de données
                resa.setIdReservation(resultSet.getInt("idReservation"));
                resa.setEstPaye(resultSet.getBoolean("estPaye")); 
                resa.setPublique(resultSet.getBoolean("estPublique"));
                resa.setHeureDebut(resultSet.getTime("heureDebut").toLocalTime());
                resa.setDate(resultSet.getDate("dateRes").toLocalDate());

                // Pour le terrain, vous devez d'abord récupérer le numéro du terrain,
                // puis utiliser ce numéro pour récupérer le terrain correspondant de la base de données.
                int idTerrain = resultSet.getInt("idTerrain");
                Terrain terrain = fetchTerrainFromDatabase(connection, idTerrain);
                resa.setTerrain(terrain);

                // Pour la liste des joueurs, vous devez d'abord récupérer les identifiants des joueurs,
                // puis utiliser ces identifiants pour récupérer les joueurs correspondants de la base de données.
                int idReservation = resultSet.getInt("idReservation");
                List<Joueur> joueurs = fetchJoueursFromDatabase(connection, idReservation);
                resa.setJoueurs(joueurs);

                resaList.add(resa);
            }
        }

        return resaList;
    }

    private Terrain fetchTerrainFromDatabase(Connection connection, int idTerrain) throws SQLException {
        Terrain terrain = null;

        String query = "SELECT * FROM terrain WHERE idTerrain = " + String.valueOf(idTerrain);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                terrain = new Terrain();
                terrain.setIdTerrain(resultSet.getInt("idTerrain"));
                terrain.setNumero(resultSet.getInt("numero"));
            }
        }

        return terrain;
    }

    private List<Joueur> fetchJoueursFromDatabase(Connection connection, int idReservation) throws SQLException {
        List<Joueur> joueurs = new ArrayList<>();

        String query = "SELECT * FROM joueurs WHERE idReservation = " + String.valueOf(idReservation) + " ORDER BY idUtilisateur ASC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String query2 = "SELECT * FROM utilisateur WHERE idUtilisateur = " + String.valueOf(resultSet.getInt("idUtilisateur"));
                try (PreparedStatement stmt2 = connection.prepareStatement(query2)) {
                    ResultSet resultSet2 = stmt2.executeQuery();
                    if (resultSet2.next()) {
                        Joueur joueur = new Joueur();
                        joueur.setEmail(resultSet2.getString("email"));
                        joueur.setMotDePasse(resultSet2.getString("motDePasse"));
                        joueur.setNom(resultSet2.getString("nom"));
                        joueur.setPrenom(resultSet2.getString("prenom"));
                        joueur.setId(resultSet2.getInt("idUtilisateur"));
                        joueur.setNiveau(resultSet2.getInt("niveau"));

                        joueurs.add(joueur);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return joueurs;
    }

    private void writeReservationsToJsonFile(List<Reservation> data) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        try {
            // Utilisation de Jackson pour convertir les objets en format JSON
            objectMapper.writeValue(new File(outputPath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setIsDown(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getIsDown() {
        return this.isDone;
    }

    public static void main(String[] args) {
        // Création et démarrage du thread
        DatabaseThread databaseThread = new DatabaseThread();
        databaseThread.start();
    }
}