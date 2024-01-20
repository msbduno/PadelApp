<?php
include 'informationProfil.php';
?>


<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" crossorigin="anonymous">
    <title>4 Glass Walls</title>
    <link href="style4.css" rel="stylesheet" type="text/css" />
    <link href="style_calendier.css" rel="stylesheet" type="text/css" />
    <script type="module" src="js/script.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>



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
            <a href="reservations.php" style="font-weight: bold;">Réservation </a>
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
                    echo " ".'</br>'; 
                    ?>

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
    <div class="fond2">

    <div id="upper">
                
                <div class="middle-container">
                    <button name="previous_week" id="previous_week"><i class="fas fa-arrow-left"></i></button>
                    <div id="current-week"> <?php include 'current_week.php'; ?> </div>
                    <button name="next_week" id="next_week"><i class="fas fa-arrow-right"></i></button>
                </div>
                
    </div>

    <?php
    

    print('<table class="tableau" id="tableau">');
        $json = json_decode(file_get_contents('semaine.json'), true);
        $week = $json[0];
        $terrains = $json[1];
        $times = $json[2];
        $_SESSION['current_time'] = new DateTime();
        $startDate = clone $_SESSION['current_time'];
        $tournois = array_map(function ($i) use ($startDate) { return (clone $startDate)->add(new DateInterval("P{$i}D")); }, range(0, 2));

        print("<tr class=days><th></th>");
        foreach($tournois as $day)
            print("<th colspan=4>" . $day->format('l, d F Y') . "</th>");
        print("</tr>");
        
        print("<tr class=terrains><th>Heure</th>");
        foreach($tournois as $day)
            foreach($terrains as $terrain)
                print("<th colspan=1>" . $terrain . "</th>");
        print("</tr>");

        foreach($times as $time => $value) {
            print("<tr class=times> <th>" . $time . "</th>");
            foreach($tournois as $day) {
                $tournois2024 = [
                    '2024-01-24' => 'Tournoi : P250',
                    '2024-04-09' => 'Tournoi : P500',
                    '2024-04-10' => 'Tournoi : P100', 
                ];

                foreach($tournois2024 as $date=>$event) {
                    $datetime = new DateTime($date);
                    if ($day->format('md') == $datetime->format('md')) {
                        if($time == '8h00') print("<td colspan=4 rowspan=34 class=tournoi>" . $event . "</td>");
                        goto out;
                    }
                }
                foreach($terrains as $terrain) {
                    $filled = false;
                    foreach($value as $val) {
                        $groupIsAll = $val["terrain"] == "All";
                        if($val["date"] == $day->format('Y-m-d') && ($groupIsAll || $val["terrain"] == $group)) {
                            if($val["duree"] != -1) {
                                $colspan = $groupIsAll ? " colspan=3" : "";
                                $isModalActive = $_SESSION['authenticated'] ? "data-modal-target=#modal " : "";
                                $class_content = $val["joueur1"] . "<br>". $val["joueur2"] . "<br>" . $val["joueur3"] . "<br>". $val["joueur4"];
                                
                                $id = ($groupIsAll) ? "all" : (($val["group"] == "T4") ? "last" : (($val["group"] == "T1") ? "first" : "middle"));
                                print("<td " . $isModalActive . $bg_color . " rowspan=" . $val["duree"] . $colspan . " class=" . $val["id"] . " id=" . $id . "> " . $class_content . " </td>");
                            } $filled = true;
                            if ($groupIsAll) goto out;
                        }
                    } if(!$filled) {
                        $classname = str_replace("h", "_", $time) . "_" . $day->format('Y-m-d') . "_" . $terrain;
                        $_SESSION['authenticated'] = true;
                        $isModalActive = $_SESSION['authenticated'] ? "data-modal-target=#modal" : "";
                        $id = ($terrain == "T4") ? "last" : (($terrain == "T1") ? "first" : "middle");
                        print("<td " . $isModalActive . " class=" . $classname . " id=" . $id . " style=\"cursor: url('cursors/precision.png')  auto;\"></td>");
                    } 
                } out:
            } print("</tr>");
        }
    print('</table>')
?>


    </div>
    </div>
</body>

<!-- Code javaScript pour les boutons NOTIFICATIONS et MON PROFIL  -->
<script src="js/bouton.js"></script>




</html>
