<?php

session_start();

$email = $_POST['emaill'];
$clave = $_POST['clavee'];

$_SESSION['email']=$email;

$conexion=mysqli_connect("localhost","root","","proyectopaev2");

$consulta="SELECT * FROM usuarios where emailUsuario='$email' and password='$clave'";
$resultado=mysqli_query($conexion, $consulta);

$rowData = mysqli_fetch_array($resultado);



if($rowData["emailUsuario"]==$email && $rowData["password"]==$clave){
        header("location:../../user.php");
}else{
    echo "incorrecto";
    }

?>
