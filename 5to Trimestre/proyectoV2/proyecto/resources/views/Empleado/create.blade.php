@extends('layouts.app')
@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-success">AGREGAR EMPLEADO</h2>
                <a href="{{ route('Empleado.index') }}" class="btn btn-secondary">Volver</a>
            </div>

            <div class="col-md-3">
                <form action="{{ route('Empleado.store') }}" method="POST">
                    @csrf
                    <label for="Empleado" class="form-control-label">EMPLEADO</label>
                    <input type="text" class="form-control" id="tipoEmpleado" name="tipoEmpleado" value="">
                    @error('tipoEmpleado')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror

                    <label for="fkUsuario">Selecciona el usuario</label>
                    <select name="fkUsuario" id="Usuario" class="form-select">
                            @foreach($Usuario as $item)
                                <option value="{{$item->idUsuario}}">{{$item->nombreUsuario}}</option>
                            @endforeach
                        </select>

                    <br>


                    <button type="submit" class="btn btn-primary">Guardar</button>
                </form>
            </div>
        </div>
    </div>
@endsection