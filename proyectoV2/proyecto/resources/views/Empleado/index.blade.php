@extends ('layouts.app')
@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2 class="text-success">CRUD EMPLEADO</h2>
            <a href="{{ route('Empleado.create') }}" class="btn btn-primary">Agregar</a>
        </div>
    </div>

    <div class="col-md-8">
        <table class="table table-bordered">
            <thead>
                <tr class="text-center">
                    <th>Id</th>
                    <th>Tipo</th>
                    <th>Usuario (FK)</th>
                    <th>Opciones</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($Empleado as $item)
                    <tr>
                        <td>{{ $item->idEmpleado }}</td>
                        <td>{{ $item->tipoEmpleado }}</td>
                        <td>{{ $item->Usuario->nombreUsuario }}</td>
                        <td class="text-center">
                            <a href="{{ route('Empleado.edit', $item->idEmpleado) }}" class="btn btn-success">Editar</a>
                            <form action="{{ route('Empleado.destroy', $item->idEmpleado) }}" method="POST" class="d-inline-block">
                                @csrf
                                @method('DELETE')
                                <button class="btn btn-danger" onclick="return confirm('¿Está seguro de eliminar {{ $item->tipoEmpleado }}?')">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                @endforeach
            </tbody>
        </table>
    </div>

</div>
@endsection

