<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f2f2;
        padding: 20px;
    }

    h1 {
        text-align: center;
        color: #333;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    th, td {
        border: 1px solid #ddd;
        padding: 12px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    tr:nth-child(even) {
        background-color: #f9f9f9;
    }

    tr:hover {
        background-color: #e9e9e9;
    }

</style>

<h1>Listado de Usuarios</h1>
<table class="table">
            <thead>
                <tr class="text-center">
                    <th scope="col">Id</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Apellido</th>
                    <th scope="col">Email</th>
                    <th scope="col">Estado</th>
                    <th scope="col">Tipo</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($Usuario as $item)
                    <tr>
                        <td>{{ $item->idUsuario }}</td>
                        <td>{{ $item->nombreUsuario }}</td>
                        <td>{{ $item->apellidoUsuario }}</td>
                        <td>{{ $item->emailUsuario }}</td>
                        <td>{{ $item->estadoUsuario }}</td>
                        <td>{{ $item->tipo_usuario->TipoUsuario }}</td>
                    </tr>
                @endforeach
            </tbody>
        </table>