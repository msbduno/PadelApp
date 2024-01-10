<?php
include 'core.php';
include 'blog.php'; 
include 'inscription.php'; 
include 'inscriptiontournoi.php'; 
include 'reservation.php'; 
include 'tournois.php'; 

$variable1 ="  &nbsp &nbsp "; 

if (isset($_POST['connexion_submit']) && $_POST['connexion_submit'] == 1) {
    if (!empty($_POST['mail']) && !empty($_POST['password'])) {
        $mail_escaped = $mysqli->real_escape_string(trim($_POST['mail']));
        //ligne échappe les caractères spéciaux dans la valeur du champ de courriel
        $password_escaped = $mysqli->real_escape_string(trim($_POST['password']));
        //ligne échappe les caractères spéciaux dans la valeur du champ de mot de passe
        $sql = "SELECT *
            FROM Etudiant 
            WHERE email = '" . $mail_escaped . "'
            AND motDePasse = '" . $password_escaped . "'";

        $result = $mysqli->query($sql);
        //le résultat de la requête est stocké dans la variable $result.
        if (!$result) {
            exit($mysqli->error);
        }
        $nb = $result->num_rows;
        //Cette ligne récupère le nombre de lignes retournées par la requête SQL et le stocke dans la variable $nb.
        if ($nb) {
            //récupération de l’id de l’étudiant
            $row = $result->fetch_assoc();
            $_SESSION['compte'] = $row['id'];
            $_PROFIL = $row; 

        }
    }
}

if (isset($_POST['inscription_submit']) && $_POST['inscription_submit'] == 1) {
    if (
        !empty($_POST['nom']) &&
        !empty($_POST['prenom']) &&
        !empty($_POST['niveau']) &&
        !empty($_POST['mail']) &&
        !empty($_POST['password']) &&
        !empty($_POST['confirmPassword'])
    ) {
        if ($_POST['password'] == $_POST['confirmPassword']) {
            $nom_escaped = $mysqli->real_escape_string(trim($_POST['nom']));
            $prenom_escaped = $mysqli->real_escape_string(trim($_POST['prenom']));
            $annee_escaped = $mysqli->real_escape_string(trim($_POST['annee']));
            $mail_escaped = $mysqli->real_escape_string(trim($_POST['mail']));
            $password_escaped = $mysqli->real_escape_string(trim($_POST['password']));
            $confirmPassword_escaped = $mysqli->real_escape_string(trim($_POST['confirmPassword']));
   
            $sql2 = "INSERT INTO Etudiant
                     SET    nom             = '$nom_escaped',
                            prenom          = '$prenom_escaped',
                            niveau = '$annee_escaped',
                            email           = '$mail_escaped',
                            motDePasse      = '$password_escaped',
                            ";
   
            $result = $mysqli->query($sql2);
            if (!$result) {
                exit($mysqli->error);
            }
            
            $idEtudiant = mysqli_insert_id($mysqli); 
            if ($idEtudiant) {
                $_SESSION['compte'] = $idEtudiant;
            }

        }
    }
}


$mysqli->close();
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
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>

    <!--  Première div  pour les pages de navigations du site -->

    <div class="Navigation">

        <div class="AppLogo">
            <img src="images/logo.jpg" width="170" height="137" />
        </div>

        <div class="AppName">
            <a href="index.php">4 GLASS WALLS </a>
        </div>

        <div class="Page1">
            <a href="index.php" style="font-weight: bold;">Accueil</a>
        </div>

        <div class="Page2">
            <a href="reservations.php">Réservation </a>
        </div>

        <div class="Page3">
            <a href="tournois.php">Tournois </a>
        </div>

        <div class="Page4">
            <a href="blog.php">Blog</a>
        </div>
    </div>




    <!-- Slideshow container -->
    <div class="slideshow-container">

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
            <div class="Deconnexion "> <a class="button4" href="Connexion.php">CONNEXION</a> </div>
        </div>

    </div>

    <!-- Fond de couleur beige  -->
    <div class="fond">
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
        <a class="Réservation_btn button4" href="reservations.php"> RESERVER UN TERRAIN</a>

    </div>



</body>

<!-- Code javaScript pour les boutons NOTIFICATIONS et MON PROFIL  -->
<script>

    // Get the button, and when the user clicks on it, execute myFunction
    document.getElementById("noti_btn").onclick = function () { Notificationsbtn() };
    document.getElementById("MonProf_btn").onclick = function () { MonProfilbtn() };

    /* myFunction toggles between adding and removing the show class, which is used to hide and show the dropdown content */
    function Notificationsbtn() {
        document.getElementById("noti_content").classList.toggle("show");

    }
    function MonProfilbtn() {
        document.getElementById("MonProf_content").classList.toggle("show");
    }

</script>


<!-- Code javaScript por le carrousel d'images  -->
<script>

    let slideIndex = 2;
    showSlides(slideIndex);

    // Next/previous controls
    function plusSlides(n) {
        showSlides(slideIndex += n);
    }

    // Thumbnail image controls
    function currentSlide(n) {
        showSlides(slideIndex = n);
    }

    function showSlides(n) {
        let i;
        let slides = document.getElementsByClassName("mySlides");
        let dots = document.getElementsByClassName("dot");
        if (n > slides.length) { slideIndex = 1 }
        if (n < 1) { slideIndex = slides.length }
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        for (i = 0; i < dots.length; i++) {
            dots[i].className = dots[i].className.replace(" active", "");
        }
        slides[slideIndex - 1].style.display = "block";
        dots[slideIndex - 1].className += " active";
    }

</script>

</html>