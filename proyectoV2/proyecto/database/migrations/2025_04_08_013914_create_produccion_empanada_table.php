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
        Schema::create('produccion_empanada', function (Blueprint $table) {
            $table->integer('fkEmpanada');
            $table->integer('fkProduccion')->index('fkproduccion');
            $table->date('fechaProd');
            $table->date('fechaVencimiento');

            $table->primary(['fkEmpanada', 'fkProduccion']);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('produccion_empanada');
    }
};
