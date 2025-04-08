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
        Schema::create('empanadas', function (Blueprint $table) {
            $table->integer('idEmpanada')->primary();
            $table->integer('empanadaValorUVenta');
            $table->integer('empanadaValorUProd');
            $table->integer('fkTipoEmpanada')->index('fktipoempanada');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('empanadas');
    }
};
