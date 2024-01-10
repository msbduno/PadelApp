<?php
    // Permet de voir apparaître les erreurs directement dans la page web
    error_reporting(E_ALL);
    ini_set('display_errors', 'On');
    ini_set('display_startup_errors', ' On');

    // informations nécessaires à la connexion

    $infoBdd = ['server' => 'localhost',
                'login' => 'root',
                'password' => '',
                'db_name' => 'projet infralogiciel'  ];  

     // Création d'une connexion à la base
    
     $mysqli = new mysqli($infoBdd['server'], $infoBdd['login'],
     $infoBdd['password'],$infoBdd['db_name']);
     //La condition permet, par l’appel à la fonction connect_errno, de vérifier que la connexion a bien été établie.
     //Dans le cas contraire, l’exécution est arrêtée.
     if ($mysqli->connect_errno) {      
         exit('Problème de connexion à la BDD');
     }
    //echo 'Connexion réussie';
    
    // L’appel à session_start() active la session.
    session_start();

?>