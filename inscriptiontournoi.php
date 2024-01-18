<?php
include 'informationProfil.php';

if (!empty($_SESSION['compte'])) {



    $sql4 = "SELECT nom, niveau, idTournois
        FROM tournois WHERE
        idTournois = '1'";

    $result4 = $conn->query($sql4);
    if (!$result4) {
        exit($conn->$error);
    }



    $nb4 = $result4->num_rows;
    if ($nb4) {
        //récupération des infos de l'utilisateur
        $row4 = $result4->fetch_assoc();
        $nomTournois = $row4['nom'];
        $niveauTournois = $row4['niveau'];
        $idTournois = $row4['idTournois'];

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

            $sql6 = "INSERT INTO equipe
            SET idTournois = '$idTournois',
            idJoueur1 = '$idJoueur1',
            idJoueur2 = '$idJoueur2'";

            $result6 = $conn->query($sql6);
            if (!$result6) {
                exit($conn->$error);
            }


        }

    }
}
?>





<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <title>4 Glass Walls</title>
    <link href="style3.css" rel="stylesheet" type="text/css" />

</head>

<body>
    <!--  Première div  pour les pages de navigations du site -->

    <div class="Navigation">

        <div class="AppLogo">
            <img src="images/logo.jpg" width="170" height="137" />
        </div>

        <div class="AppName">
            <a href="accueil.php">4 GLASS WALLS </a>
        </div>

        <div class="accueil">
            <a href="accueil.php">Accueil</a>
        </div>

        <div class="reservation">
            <a href="reservations.php">Réservation </a>
        </div>

        <div class="tournois">
            <a href="tournois.php" style="font-weight: bold;">Tournois </a>
        </div>

        <div class="blog">
            <a href="blog.php">Blog</a>
        </div>



        <button id="noti_btn" class="Notifications_btn">NOTIFICATIONS </button>
        <div id="noti_content" class="Notifications_content">
            <!--  -->
            <button id="noti_btn1" class="Notifications_btn1">A venir </button>
            <button id="noti_btn2" class="Notifications_btn2">Non lues </button>

            <div class="Barre_Notifications"> </div>
        </div>


        <button id="MonProf_btn" class="MonProfil_btn">MON PROFIL </button>
        <div id="MonProf_content" class="MonProfil_content">
            <!--  -->
            <div class="Profil"> Profil </div>

            <div class="Barre_MonProfil"> </div>
            <div class="photoProfil">
                <img src="images/default_profil_picture.png" alt="Photo de profil par défaut" width=150px height=150px>
            </div>
            <div class="afficherNomProfil">
                Bonjour
                <?php echo $prenomUtilisateur ?>
                <?php echo $nomUtilisateur ?>

            </div>
            <div class="afficherNiveauProfil">
                Votre niveau de Padel :
                <?php echo $niveauUtilisateur ?>
            </div>

            <div class="Deconnexion"> <a style="color:white" href="index.php">DECONNEXION</a> </div>
        </div>
    </div>

    <!-- Fond de couleur beige  -->
    <div class="fond">
        <form method="POST">

            <ul>
                <li>
                    <label for="nomTournoi">Veuillez sélectionner un tournoi&nbsp;:</label>
                    <select name="nomTournoi" id="nomTournoi">
                        <option value="null">Sélectionner un tournoi</option>
                        <option value="1">
                            <?php echo $nomTournois ?>, niveau
                            <?php echo $niveauTournois ?>
                        </option>

                    </select>
                </li>

                <li>
                    <label for="nomJoueur1">Nom du joueur 1&nbsp;:</label>
                    <input type="text" id="nomJoueur1" name="nomJoueur1" />
                    <script>
                        document.getElementById('nomJoueur1').setAttribute('value', "<?php echo $nomUtilisateur ?>");
                    </script>
                </li>
                <li>
                    <label for="prenomJoueur1">Prénom du joueur 1&nbsp;:</label>
                    <input type="text" id="prenomJoueur1" name="prenomJoueur1" />
                    <script>
                        document.getElementById('prenomJoueur1').setAttribute('value', "<?php echo $prenomUtilisateur ?>");
                    </script>
                </li>
                <li>
                    <label for="mailJoueur1">Email du joueur 1&nbsp;:</label>
                    <input type="mail" id="mailJoueur1" name="mailJoueur1" />
                    <script>
                        document.getElementById('mailJoueur1').setAttribute('value', "<?php echo $mailUtilisateur ?>");
                    </script>
                </li>
                <li>
                    <label for="nomJoueur2">Nom du joueur 2&nbsp;:</label>
                    <input type="text" id="nomJoueur2" name="nomJoueur2" />
                </li>
                <li>
                    <label for="prenomJoueur2">Prénom du joueur 2&nbsp;:</label>
                    <input type="text" id="prenomJoueur2" name="prenomJoueur2" />
                </li>
                <li>
                    <label for="mailJoueur2">Email du joueur 2&nbsp;:</label>
                    <input type="mail" id="mailJoueur2" name="mailJoueur2" />
                </li>

                <button name="tournoi_submit" value="1" type="submit">S'INSCRIRE</button>

            </ul>


        </form>


    </div>


</body>

<!-- Code javaScript pour les boutons NOTIFICATIONS et MON PROFIL  -->
<script src="js/bouton.js"></script>




</html>