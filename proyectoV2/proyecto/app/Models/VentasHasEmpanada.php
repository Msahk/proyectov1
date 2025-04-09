<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * Class VentasHasEmpanada
 * 
 * @property int $fkVentas
 * @property int $fkEmpanada
 * @property int $totalBruto
 * @property int $totalNeto
 * 
 * @property Venta $venta
 * @property Empanada $empanada
 *
 * @package App\Models
 */
class VentasHasEmpanada extends Model
{
	protected $table = 'ventas_has_empanada';
	public $incrementing = false;
	public $timestamps = false;

	protected $casts = [
		'fkVentas' => 'int',
		'fkEmpanada' => 'int',
		'totalBruto' => 'int',
		'totalNeto' => 'int'
	];

	protected $fillable = [
		'totalBruto',
		'totalNeto'
	];

	public function venta()
	{
		return $this->belongsTo(Venta::class, 'fkVentas');
	}

	public function empanada()
	{
		return $this->belongsTo(Empanada::class, 'fkEmpanada');
	}
}
