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
        <h2>Crear usuario</h2>
        <h3></h3>
        <form class="form">
            <div class="caja">
                <input type="number" name="doc" placeholder="">
                <label for="doc">Documento</label>
            </div>
            <div class="caja">
                <input type="text" name="name" placeholder="">
                <label for="name">Nombre</label>
            </div>
            <div class="caja">
                <input type="text" name="email" id="" placeholder="">
                <label for="email">Correo</label>
            </div>
            <div class="caja">
                <input type="password" name="password" id="password" placeholder="">
                <label for="password">Contraseña</label>
            </div>
            <div class="check-results">
                <span id="mayuscula" class="invalid"><i class="fa-solid fa-shield"></i>Letra mayúscula</span>
                <span id="minuscula" class="invalid"><i class="fa-solid fa-shield"></i>Letra minúscula</span>
                <span id="numero" class="invalid"><i class="fa-solid fa-shield"></i>Tener un número</span>
                <span id="simbolo" class="invalid"><i class="fa-solid fa-shield"></i>Tener un símbolo</span>
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
