<?php

session_start();

$email = $_POST['emaill'];
$clave = $_POST['clavee'];

$_SESSION['email']=$email;

$conexion=mysqli_connect("localhost","root","","proyectopae");

$consulta="SELECT * FROM usuarios where email_usuario='$email' and password='$clave'";
$resultado=mysqli_query($conexion, $consulta);

$rowData = mysqli_fetch_array($resultado);



if($rowData["email_usuario"]==$email && $rowData["password"]==$clave){
        header("location:../../user.php");
}else{
    echo "incorrecto";
    }

?>
