<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Carbon\Carbon;
use Illuminate\Database\Eloquent\Model;

/**
 * Class ProduccionEmpanada
 * 
 * @property int $fkEmpanada
 * @property int $fkProduccion
 * @property Carbon $fechaProd
 * @property Carbon $fechaVencimiento
 * 
 * @property Empanada $empanada
 * @property Produccion $produccion
 *
 * @package App\Models
 */
class ProduccionEmpanada extends Model
{
	protected $table = 'produccion_empanada';
	public $incrementing = false;
	public $timestamps = false;

	protected $casts = [
		'fkEmpanada' => 'int',
		'fkProduccion' => 'int',
		'fechaProd' => 'datetime',
		'fechaVencimiento' => 'datetime'
	];

	protected $fillable = [
		'fechaProd',
		'fechaVencimiento'
	];

	public function empanada()
	{
		return $this->belongsTo(Empanada::class, 'fkEmpanada');
	}

	public function produccion()
	{
		return $this->belongsTo(Produccion::class, 'fkProduccion');
	}
}
