<?php
include 'notificationReservation.php';
if (empty($_SESSION['compte'])) {
    header("Location: index.php");
    exit();
}
?>



<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <title>4 Glass Walls</title>
    <link href="style5.css" rel="stylesheet" type="text/css" />

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
            <a href="tournois.php">Tournois </a>
        </div>

        <div class="blog">
            <a href="blog.php" style="font-weight: bold;">Blog</a>
        </div>



        <button id="noti_btn" class="Notifications_btn">NOTIFICATIONS </button>
        <div id="noti_content" class="Notifications_content">
            <div class="afficherNotification">
                <?php if ($findInfo) { ?>
                    <p>Vous avez une réservation le
                        <?php echo $dateRes; ?> à
                        <?php echo $heureDebut; ?> terrain
                        <?php echo $idTerrain; ?>
                    </p>
                <?php } else { ?>

                    <p>Vous n'avez pas de réservation</p>


                <?php } ?>

            </div>
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

                <?php
                $variable1 = "  &nbsp &nbsp ";
                echo " Nom : " . $nomUtilisateur;
                echo " " . '</br>';

                echo " Prénom :  " . $prenomUtilisateur . '</br>';
                echo " " . '</br>'; ?>

            </div>
            <div class="afficherNiveauProfil">

                <?php
                echo " " . '</br>';
                echo "Niveau padel : " . $niveauUtilisateur
                    ?>
            </div>

            <div class="Deconnexion"> <a style="color:white" href="index.php">DECONNEXION</a> </div>
        </div>
    </div>

    <!-- Fond de couleur beige  -->
    <div class="fond">

        <div
            style="text-align:center; margin-left: auto; margin-right: auto; font-style: Inter; font-weight: bold; font-size: 40px;">
            PROCHAINEMENT...
        </div>



    </div>



    </div>
</body>

<!-- Code javaScript pour les boutons NOTIFICATIONS et MON PROFIL  -->
<script src="js/bouton.js"></script>




</html>