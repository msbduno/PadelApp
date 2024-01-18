<?php
include 'core.php';



if (!empty($_SESSION['compte'])) {
    $idUtilisateur_escaped = $conn->real_escape_string(trim($_SESSION['compte']));

    $sql3 = "SELECT prenom, nom, niveau, email
    FROM utilisateur 
    WHERE idUtilisateur = '" . $idUtilisateur_escaped . "'";

    $result3 = $conn->query($sql3);
    if (!$result3) {
        exit($conn->$error);
    }

    $nb3 = $result3->num_rows;
    if ($nb3) {
        //récupération des infos de l'utilisateur
        $row3 = $result3->fetch_assoc();
        $prenomUtilisateur = $row3['prenom'];
        $nomUtilisateur = $row3['nom'];
        $niveauUtilisateur = $row3['niveau'];
        $mailUtilisateur = $row3['email'];
    }

}





?>