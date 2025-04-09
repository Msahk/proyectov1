@extends('layouts.app')
@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-success">AGREGAR USUARIO</h2>
                <a href="{{ route('Usuario.index') }}" class="btn btn-secondary">Volver</a>
            </div>

            <div class="col-md-3">
                <form action="{{ route('Usuario.store') }}" method="POST">
                    @csrf
                    <label for="idUsuario" class="form-control-label">idUsuario</label>
                    <input type="number" class="form-control" id="idUsuario" name="idUsuario" value="">
                    @error('idUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="nombreUsuario" class="form-control-label">Nombre</label>
                    <input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" value="">
                    @error('nombreUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="apellidoUsuario" class="form-control-label">Apellido</label>
                    <input type="text" class="form-control" id="apellidoUsuario" name="apellidoUsuario" value="">
                    @error('apellidoUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="emailUsuario" class="form-control-label">Correo</label>
                    <input type="email" class="form-control" id="emailUsuario" name="emailUsuario" value="">
                    @error('emailUsuario')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="password" class="form-control-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" value="">

                    <input type="hidden" class="form-control" id="" name="estadoUsuario" value="Activo">

                    <label for="fkTipoUsuario">Tipo Usuario</label>
                    <select name="fkTipoUsuario" id="tipoUsuario" class="form-select">
                            @foreach($TipoUsuario as $item)
                                <option value="{{$item->idTipoUsuario}}">{{$item->TipoUsuario}}</option>
                            @endforeach
                        </select>

                    
                    <br>
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </form>
            </div>
        </div>
    </div>
@endsection