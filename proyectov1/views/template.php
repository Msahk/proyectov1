<?php


/* 
$usuarios = "SELECT * FROM usuarios";
$consultaUsuarios = mysqli_query($conexion, $usuarios);

$tipousuario = "SELECT * FROM tipo_usuario";
$consultaTipoUsuarios = mysqli_query($conexion, $tipousuario);

$tipousuario = "SELECT * FROM tipo_usuario";
$consultaTipoUsuarios = mysqli_query($conexion, $tipousuario);

$admin = "SELECT * FROM administradores";
$consultaAdmin = mysqli_query($conexion, $admin); */





?>

<!DOCTYPE html>
<html lang="es">

<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
        rel="stylesheet" />

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet" href="assets/css/styles.css">
    <?php
    include_once "modules/navegacion.php";

    $mvc = new MvcController();
    $mvc->enlacePaginasController();

    ?>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
</head>

<body>
    
    </div>



</body>

</html>