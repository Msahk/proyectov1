<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../assets/css/estilos.css">
    <script src="js/script.js"></script>
    <title>Formulario produccion</title>
</head>
<body>
    <header class="encabezado">
        <img src="../../assets/img/Logo 1 sin fondo.png" alt="Logo de la empresa" class="logo">
        <a href="#" class="logo-text">Peguele a la Empanada</a>
        
    
    <nav class="lista">
        <a href="index.html">Inicio</a>
        <a href="#sobre-nosotros">Sobre Nosotros</a>
        <a href="#contacto">Contacto</a>
        <a href="#formularios">Ingresar</a>
    </nav>    
    </header>
    <form action="#" method="post" class="form" id="formulario-produccion">
        <h3>Registro de Produccion</h3>
        <label for="cantidad-producida">Cantidad Producida:</label>
        <input type="number" id="cantidad-producida" name="cantidad-producida" required>

        <label for="fecha-produccion">Fecha de Producción:</label>
        <input type="date" id="fecha-produccion" name="fecha-produccion" required>

        <label for="fecha-vencimiento">Fecha de Vencimiento:</label>
        <input type="date" id="fecha-vencimiento" name="fecha-vencimiento" required>

        <div class="form-group">
        <label for="tipo-empanada">Tipo de Empanada:</label>
       
<div class="seleccion">
            <select id="empanada-seleccionada" name="empanada-seleccionada" required>
                <option value="">Seleccione una empanada</option>
                <option value="pollo">Pollo</option>
                <option value="carne">Carne</option>
                <option value="queso">Queso</option>
                <option value="lechona">Lechona</option>
                <option value="mixta">Mixta</option>
            </select>
            <div class="select-arrow">&#9660;</div>
        </div>
    </div>
       
        <button type="submit" class="botonini">Registrar</button>
        </form>
        <footer class="footer">
            <p>&copy; 2024 Péguele a la Empanada. Todos los derechos reservados.</p>
            </footer>
</body>
</html>