
// Script.js

// Activar menú responsive (si tuvieras un botón tipo #menu-btn)
document.addEventListener('DOMContentLoaded', () => {
  const menuBtn = document.getElementById('menu-btn');
  const navbar = document.querySelector('.navbar');

  if (menuBtn) {
    menuBtn.addEventListener('click', () => {
      navbar.classList.toggle('active');
    });
  }

  // Animación scroll suave
  document.querySelectorAll('a[href^="#"]').forEach(link => {
    link.addEventListener('click', function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        target.scrollIntoView({
          behavior: 'smooth'
        });
      }
    });
  });
});
