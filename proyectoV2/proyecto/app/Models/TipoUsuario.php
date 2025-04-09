<?php

/**
 * Created by Reliese Model.
 */

namespace App\Models;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

class TipoUsuario extends Model
{
	protected $table = 'tipo_usuario';
	protected $primaryKey = 'idTipoUsuario';
	
	protected $fillable = [
		'TipoUsuario'
	];
	public $timestamps = false;

	public function usuarios()
	{
		return $this->hasMany(Usuario::class, 'fkTipoUsuario');
	}
}
