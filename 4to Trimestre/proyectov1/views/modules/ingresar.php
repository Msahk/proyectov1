<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../../assets/css/estilos.css">
</head>
<body>
    <header class="encabezado">
        <img src="../../assets/img/Logo 1 sin fondo.png" alt="Logo de la empresa" class="logo">
        <a href="#" class="logo-text">Peguele a la Empanada</a>
        
    
    <nav class="lista">
        <a href="../../index.php">Inicio</a>
    </nav>
</header>
    <section id="formularios" class="formu">
        <div class="listaformu">
            <form action="" method="post" class="form">
                <h3>Iniciar sesion</h3>
                <label for="email">Correo:</label>
                <input type="email" id="email-sesion" name="email" required>
             <label for="password-sesion">Contraseña:</label>
             <input type="password" id="password-sesion" name="password" required>
             <input type="submit" class="botonini" onclick="return $resultado" value="Ingresar">
            </form>
        </div>
        </section>
</body>
</html>

<?php

require_once '../../controllers/ingresarController.php';

?>