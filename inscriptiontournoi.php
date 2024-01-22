<?php
include 'notificationReservation.php';

if (empty($_SESSION['compte'])) {
    header("Location: index.php");
    exit();
}

unset($_POST['nomJoueur1']);
unset($_POST['prenomJoueur1']);
unset($_POST['mailJoueur1']);
unset($_POST['nomJoueur2']);
unset($_POST['prenomJoueur2']);
unset($_POST['mailJoueur2']);
unset($_POST['nomTournoi']);



?>





<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
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
            <a href="tournois.php" style="font-weight: bold;">Tournois </a>
        </div>

        <div class="blog">
            <a href="blog.php">Blog</a>
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
        <form method="POST">

            <ul>
                <li>

                    <label for="nomTournoi">Veuillez sélectionner un tournoi&nbsp;:</label>
                    <div class="box">
                        <select name="nomTournoi" id="nomTournoi">
                            <option value="null" style="text-align: center;">Sélectionner un tournoi</option>
                            <option value="1" style="text-align: center;">
                                <?php echo $nomTournois1 ?>, niveau
                                <?php echo $niveauTournois1 ?>
                            </option>
                            <option value="2" style="text-align: center;">
                                <?php echo $nomTournois2 ?>, niveau
                                <?php echo $niveauTournois2 ?>
                            </option>
                            <option value="3" style="text-align: center;">
                                <?php echo $nomTournois3 ?>, niveau
                                <?php echo $niveauTournois3 ?>
                            </option>
                            <option value="4" style="text-align: center;">
                                <?php echo $nomTournois4 ?>, niveau
                                <?php echo $niveauTournois4 ?>
                            </option>

                        </select>
                    </div>

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


                <button class="boutontournoi" name="tournoi_submit" value="1" type="submit"
                    id="tournoi_submit">S'INSCRIRE
                </button>

                <div class="warning"> 
                    <img src="images/warning.png" style="width: 10%;">
                    Attention : le joueur 2 doit avoir un compte 4 GLASS WALLS 
                </div>


            </ul>



        </form>


    </div>


</body>

<!-- Code javaScript pour les boutons NOTIFICATIONS et MON PROFIL  -->
<script src="js/bouton.js"></script>




</html>