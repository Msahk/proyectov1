@extends('layouts.app')
@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-success">AGREGAR USUARIO</h2>
                <a href="{{ route('Usuario.index') }}" class="btn btn-secondary">Volver</a>
            </div>

            <div class="col-md-3">
                <form action="{{ route('Usuario.update', $Usuario) }}" method="POST">
                    @csrf
                    @method('PUT')
                    <label for="idUsuario" class="form-control-label">idUsuario</label>
                    <input type="number" class="form-control" id="idUsuario" name="idUsuario" value="{{ $Usuario->idUsuario }}">
                    @error('idUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="nombreUsuario" class="form-control-label">Nombre</label>
                    <input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" value="{{ $Usuario->nombreUsuario }}">
                    @error('nombreUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="apellidoUsuario" class="form-control-label">Apellido</label>
                    <input type="text" class="form-control" id="apellidoUsuario" name="apellidoUsuario" value="{{ $Usuario->apellidoUsuario }}">
                    @error('apellidoUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="emailUsuario" class="form-control-label">Correo</label>
                    <input type="email" class="form-control" id="emailUsuario" name="emailUsuario" value="{{ $Usuario->emailUsuario }}">
                    @error('emailUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="password" class="form-control-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" value="{{ $Usuario->password }}">

                    <input type="hidden" class="form-control" id="" name="estadoUsuario" value="Activo">

                    <label for="fkTipoUsuario">Tipo Usuario</label>

                        <select name="fkTipoUsuario" id="fkTipoUsuario" class="form-select" value="{{ $Usuario->fkTipoUsuario }}">
                            @foreach($TipoUsuario as $item)
                            <option value="{{ $item->idTipoUsuario }}"
                            {{ $Usuario->fkTipoUsuario == $item->idTipoUsuario ? 'selected' : '' }}>{{ $item->TipoUsuario }}</option>
                            @endforeach
                        </select>

                    
                    <br>
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </form>
            </div>
        </div>
    </div>
@endsection