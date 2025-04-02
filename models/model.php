<?php

class EnlacesPaginas {
    public static function enlacesPaginasModel($enlacemodel) {
        if (
            $enlacemodel == "produccion" ||
            $enlacemodel == "salir" ||
            $enlacemodel == "usuarios" ||
            $enlacemodel == "dashboard" ||
            $enlacemodel == "ingresar"
            )
            {
            
            $module = "views/modules/$enlacemodel.php";
            }
        else {
            header("location: views/modules/404.php");
        }

        return $module;
    }
}