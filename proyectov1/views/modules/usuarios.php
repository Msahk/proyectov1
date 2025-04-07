

<body>
    <div class="contenedor">
        <section>
            <h1>TABLA USUARIOS</h1>
            <div class="contenedor-tabla">

                <table>
                    <thead>
                        <tr>
                            <th>Id<span class="ajustable" onmousedown="initResize(0)"></span></th>
                            <th>Nombre<span class="ajustable" onmousedown="initResize(0)"></span></th>
                            <th>Apellido<span class="ajustable" onmousedown="initResize(0)"></span></th>
                            <th>Correo<span class="ajustable" onmousedown="initResize(0)"></span></th>
                            <th>Contraseña<span class="ajustable" onmousedown="initResize(0)"></span></th>
                            <th>Tipo Usuario (FK)<span class="ajustable" onmousedown="initResize(0)"></span></th>
<th>Estado Usuario<span class="ajustable" onmousedown="initResize(0)"></span></th>
                        </tr>
                    </thead>
                    <tbody>
                            <?php
                            $consUsuario = new Usuario();
                            $consUsuario -> vistaUsuariosController();
                            ?>
                    </tbody>
                </table>
            </div>
            
        </section>
    </div>
    </div>



</body>

</html>