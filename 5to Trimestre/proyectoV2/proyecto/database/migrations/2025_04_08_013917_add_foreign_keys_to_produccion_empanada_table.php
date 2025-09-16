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
        Schema::table('produccion_empanada', function (Blueprint $table) {
            $table->foreign(['fkEmpanada'], 'produccion_empanada_ibfk_1')->references(['idEmpanada'])->on('empanadas')->onUpdate('restrict')->onDelete('restrict');
            $table->foreign(['fkProduccion'], 'produccion_empanada_ibfk_2')->references(['idProduccion'])->on('produccion')->onUpdate('restrict')->onDelete('restrict');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('produccion_empanada', function (Blueprint $table) {
            $table->dropForeign('produccion_empanada_ibfk_1');
            $table->dropForeign('produccion_empanada_ibfk_2');
        });
    }
};
