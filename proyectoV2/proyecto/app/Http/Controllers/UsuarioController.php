<?php

namespace App\Http\Controllers;

use App\Models\Usuario;
use App\Models\TipoUsuario;
use Illuminate\Http\Request;

class UsuarioController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $Usuario=Usuario::all();
        $TipoUsuario = TipoUsuario::all();
        return view('Usuario.index',['Usuario'=>$Usuario, 'TipoUsuario'=>$TipoUsuario]);
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        $TipoUsuario = TipoUsuario::all();
        return view('Usuario.create', ['TipoUsuario' => $TipoUsuario]);
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $request->validate([
            'idUsuario'=>'required|min:1|max:11|unique:usuarios',
            'nombreUsuario'=>'required|min:3|max:45',
            'apellidoUsuario'=>'required|min:3|max:45',
            'emailUsuario'=>'required|min:3|max:100|unique:usuarios',
        ]);
        Usuario::create($request->all());
        
        return redirect()->route('Usuario.index');
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
    public function edit(Usuario $Usuario)
    {
        $TipoUsuario = TipoUsuario::all();
        return view('Usuario.edit', ['Usuario'=>$Usuario, 'TipoUsuario'=>$TipoUsuario ]);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Usuario $Usuario)
    {
        $request->validate([
            'idUsuario'=>'required|min:1|max:11',
            'nombreUsuario'=>'required|min:3|max:45',
            'apellidoUsuario'=>'required|min:3|max:45',
            'emailUsuario'=>'required|min:3|max:100',
        ]);
        $Usuario->update($request->all());
        return redirect()->route('Usuario.index');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Request $request, Usuario $Usuario)
    {
        $Usuario->delete();

        return redirect()->route('Usuario.index');
    }
}
