<?php

//Iniciamos session
session_start();

//Mediante require_once se incluye codigo de otros arhivos

    require_once "controllers/controller.php";
    require_once "models/model.php";

    //se inluye controladores por tablas
    require_once "controllers/produccionController.php";
    require_once "controllers/usuariosController.php";



    //se incluye modelos por tablas
    require_once "models/produccionModel.php";
    require_once "models/usuariosModel.php";



$vista = new MvcController();
$vista -> plantillaUser();