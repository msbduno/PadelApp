<?php
include 'core.php';

//$_SESSION['compte'] = '';
unset($_SESSION['compte']);


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
                estModerateur = '0'";

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
    <link href="style3.css" rel="stylesheet" type="text/css" />

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
                glasswalls.onclick = function () { alert("Veuillez vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>
        </div>

        <div class="accueil">

            <a id="accueil">Accueil
                <?php
                echo '<script type="text/javascript">
                accueil.onclick = function () { alert("Veuillez vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>

        </div>

        <div class="reservation">
            <a id="reservation">Réservation
                <?php
                echo '<script type="text/javascript">
                reservation.onclick = function () { alert("Veuillez vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>
        </div>

        <div class="tournois">
            <a id="tournois">Tournois
                <?php
                echo '<script type="text/javascript">
                tournois.onclick = function () { alert("Veuillez vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>
        </div>

        <div class="blog">
            <a id="blog">Blog
                <?php
                echo '<script type="text/javascript">
                blog.onclick = function () { alert("Veuillez vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
                </script>';
                ?>
            </a>
        </div>

        <button id="noti_btn" class="Notifications_btn">NOTIFICATIONS

            <?php
            echo '<script type="text/javascript">
noti_btn.onclick = function () { alert("Veuillez vous connecter ou vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
</script>';
            ?>
        </button>

        <button id="MonProf_btn" class="MonProfil_btn">MON PROFIL
            <?php
            echo '<script type="text/javascript">
MonProf_btn.onclick = function () { alert("Veuillez vous connecter ou vous inscrire pour accéder aux fonctionnalités de 4 GLASS WALLS !"); } 
</script>';
            ?>
        </button>
    </div>

    

    <div class="fondInscripitionImage">
        <div class="fondInscripition">
            <div class="titreInscription1">CREATION DE COMPTE </div>
            <div class="titreInscription2">Crée un compte de façon simple afin de profiter des services de 4 GLASS WALLS
            </div>

            <form method="POST">
                <?php
                if (empty($_SESSION['compte'])) {
                    ?>
                    <div class="inscriptionNom">
                        <label class="colorblanc" for="nom">Nom</label>
                        <input id="nom" name="nom" type="text" placeholder="Veuillez renseigner votre nom">
                    </div>
                    <div class="inscriptionPrenom">
                        <label class="colorblanc" for="prenom">Prenom</label>
                        <input id="prenom" name="prenom" type="text" placeholder="Veuillez renseigner votre prénom">
                    </div>

                    <div class="inscriptionNiveau">
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

                    <div class="inscriptionMail">
                        <label class="colorblanc" for="mail">Email</label>
                        <input id="idmail" name="mail" type="text" placeholder="Veuillez renseigner votre adresse mail">
                    </div>


                    <div class="inscriptionPassword">
                        <label class="colorblanc" for="password">Mot de passe</label>
                        <input name="password" type="password" id="defaultLoginFormPassword"
                            placeholder="Veuillez renseigner votre mot de passe">
                    </div>

                    <div class="inscriptionConfirmPassword">
                        <label class="colorblanc" for="confirmPassword">Confirmer votre mot de passe</label>
                        <input type="password" name="confirmPassword" id="defaultLoginFormPassword"
                            placeholder="Veuillez confirmer votre mot de passe">
                    </div>



                    <button name="subscribe_submit" class="InscriptionCompte" value="1" type="submit">CREER MON
                        COMPTE</button>

                <?php } else {
                    if (isset($_POST['subscribe_submit']) && $_POST['subscribe_submit'] == 1) {
                        header("Location: accueil.php");
                    }
                } ?>

            </form>

        </div>
    </div>


</body>



</html>