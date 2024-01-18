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
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import padelapp.interactions.Reservation;
import padelapp.interactions.Terrain;
import padelapp.utilisateurs.Joueur;

public class DatabaseThread extends Thread {
    String url = "jdbc:mysql://192.168.56.81/PadelApp"; //Url de connexion a la BDD
    String username = "admin"; //ID de connexion    
    String password = "network"; //MDP de connexion
    String outputPath = "src/main/java/padelapp/ressources/reservations.json";
    List<Reservation> reservations = new ArrayList<Reservation>();

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
                String idJoueur = resultSet.getString("idUtilisateur");
                List<Joueur> joueur = fetchJoueursFromDatabase(connection, idJoueur);
                resa.setJoueurs(joueur);

                resaList.add(resa);
            }
        }

        return resaList;
    }

    private Terrain fetchTerrainFromDatabase(Connection connection, int idTerrain) throws SQLException {
        Terrain terrain = null;

        String query = "SELECT * FROM terrain WHERE idTerrain = " + String.valueOf(idTerrain);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            //preparedStatement.setObject(1, idTerrain);  // Utilisez setObject au lieu de setInt
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                terrain = new Terrain();
                terrain.setIdTerrain(resultSet.getInt("idTerrain"));
                terrain.setNumero(resultSet.getInt("numero"));
            }
        }

        return terrain;
    }

    private List<Joueur> fetchJoueursFromDatabase(Connection connection, String idJoueurs) throws SQLException {
        List<Joueur> joueurs = new ArrayList<>();

        // Assuming idJoueurs is a comma-separated string of player IDs
        String[] ids = idJoueurs.split(",");

        for (String id : ids) {
            String query = "SELECT * FROM utilisateur WHERE idUtilisateur = " + idJoueurs;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                //preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Joueur joueur = new Joueur();
                    joueur.setEmail(resultSet.getString("email"));
                    joueur.setMotDePasse(resultSet.getString("motDePasse"));
                    joueur.setNom(resultSet.getString("nom"));
                    joueur.setPrenom(resultSet.getString("prenom"));
                    joueur.setId(resultSet.getInt("idUtilisateur"));
                    joueur.setNiveau(resultSet.getInt("niveau"));

                    joueurs.add(joueur);
                }
            }
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

    public static void main(String[] args) {
        // Création et démarrage du thread
        DatabaseThread databaseThread = new DatabaseThread();
        databaseThread.start();
    }
}