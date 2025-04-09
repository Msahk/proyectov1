<?php

namespace App\Http\Controllers;

use App\Models\TipoUsuario;
use Illuminate\Http\Request;

class TipoUsuarioController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $TipoUsuario=TipoUsuario::all();
        return view('TipoUsuario.index',['TipoUsuario'=>$TipoUsuario]);
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        return view('TipoUsuario.create');
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $request->validate([
            'TipoUsuario'=>'required|min:3|max:30|unique:tipo_usuario'
        ]);
        TipoUsuario::create($request->all());

    
        return redirect()->route('TipoUsuario.index');
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
    public function edit(TipoUsuario $TipoUsuario)
    {
        return view('TipoUsuario.edit', ['TipoUsuario' => $TipoUsuario]);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, TipoUsuario $TipoUsuario)
    {
        $request->validate([
            'TipoUsuario'=>'required|min:3|max:30|unique:tipo_usuario'
        ]);

        $TipoUsuario->update($request->all());
        return redirect()->route('TipoUsuario.index');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Request $request, TipoUsuario $TipoUsuario)
    {
        $TipoUsuario->delete();

        return redirect()->route('TipoUsuario.index');
    }
}
