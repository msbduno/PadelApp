<?php

// Titre des différentes pages
$_TITRE_PAGE = '4 GLASS WALLS';

// Permet de voir apparaître les erreurs directement dans la page web
error_reporting(E_ALL);
ini_set('display_errors', 'On');
ini_set('display_startup_errors', ' On');

session_start();

// Informations BDD
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

if (isset($_GET['logout']) && $_GET['logout'] == 1) {
    unset($_SESSION['compte']);
    header("Location: ./");
}

?>