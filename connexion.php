<?php

include 'core.php';

$_SESSION['compte'] = '';


if (isset($_POST['connexion_submit']) && $_POST['connexion_submit'] == 1) {
    if (!empty($_POST['userpassword']) && !empty($_POST['usermail'])) {


        $usermail_escaped = $conn->real_escape_string(trim($_POST['usermail']));
        $userpassword_escaped = $conn->real_escape_string(trim($_POST['userpassword']));

        $sql1 = "SELECT idUtilisateur
                    FROM utilisateur
                    WHERE email = '" . $usermail_escaped . "'
                    AND motDePasse = '" . $userpassword_escaped . "'";

        $result = $conn->query($sql1);
        if (!$result) {
            exit($conn->$error);
        }

        $nb = $result->num_rows;
        if ($nb) {
            //récupération de l'id de l'étudiant
            $row = $result->fetch_assoc();
            $_SESSION['compte'] = $row['idUtilisateur'];
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <title>
        <?php echo $_TITRE_PAGE ?>
    </title>
    <link rel="stylesheet" type="text/css" href="style.css" />

</head>

<body>

    <div class="Navigation">

        <div class="AppLogo">
            <img src="images/logo.jpg" width="170" height="137" />
        </div>

        <div class="AppName">
            <a id="glasswalls">4 GLASS WALLS
                <?php
                echo '<script type="text/javascript">
                glasswalls.onclick = function () { alert("Veuillez vous connecter ou vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>
        </div>

        <div class="Page1">

            <a id="accueil">Accueil
                <?php
                echo '<script type="text/javascript">
                accueil.onclick = function () { alert("Veuillez vous connecter ou vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>

        </div>

        <div class="Page2">
            <a id="reservation">Réservation
                <?php
                echo '<script type="text/javascript">
                reservation.onclick = function () { alert("Veuillez vous connecter ou vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>
        </div>

        <div class="Page3">
            <a id="tournois">Tournois
                <?php
                echo '<script type="text/javascript">
                tournois.onclick = function () { alert("Veuillez vous connecter ou vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>
        </div>

        <div class="Page4">
            <a id="blog">Blog
                <?php
                echo '<script type="text/javascript">
                blog.onclick = function () { alert("Veuillez vous connecter ou vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>
        </div>
    </div>

    <!-- Slideshow container -->
    <div class="slideshow-container">

        <button id="noti_btn" class="Notifications_btn">NOTIFICATIONS</button>
        <div id="noti_content" class="Notifications_content">
            <!--  -->
            <button id="noti_btn1" class="Notifications_btn1">A venir </button>
            <button id="noti_btn2" class="Notifications_btn2">Non lues </button>

            <div class="Barre_Notifications"> </div>
        </div>


        <button id="MonProf_btn" class="MonProfil_btn">MON PROFIL</button>
        <div id="MonProf_content" class="MonProfil_content">
            <!--  -->
            <div class="Profil"> Profil </div>
            <div class="Gerer"> Gerer <a> Paramètres du compte </a> </div>
            <div class="Barre_MonProfil"> </div>
            <div class="Deconnexion "> <a class="button4" href="index.php">DECONNEXION</a> </div>
        </div>

    </div>



    <div class="Connexion">


        <div class="titreConnexion"> JE ME CONNECTE !</div>

        <form method="POST">
            <?php if (empty($_SESSION['compte'])) { ?>

                <p>
                    <label for="user">Email</label>
                    <input id="usermail" name="usermail" type="text" placeholder="Veuillez renseigner votre adresse mail">
                </p>
                <p>
                    <label for="password">Mot de passe</label>
                    <input name="userpassword" type="password" id="defaultLoginFormPassword"
                        placeholder="Veuillez renseigner votre mot de passe">
                </p>






                <button class="connexion_submit button1" name="connexion_submit" value="1" type="submit">SE
                    CONNECTER</button>

                <a class="mdpOublié" href="inscription.php">Mot de passe oublié ?</a>

                <div class="barre"> </div>
            <?php } else {

                if (isset($_POST['connexion_submit']) && $_POST['connexion_submit'] == 1) {


                    ?>
                    <div>
                        <h2>Vous êtes connecté !</h2>

                    </div>
                    <?php
                }
            }
            ?>
        </form>
    </div>



    <div class="Connexion2"></div>
    <div class="ConnexionImage">

        <div class="PasDeCompte"> TU N’AS PAS ENCORE DE COMPTE ?</div>

        <a class="CreationCompte " href="inscription.php">CREER MON COMPTE</a>
    </div>


</body>


<!-- Code javaScript pour les boutons NOTIFICATIONS et MON PROFIL  -->
<script>
    // Get the button, and when the user clicks on it, execute myFunction
    document.getElementById("noti_btn").onclick = function () {
        Notificationsbtn()
    };
    document.getElementById("MonProf_btn").onclick = function () {
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

</html>