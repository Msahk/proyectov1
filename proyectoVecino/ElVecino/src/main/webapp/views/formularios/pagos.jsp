<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>El vecino</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleForm.css">
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
</head>
<body>
    <div class="login formu">
        <h2>Agregar pago</h2>
        <h3></h3>
        <form class="form">
            <div class="caja">
                <input type="number" name="numero_cuota" placeholder="">
                <label for="numero_cuota">Numero de cuotas</label>
            </div>
            <div class="caja">
                <input type="number" name="monto_pagado" placeholder="">
                <label for="monto_pagado">Monto pagado</label>
            </div>
            <div class="caja">
                <input type="date" name="fechaPago" id="">
                <label for="fechaPago">Fecha de pago</label>
            </div>
            <div class="caja">
                <input type="text" placeholder="">
                <label for="">Tipo de pago</label>
            </div>
            <div class="caja">
                <input type="number" placeholder="">
                <label for="">Valor pagado</label>
            </div>
            <div class="caja">
                <input type="text" placeholder="">
                <label for="">Observaciones</label>
            </div>
            <button type="submit">
                <p>Guardar</p>
                <span class="material-symbols-outlined">arrow_forward</span>
            </button>
        </form>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
    <script src="https://kit.fontawesome.com/2b530c1d65.js" crossorigin="anonymous"></script>
</body>
</html>
