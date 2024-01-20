<?php

include 'informationProfil.php';
?>



<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <title>
        <?php echo $_TITRE_PAGE ?>
    </title>
    <link href="style4.css" rel="stylesheet" type="text/css" />

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
            <a href="accueil.php" style="font-weight: bold;">Accueil</a>
        </div>

        <div class="reservation">
            <a href="reservations.php">Réservation </a>
        </div>

        <div class="tournois">
            <a href="tournois.php">Tournois </a>
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
            
                <?php
                    $variable1 ="  &nbsp &nbsp "; 
                    echo " Nom : ".$nomUtilisateur; 
                    echo " ".'</br>'; 
                    
                    echo " Prénom :  ".$prenomUtilisateur.'</br>' ;
                    echo " ".'</br>'; ?>

            </div>
            <div class="afficherNiveauProfil">
                
                <?php 
                echo " ".'</br>';
                echo "Niveau padel : ".$niveauUtilisateur 
                 ?>
            </div>

            <div class="Deconnexion"> <a style="color:white" href="index.php">DECONNEXION</a> </div>
        </div>
    </div>







    <!-- Fond de couleur beige  -->
    <div class="fond">
        <div class="slideshow-container">
            <!-- Full-width images -->
            <div class="mySlides" style="text-align: center;">
                <img src="images/tournois_1.png" width="1000" height="430">
            </div>

            <div class="mySlides" style="text-align: center;">
                <img src="images/actualites_1.jpg" width="1000" height="430">
            </div>

            <div class="mySlides" style="text-align: center;">
                <img src="images/niveaux_padel.jpg" width="1000" height="430">
            </div>


            <!-- The dots/circles -->
            <div style="text-align:center">
                <div class="position_dot">
                    <span class="dot" onclick="currentSlide(1)"></span>
                    <span class="dot" onclick="currentSlide(2)"></span>
                    <span class="dot" onclick="currentSlide(3)"></span>
                </div>
            </div>


            <!-- Next and previous buttons -->
            <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
            <a class="next" onclick="plusSlides(1)">&#10095;</a>

            <!--  Bouton pour aller à la réservation -->
            <a class="Réservation_btn" href="reservations.php"> RESERVER UN TERRAIN</a>

        </div>
    </div>


</body>

<!-- Code javaScript pour les boutons NOTIFICATIONS et MON PROFIL  -->
<script src="js/bouton.js"></script>
<!-- Code javaScript por le carrousel d'images  -->
<script src="js/caroussel.js"></script>



</html>