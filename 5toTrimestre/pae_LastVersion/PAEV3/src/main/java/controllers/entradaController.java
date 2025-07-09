package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;
import models.entrada;
import models.entradaDao;

@WebServlet("/entradaController")
public class entradaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id_ins = Integer.parseInt(request.getParameter("id_ins"));

            List<entrada> lista = entradaDao.obtenerPorInsumo(id_ins);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Convertimos la lista a JSON con formato de fecha legible
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String json = gson.toJson(lista);

            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Parámetro inválido o error interno.\"}");
        }
    }
}
