<?php
ob_start();

class Usuario{
	public function	vistaUsuariosController(){
		$respuesta = Usuarios::vistaUsuariosModel("USUARIO");
		// foreach: recorrer arreglos
		foreach ($respuesta as $item ) {
			echo '<tr>
			<td><span class="contenido-celda">'.$item["idUsuario"].'</td>
			<td><span class="contenido-celda">'.$item["nombreUsuarios"].'</td>
			<td><span class="contenido-celda">'.$item["apellidoUsuario"].'</td>
			<td><span class="contenido-celda">'.$item["emailUsuario"].'</td>
			<td><span class="contenido-celda">'.$item["password"].'</td>
			<td><span class="contenido-celda">'.$item["tipoUsuario"].'</td>
<td><span class="contenido-celda">'.$item["estadoUsuario"].'</td></tr>';
		}
	}
}