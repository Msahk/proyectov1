@extends ('layouts.app')
@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2 class="text-success">CRUD TIPO USUARIO</h2>
            <a href="{{ route('Usuario.create') }}" class="btn btn-primary">Agregar</a>
        </div>
    </div>


    <div class="col-md-10">
        <table class="table table-bordered">
            <thead>
                <tr class="text-center">
                    <th>Id</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Password</th>
                    <th>Email</th>
                    <th>Estado</th>
                    <th>Opciones</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($Usuario as $item)
                    <tr>
                        <td>{{ $item->idUsuario }}</td>
                        <td>{{ $item->nombreUsuario }}</td>
                        <td>{{ $item->apellidoUsuario }}</td>
                        <td>{{ $item->password }}</td>
                        <td>{{ $item->emailUsuario }}</td>
                        <td>{{ $item->estadoUsuario }}</td>
                        <td>{{ $item->tipo_usuario->TipoUsuario }}</td>
                        <td class="text-center">
                            <a href="{{ route('Usuario.edit', $item->idUsuario) }}" class="btn btn-success">Editar</a>
                            <form action="{{ route('Usuario.destroy', $item->idUsuario) }}" method="POST" class="d-inline-block">
                                @csrf
                                @method('DELETE')
                                <button class="btn btn-danger" onclick="return confirm('Esta seguro de eliminar {{ $item->nombreUsuario }}?')">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                @endforeach
            </tbody>
        </table>
    </div>

</div>
@endsection


