<?php

//conexion con el archivo que conecta a la base de datos
require_once "conexion.php";

//Clase usuarios heredando clase conexion
class Usuarios extends Conexion {
    

    //funcion para obtener los registros de las tablas
    public static function vistaUsuariosModel($tabla) {
        //Se genera la conexion a la base de datos y luego la consulta
        $stmt = Conexion::conectar()->prepare("SELECT * from usuarios INNER JOIN tipo_usuario ON fkTipoUsuario = idTipoUsuario");

        //Se ejecuta la consulta ya definida en la variable
        $stmt-> execute();

        //con fetchAll retorna el resultado de la consulta previamente ejecutada
        return $stmt->fetchAll();
    }
}