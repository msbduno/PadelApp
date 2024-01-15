<?php
$servername = "localhost";
$username = "moodle_user";
$password = "network";
$dbname = "moodle";

// Créer une connexion
$conn = new mysqli($servername, $username, $password, $dbname);

// Vérifier la connexion
if ($conn->connect_error) {
    die("Échec de la connexion : " . $conn->connect_error);
}

$sql = "SELECT idUtilisateur FROM utilisateur";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Afficher les données de chaque ligne
    while($row = $result->fetch_assoc()) {
        echo "idUtilisateur: " . $row["idUtilisateur"];
    }
} else {
    echo "0 résultats";
}
$conn->close();
?>
