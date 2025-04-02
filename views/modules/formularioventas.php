<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../assets/css/estilos.css">
    <script src="js/script.js"></script>
    <title>Formulario ventas</title>
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
<form action="#" method="post" class="form" id="formulario-ventas">
    <h3>Registro de Ventas</h3>
   
    <label for="fecha-venta">Fecha de Venta:</label>
    <input type="date" id="fecha-venta" name="fecha-venta" required>
    <div class="seleccion">
    <label for="estado-venta">Estado de Venta:</label>
    <select id="estado-venta" name="estado-venta" required>
        <option value="">Seleccione un estado</option>
        <option value="venta-rapida">Venta Rápida</option>
        <option value="venta-directa">Venta Directa</option>
        <option value="venta-cancelada">Venta Cancelada</option>
        <option value="venta-mayorista">Venta Mayorista</option>
    </select>
</div>
    <label for="observacion-venta">Observación de Venta:</label>
    <input type="text" id="observacion-venta" name="observacion-venta" required>

    <label for="total-venta">Total de Venta:</label>
    <input type="number" id="total-venta" name="total-venta" required>
    
     
    <div class="form-group">
        <label for="empanada-seleccionada">Empanada:</label>
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
</select> 
    <button type="submit" class="botonini">Registrar</button> 
</form>

<footer class="footer">
    <p>&copy; 2024 Péguele a la Empanada. Todos los derechos reservados.</p>
</footer>
                                        
</body>
</html>