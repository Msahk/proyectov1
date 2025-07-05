<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>El Vecino Alegre</title>
  
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css" />
  
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
</head>
<body>

<header class="header">
  <a href="#" class="logo">
    <img src="${pageContext.request.contextPath}/assets/img/logo2.jpg" alt="Coffee Logo" />
  </a>
  
  <nav class="navbar">
    <a href="#">Inicio</a>
    <a href="#">Nosotros</a>
    <a href="#">Menú</a>
    <a href="#">Productos</a>
    <a href="#">Reseñas</a>
    <a href="#">Contacto</a>
    <a href="#">Blogs</a>
  </nav>
  
  <div class="icons">
    <div class="fas fa-search" id="search-btn"></div>
    <div class="fas fa-shopping-cart" id="cart-btn"></div>
    <a href="${pageContext.request.contextPath}/views/formularios/login.jsp">
      <div class="fas fa-user" id="profile-btn"></div>
    </a>
  </div>
</header>

<section class="home" id="home">
  <div class="content">
    <h1>TIENDA <br> "EL VECINO AMIGO"</h1>
    <p>Encuentra Todos Los Productos Que Necesitas Al Mejor Precio Y Con La Calidad Que Mereces. En Nuestra Tienda, Trabajamos Para Ofrecerte Siempre Lo Mejor, Cerca De Ti Y Con Atención Amigable.</p>
    <a href="#" class="btn">Comprar ahora</a>
  </div>
</section>

</body>
</html>
