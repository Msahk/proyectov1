<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

class Empleado extends Model
{
	protected $table = 'empleados';
	protected $primaryKey = 'idEmpleado';
	protected $fillable = [
		'tipoEmpleado',
		'fkUsuario'
	];
	public $timestamps = false;

	public function usuario()
	{
		return $this->belongsTo(Usuario::class, 'fkUsuario');
	}

	public function produccions()
	{
		return $this->hasMany(Produccion::class, 'fkEmpleado');
	}

	public function ventas()
	{
		return $this->hasMany(Venta::class, 'fkEmpleado');
	}
}
