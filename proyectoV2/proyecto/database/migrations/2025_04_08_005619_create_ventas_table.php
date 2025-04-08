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
        Schema::create('ventas', function (Blueprint $table) {
            $table->integer('idVentas')->primary();
            $table->date('fechaVenta');
            $table->string('observacionVenta', 45)->nullable();
            $table->string('estadoVenta', 15)->nullable();
            $table->integer('fkEmpleado')->index('fkempleado');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('ventas');
    }
};
