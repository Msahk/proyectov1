<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

/**
 * Class Produccion
 * 
 * @property int $idProduccion
 * @property int $cantidadProduccion
 * @property int $fkEmpleado
 * 
 * @property Empleado $empleado
 * @property Collection|Empanada[] $empanadas
 *
 * @package App\Models
 */
class Produccion extends Model
{
	protected $table = 'produccion';
	protected $primaryKey = 'idProduccion';
	public $incrementing = false;
	public $timestamps = false;

	protected $casts = [
		'idProduccion' => 'int',
		'cantidadProduccion' => 'int',
		'fkEmpleado' => 'int'
	];

	protected $fillable = [
		'cantidadProduccion',
		'fkEmpleado'
	];

	public function empleado()
	{
		return $this->belongsTo(Empleado::class, 'fkEmpleado');
	}

	public function empanadas()
	{
		return $this->belongsToMany(Empanada::class, 'produccion_empanada', 'fkProduccion', 'fkEmpanada')
					->withPivot('fechaProd', 'fechaVencimiento');
	}
}
