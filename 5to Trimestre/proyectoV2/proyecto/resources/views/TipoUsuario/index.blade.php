@extends ('layouts.app')
@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2 class="text-success">CRUD TIPO USUARIO</h2>
            <a href="{{ route('TipoUsuario.create') }}" class="btn btn-primary">Agregar</a>
        </div>
    </div>


    <div class="col-md-8">
        <table class="table table-bordered">
            <thead>
                <tr class="text-center">
                    <th>Id</th>
                    <th>Tipo</th>
                    <th>Opciones</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($TipoUsuario as $tipo)
                    <tr>
                        <td>{{ $tipo->idTipoUsuario }}</td>
                        <td>{{ $tipo->TipoUsuario }}</td>
                        <td class="text-center">
                            <a href="{{ route('TipoUsuario.edit', $tipo->idTipoUsuario) }}" class="btn btn-success">Editar</a>
                            <form action="{{ route('TipoUsuario.destroy', $tipo->idTipoUsuario) }}" method="POST" class="d-inline-block">
                                @csrf
                                @method('DELETE')
                                <button class="btn btn-danger" onclick="return confirm('Esta seguro de eliminar {{ $tipo->TipoUsuario }}?')">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                @endforeach
            </tbody>
        </table>
    </div>

</div>
@endsection


