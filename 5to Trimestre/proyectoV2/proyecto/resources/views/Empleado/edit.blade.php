@extends('layouts.app')
@section('content')
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-success">EDITAR EMPLEADO</h2>
                <a href="{{ route('Empleado.index') }}" class="btn btn-secondary">Volver</a>
            </div>

            <div class="col-md-3">
                <form action="{{ route('Empleado.update', $Empleado) }}" method="POST">
                    @csrf
                    @method('PUT')
                    <label for="Empleado" class="form-control-label">EMPLEADO</label>
                    <input type="text" class="form-control" id="Empleado" name="tipoEmpleado" value="{{ $Empleado->tipoEmpleado }}">
                    @error('tipoEmpleado')
                    <small class="text-danger">{{ $message }}</small>
                    @enderror
                    <br>
                    <button type="submit" class="btn btn-success">Actualizar</button>
                </form>
            </div>
        </div>
    </div>
@endsection