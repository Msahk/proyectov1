<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Carbon\Carbon;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

/**
 * Class Venta
 * 
 * @property int $idVentas
 * @property Carbon $fechaVenta
 * @property string|null $observacionVenta
 * @property string|null $estadoVenta
 * @property int $fkEmpleado
 * 
 * @property Empleado $empleado
 * @property Collection|Empanada[] $empanadas
 *
 * @package App\Models
 */
class Venta extends Model
{
	protected $table = 'ventas';
	protected $primaryKey = 'idVentas';
	public $incrementing = false;
	public $timestamps = false;

	protected $casts = [
		'idVentas' => 'int',
		'fechaVenta' => 'datetime',
		'fkEmpleado' => 'int'
	];

	protected $fillable = [
		'fechaVenta',
		'observacionVenta',
		'estadoVenta',
		'fkEmpleado'
	];

	public function empleado()
	{
		return $this->belongsTo(Empleado::class, 'fkEmpleado');
	}

	public function empanadas()
	{
		return $this->belongsToMany(Empanada::class, 'ventas_has_empanada', 'fkVentas', 'fkEmpanada')
					->withPivot('totalBruto', 'totalNeto');
	}
}
