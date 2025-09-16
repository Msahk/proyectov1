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
        Schema::create('ventas_has_empanada', function (Blueprint $table) {
            $table->integer('fkVentas');
            $table->integer('fkEmpanada')->index('fkempanada');
            $table->integer('totalBruto');
            $table->integer('totalNeto');

            $table->primary(['fkVentas', 'fkEmpanada']);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('ventas_has_empanada');
    }
};
