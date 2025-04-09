<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

/**
 * Class TipoEmpanada
 * 
 * @property int $idTipoEmpanada
 * @property string $tipoEmpanada
 * 
 * @property Collection|Empanada[] $empanadas
 *
 * @package App\Models
 */
class TipoEmpanada extends Model
{
	protected $table = 'tipo_empanada';
	protected $primaryKey = 'idTipoEmpanada';
	public $incrementing = false;
	public $timestamps = false;

	protected $casts = [
		'idTipoEmpanada' => 'int'
	];

	protected $fillable = [
		'tipoEmpanada'
	];

	public function empanadas()
	{
		return $this->hasMany(Empanada::class, 'fkTipoEmpanada');
	}
}
