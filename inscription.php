<?php
include 'core.php';




if (isset($_POST['subscribe_submit']) && $_POST['subscribe_submit'] == 1) {
    if (
        !empty($_POST['nom']) &&
        !empty($_POST['prenom']) &&
        !empty($_POST['niveau']) &&
        !empty($_POST['mail']) &&
        !empty($_POST['password']) &&
        !empty($_POST['confirmPassword'])
    ) {
        if ($_POST['password'] == $_POST['confirmPassword']) {
            $nom_escaped = $conn->real_escape_string(trim($_POST['nom']));
            $prenom_escaped = $conn->real_escape_string(trim($_POST['prenom']));
            $niveau_escaped = $conn->real_escape_string(trim($_POST['niveau']));
            $email_escaped = $conn->real_escape_string(trim($_POST['mail']));
            $password_escaped = $conn->real_escape_string(trim($_POST['password']));

            $sql = "INSERT INTO utilisateur 
            SET email = '$email_escaped',
                motDePasse = '$password_escaped',
                nom = '$nom_escaped',
                prenom = '$prenom_escaped',
                niveau = '$niveau_escaped',
                numeroTelephone = '0788553227'";

            $result = $conn->query($sql);
            if (!$result) {
                exit($conn->$error);
            }
            if ($idUtilisateur = $conn->insert_id) {
                $_SESSION['compte'] = $idUtilisateur;
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
            <a href="index.php">4 GLASS WALLS </a>
        </div>

        <div class="Page1">
            <a href="index.php">Accueil</a>
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

    <div class="fondInscripition">
        <div class="back">
            <div class="titre1">CREATION DE COMPTE </div>
            <div class="titre2">Crée un compte de façon simple afin de profiter des services de 4 GLASS WALLS </div>

            <form method="POST">
                <div class="bloc1">
                    <label class="colorblanc" for="nom">Nom</label>
                    <input id="nom" name="nom" type="text">
                </div>
                <div class="bloc2">
                    <label class="colorblanc" for="prenom">Prenom</label>
                    <input id="prenom" name="prenom" type="text">
                </div>

                <div class="bloc3">
                    <label class="colorblanc" for="niveau">Niveau Padel</label>
                    <select id="niveau" name="niveau" required>
                        <option value="null"></option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                </div>

                <div class="bloc4">
                    <label class="colorblanc" for="mail">Email</label>
                    <input id="idmail" name="mail" type="text">
                </div>


                <div class="bloc5">
                    <label class="colorblanc" for="password">Mot de passe</label>
                    <input name="password" type="password" id="defaultLoginFormPassword">
                </div>

                <div class="bloc6">
                    <label class="colorblanc" for="confirmPassword">Confirmer votre mot de passe</label>
                    <input type="password" name="confirmPassword" id="defaultLoginFormPassword">
                </div>



                <button name="subscribe_submit" class="CreationCompte1" value="1" type="submit">CREER MON
                    COMPTE</button>

                <?php if (isset($_POST['subscribe_submit']) && $_POST['subscribe_submit'] == 1) {
                    ?>
                    <div>
                        <h2>Vous êtes inscrit !</h2>
                    </div>
                <?php } ?>

            </form>

        </div>
    </div>


</body>

</html>