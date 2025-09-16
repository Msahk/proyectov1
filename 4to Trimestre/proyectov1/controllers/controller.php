<?php

class MvcController{
	
	public function plantillaUser(){
		include "views/template.php";
	}
	
	public function enlacePaginasController(){
		if(isset($_GET["action"])){
			$enlacesController=$_GET["action"];
			$respuesta = EnlacesPaginas::enlacesPaginasModel($enlacesController);
			//$respuesta="views/modules/".$enlacesController.".php";
			include $respuesta;
		}
	}
}

?>