<?php

include 'informationProfil.php';

if (!empty($_SESSION['compte'])) {

    $idUtilisateur = $_SESSION['compte'];


    $sqlRes = "SELECT idTerrain, heureDebut, dateRes
                        FROM reservation
                        WHERE idUtilisateur = '$idUtilisateur'";


    $resultRes = $conn->query($sqlRes);
    $findInfo = false;
    if (!$resultRes) {
        
        exit($conn->$error);
    }

    $nbRes = $resultRes->num_rows;
    if ($nbRes) {
        //récupération des infos de l'utilisateur
        $rowRes = $resultRes->fetch_assoc();
        $idTerrain = $rowRes['idTerrain'];
        $heureDebut = $rowRes['heureDebut'];
        $dateRes = $rowRes['dateRes'];
        $findInfo = true;
       
    }




}




?>