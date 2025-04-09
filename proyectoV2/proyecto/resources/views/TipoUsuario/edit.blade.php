@extends('layouts.app')
@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-success">EDITAR TIPO USUARIO</h2>
                <a href="{{ route('TipoUsuario.index') }}" class="btn btn-secondary">Volver</a>
            </div>

            <div class="col-md-3">
                <form action="{{ route('TipoUsuario.update', $TipoUsuario) }}" method="POST">
                    @csrf
                    @method('PUT')
                    <label for="TipoUsuario" class="form-control-label">TIPO USUARIO</label>
                    <input type="text" class="form-control" id="TipoUsuario" name="TipoUsuario" value="{{ $TipoUsuario->TipoUsuario }}">
                    @error('TipoUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror
                    <br>
                    <button type="submit" class="btn btn-success">Actualizar</button>
                </form>
            </div>
        </div>
    </div>
@endsection