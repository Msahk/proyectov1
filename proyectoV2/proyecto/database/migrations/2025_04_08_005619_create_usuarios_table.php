<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('usuarios', function (Blueprint $table) {
            $table->integer('idUsuario')->primary();
            $table->string('nombreUsuarios', 45);
            $table->string('apellidoUsuario', 45);
            $table->string('estadoUsuario', 8);
            $table->string('password', 50);
            $table->string('emailUsuario', 100);
            $table->integer('fkTipoUsuario')->index('fktipousuario');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('usuarios');
    }
};
