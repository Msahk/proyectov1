<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

/**
 * Class Empanada
 * 
 * @property int $idEmpanada
 * @property int $empanadaValorUVenta
 * @property int $empanadaValorUProd
 * @property int $fkTipoEmpanada
 * 
 * @property TipoEmpanada $tipo_empanada
 * @property Collection|Produccion[] $produccions
 * @property Collection|Venta[] $ventas
 *
 * @package App\Models
 */
class Empanada extends Model
{
	protected $table = 'empanadas';
	protected $primaryKey = 'idEmpanada';
	public $incrementing = false;
	public $timestamps = false;

	protected $casts = [
		'idEmpanada' => 'int',
		'empanadaValorUVenta' => 'int',
		'empanadaValorUProd' => 'int',
		'fkTipoEmpanada' => 'int'
	];

	protected $fillable = [
		'empanadaValorUVenta',
		'empanadaValorUProd',
		'fkTipoEmpanada'
	];

	public function tipo_empanada()
	{
		return $this->belongsTo(TipoEmpanada::class, 'fkTipoEmpanada');
	}

	public function produccions()
	{
		return $this->belongsToMany(Produccion::class, 'produccion_empanada', 'fkEmpanada', 'fkProduccion')
					->withPivot('fechaProd', 'fechaVencimiento');
	}

	public function ventas()
	{
		return $this->belongsToMany(Venta::class, 'ventas_has_empanada', 'fkEmpanada', 'fkVentas')
					->withPivot('totalBruto', 'totalNeto');
	}
}
