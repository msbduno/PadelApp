<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <title>4 Glass Walls</title>
    <link href="style2.css" rel="stylesheet" type="text/css" />

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
            <a href="accueil.php" >Accueil</a>
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
            <div class="Gerer"> Gerer <a> Paramètres du compte </a> </div>
            <div class="Barre_MonProfil"> </div>
            <div class="Deconnexion "> <a style="color:white" href="index.php">DECONNEXION</a> </div>
        </div>
    </div>

    </div>
    <!-- Fond de couleur beige  -->
    <div class="fond">

        <!-- Slideshow container -->
        <div class="slideshow-container">

            <!-- Full-width images -->
            <div class="mySlides ">
                <img src="images/tournois_1.png" width="1000" height="430">
            </div>

            <div class="mySlides ">
                <img src="images/tournois_1.png" width="1000" height="430">
            </div>

            <div class="mySlides">
                <img src="images/tournois_1.png" width="1000" height="430">
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
            <a class="Réservation_btn button4" href="inscriptiontournoi.php"> INSCRIPTION TOURNOIS</a>

        </div>
    </div>


</body>

<!-- Code javaScript pour les boutons NOTIFICATIONS et MON PROFIL  -->
<script src="js/bouton.js"></script>

<!-- Code javaScript por le carrousel d'images  -->
<script src="js/caroussel.js"></script>


</html>