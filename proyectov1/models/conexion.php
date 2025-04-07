<?php
class Conexion {
    //GENERA UNA FUNCION LA CUAL HACE LA CONEXION A LA BASE DE DATOS
    public static function conectar() {
        $link = new PDO("mysql:host=localhost;dbname=proyectopaev2", "root","");
        return $link;
    }
}