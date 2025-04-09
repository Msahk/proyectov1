<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

class Usuario extends Model
{
	protected $table = 'usuarios';
	protected $primaryKey = 'idUsuario';
	
	protected $fillable = [
		'idUsuario',
		'nombreUsuario',
		'apellidoUsuario',
		'emailUsuario',
		'password',
		'estadoUsuario',
		'fkTipoUsuario'
	];
	
	public $timestamps = false;
	public function tipo_usuario()
	{
		return $this->belongsTo(TipoUsuario::class, 'fkTipoUsuario');
	}

	public function empleados()
	{
		return $this->hasMany(Empleado::class, 'fkUsuario');
	}
}
