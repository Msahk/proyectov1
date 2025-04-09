<?php

namespace App\Http\Controllers;

use App\Models\Empleado;
use App\Models\Usuario;
use Illuminate\Http\Request;

class EmpleadoController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $Empleado = Empleado::all();
        $Usuario = Usuario::all();
        return view('Empleado.index',['Empleado'=> $Empleado, 'Usuario'=>$Usuario]);
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        $Usuario = Usuario::all();
        return view('Empleado.create', ['Usuario'=>$Usuario]);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $request->validate([
            'tipoEmpleado'=>'required|min:3|max:30'
        ]);
        Empleado::create($request->all());

    
        return redirect()->route('Empleado.index');
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Empleado $Empleado)
    {
        $Usuario = Usuario::all();
        return view('Empleado.edit',['Empleado'=> $Empleado, 'Usuario'=>$Usuario]);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Empleado $Empleado)
    {
        $request->validate([
            'tipoEmpleado' => 'required|min:3|max:30'

        ]);

        $Empleado->update($request->all());
        return redirect()->route('Empleado.index');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Request $request, Empleado $Empleado)
    {
        $Empleado->delete();

        return redirect()->route('Empleado.index');
    }
}
