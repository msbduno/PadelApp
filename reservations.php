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
     <!-- Première div  pour les pages de navigations du site -->

    <div class="Navigation">

        <div class="AppLogo">
            <img src="images/logo.jpg" width="170" height="137" />
        </div>

        <div class="AppName">
            <a href="accueil.php">4 GLASS WALLS</a>
        </div>

        <div class="Page1">
            <a href="accueil.php">Accueil</a>
        </div>

        <div class="Page2">
            <a href="reservations.php" style="font-weight: bold;">Réservation</a>
        </div>

        <div class="Page3">
            <a href="tournois.php">Tournois </a>
        </div>

        <div class="Page4">
            <a href="blog.php">Blog</a>
        </div>
    </div>

    <!-- Fond de couleur beige  -->
    

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
        <div class="fond">

            
        </div>

</div>

<div>

    <div class="TitreReservation">JE RESERVE UNE PISTE </div>

    <div class="Carre"></div>

    <div class="Date">
        <input id="date" type="date"  />
    </div>

    <div class="Statut">
        <label class="colorblanc" for="Publique">PRIVEE</label>
        <select id="annee" name="annee" required>
            <option value="1">PRIVEE</option>
            <option value="2">PUBLIQUE</option>
        </select>
    </div>

    


</div>
</body>

</html>