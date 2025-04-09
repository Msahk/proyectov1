<?php

use App\Http\Controllers\TipoUsuarioController;
use App\Http\Controllers\UsuarioController;
use App\Http\Controllers\EmpleadoController;
use Illuminate\Support\Facades\Route;

/* Route::get('/', function () {
    return view('TipoUsuario.index');
    
}); */

Auth::routes();
Route::get('/', function () {
    return view('welcome');
});


Route::resource('TipoUsuario',TipoUsuarioController::class);
Route::resource('Usuario',UsuarioController::class);
Route::resource('Empleado',EmpleadoController::class);


Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');
