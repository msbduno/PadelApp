<<<<<<< HEAD
<?php

$_TITRE_PAGE = 'bathis mossard';



if (isset($_POST['connexion_submit']) && $_POST['connexion_submit'] == 1) {
    if (!empty($_POST['password']) && !empty($_POST['mail'])) {
        

        $mail_escaped = $mysqli->real_escape_string(trim($_POST['mail']));
        $password_escaped = $mysqli->real_escape_string(trim($_POST['password']));

        $sql1 = "SELECT id
                    FROM Etudiant
                    WHERE email = '" . $mail_escaped . "'
                    AND motDePasse = '" . $password_escaped . "'";

        $result = $mysqli->query($sql1);
        if (!$result) {
            exit($mysqli->$error);
        }

        $nb = $result->num_rows;
        if ($nb) {
            //récupération de l'id de l'étudiant
            $row = $result->fetch_assoc();
            $_SESSION['compte'] = $row['id'];
        }
    }
}

if (isset($_POST['subscribe_submit']) && $_POST['subscribe_submit'] == 1) {
    if (
        !empty($_POST['nom']) &&
        !empty($_POST['prenom']) &&
        !empty($_POST['anneescolaire']) &&
        !empty($_POST['email']) &&
        !empty($_POST['mdp']) &&
        !empty($_POST['mdpconf'])
    ) {

        if ($_POST['mdp'] == $_POST['mdpconf']) {
            $nom_escaped = $mysqli->real_escape_string(trim($_POST['nom']));
            $prenom_escaped = $mysqli->real_escape_string(trim($_POST['prenom']));
            $anneescolaire_escaped = $mysqli->real_escape_string(trim($_POST['anneescolaire']));
            $email_escaped = $mysqli->real_escape_string(trim($_POST['email']));
            $mdp_escaped = $mysqli->real_escape_string(trim($_POST['mdp']));

            $sql2 = "INSERT INTO Etudiant 
                    SET nom = '$nom_escaped',
                        prenom = '$prenom_escaped',
                        email = '$email_escaped',
                        motDePasse = '$mdp_escaped',
                        dateIns  = NOW(),
                        dateModif = NOW(),
                        id_AnneeScolaire = '$anneescolaire_escaped'";

            $result = $mysqli->query($sql2);
            if (!$result) {
                exit($mysqli->$error);
            }

            if ($idEtu = $mysqli->insert_id) {
                $_SESSION['compte'] = $idEtu;
            }
        }
    }
}


?>



=======
>>>>>>> 9f4f686de66a366d6a433775406496c70ed86999
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <title><?php echo $_TITRE_PAGE ?></title>
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
            <div class="Deconnexion "> <a class="button4" href="connexion.php">CONNEXION</a> </div>
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
    document.getElementById("noti_btn").onclick = function() {
        Notificationsbtn()
    };
    document.getElementById("MonProf_btn").onclick = function() {
        MonProfilbtn()
    };

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
        if (n > slides.length) {
            slideIndex = 1
        }
        if (n < 1) {
            slideIndex = slides.length
        }
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