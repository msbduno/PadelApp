<?php
include 'informationProfil.php';

if (!empty($_SESSION['compte'])) {

    // Information tournoi 1

    $sqlTournois1 = "SELECT nom, niveau, idTournois
        FROM tournois WHERE
        idTournois = '1'";

    $resultTournois1 = $conn->query($sqlTournois1);
    if (!$resultTournois1) {
        exit($conn->$error);
    }



    $nbTournois1 = $resultTournois1->num_rows;
    if ($nbTournois1) {

        $rowTournois1 = $resultTournois1->fetch_assoc();

        $nomTournois1 = $rowTournois1['nom'];
        $niveauTournois1 = $rowTournois1['niveau'];
        $idTournois1 = $rowTournois1['idTournois'];


    }

    // Information tournoi 2

    $sqlTournois2 = "SELECT nom, niveau, idTournois
        FROM tournois WHERE
        idTournois = '2'";

    $resultTournois2 = $conn->query($sqlTournois2);
    if (!$resultTournois2) {
        exit($conn->$error);
    }



    $nbTournois2 = $resultTournois2->num_rows;
    if ($nbTournois2) {

        $rowTournois2 = $resultTournois2->fetch_assoc();

        $nomTournois2 = $rowTournois2['nom'];
        $niveauTournois2 = $rowTournois2['niveau'];
        $idTournois2 = $rowTournois2['idTournois'];


    }

    // Information tournoi 3

    $sqlTournois3 = "SELECT nom, niveau, idTournois
        FROM tournois WHERE
        idTournois = '3'";

    $resultTournois3 = $conn->query($sqlTournois3);
    if (!$resultTournois3) {
        exit($conn->$error);
    }



    $nbTournois3 = $resultTournois3->num_rows;
    if ($nbTournois3) {

        $rowTournois3 = $resultTournois3->fetch_assoc();

        $nomTournois3 = $rowTournois3['nom'];
        $niveauTournois3 = $rowTournois3['niveau'];
        $idTournois3 = $rowTournois3['idTournois'];


    }

    // Information tournoi 4

    $sqlTournois4 = "SELECT nom, niveau, idTournois
     FROM tournois WHERE
     idTournois = '4'";

    $resultTournois4 = $conn->query($sqlTournois4);
    if (!$resultTournois4) {
        exit($conn->$error);
    }



    $nbTournois4 = $resultTournois4->num_rows;
    if ($nbTournois4) {

        $rowTournois4 = $resultTournois4->fetch_assoc();

        $nomTournois4 = $rowTournois4['nom'];
        $niveauTournois4 = $rowTournois4['niveau'];
        $idTournois4 = $rowTournois4['idTournois'];


    }


    if (isset($_POST['tournoi_submit']) && $_POST['tournoi_submit'] == 1) {
        if (
            !empty($_POST['nomJoueur1']) &&
            !empty($_POST['prenomJoueur1']) &&
            !empty($_POST['mailJoueur1']) &&
            !empty($_POST['nomJoueur2']) &&
            !empty($_POST['prenomJoueur2']) &&
            !empty($_POST['mailJoueur2']) &&
            !empty($_POST['nomTournoi'])
        ) {
            $mailJoueur2_escaped = $conn->real_escape_string(trim($_POST['mailJoueur2']));

            // Récuperation de l'ID du joueur 2
            $sql5 = "SELECT idUtilisateur 
            FROM utilisateur
            WHERE email = '$mailJoueur2_escaped'";

            $result5 = $conn->query($sql5);
            if (!$result5) {
                exit($conn->$error);
            }

            $nb5 = $result5->num_rows;
            if ($nb5) {
                //récupération des infos de l'utilisateur
                $row5 = $result5->fetch_assoc();
                $idJoueur2 = $row5['idUtilisateur'];
            }

            $idJoueur1 = $_SESSION['compte'];
            $idTournoisChoisi_escaped = $conn->real_escape_string(trim($_POST['nomTournoi']));

            $sql6 = "INSERT INTO equipe
            SET idTournois = '$idTournoisChoisi_escaped',
            idJoueur1 = '$idJoueur1',
            idJoueur2 = '$idJoueur2'";

            $result6 = $conn->query($sql6);
            if (!$result6) {
                exit($conn->$error);
            }


        } else {

            echo '<script type="text/javascript">
                    tournoi_submit.onclick = function () { alert("Veuillez remplir toutes les informations nécessaires pour participer à un tournoi"); } 
            </script>';

        }

    }
}
?>