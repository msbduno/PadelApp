package padelapp.ui;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;

import padelapp.interactions.Reservation;

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
            this.reservations = fetchDataFromDatabase(connection);

            // Etape 4 : Ecriture des données au format JSON dans un fichier
            writeDataToJsonFile(this.reservations);

            System.out.println("Données extraites de la base de données et écrites dans le fichier " + outputPath);

            // Fermeture de la connexion
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private List<Reservation> fetchDataFromDatabase(Connection connection) throws SQLException {
        List<Reservation> dataList = new ArrayList<>();

        // Utilisez PreparedStatement pour éviter les attaques par injection SQL
        String query = "SELECT * FROM reservation";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Récupération des données de la base de données et création d'objets
                Reservation data = new Reservation();
                // ... Initialiser les propriétés de l'objet en fonction des colonnes de la base de données
                dataList.add(data);
            }
        }

        return dataList;
    }

    private void writeDataToJsonFile(List<Reservation> data) {
        try {
            // Utilisation de Jackson pour convertir les objets en format JSON
            ObjectMapper objectMapper = new ObjectMapper();
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