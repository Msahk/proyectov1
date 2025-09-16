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
        Schema::table('ventas_has_empanada', function (Blueprint $table) {
            $table->foreign(['fkVentas'], 'ventas_has_empanada_ibfk_1')->references(['idVentas'])->on('ventas')->onUpdate('restrict')->onDelete('restrict');
            $table->foreign(['fkEmpanada'], 'ventas_has_empanada_ibfk_2')->references(['idEmpanada'])->on('empanadas')->onUpdate('restrict')->onDelete('restrict');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('ventas_has_empanada', function (Blueprint $table) {
            $table->dropForeign('ventas_has_empanada_ibfk_1');
            $table->dropForeign('ventas_has_empanada_ibfk_2');
        });
    }
};
