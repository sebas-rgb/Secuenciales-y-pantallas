package org.example.testfinalomemato;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "recoverServlet", value = "/recover-servlet")
public class RecoverServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");

        // Ruta del archivo
        String filePath = getServletContext().getRealPath("/WEB-INF/usuarios.txt");
        File file = new File(filePath);
        String password = null;

        // Verificar si el correo electrónico existe y obtener la contraseña
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("Email: " + email + ",")) {
                        password = line.split("Password: ")[1].split(",")[0];
                        break;
                    }
                }
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (password != null) {
            out.println("<h1>Su contraseña es: " + password + "</h1>");
        } else {
            out.println("<h1>Correo electrónico no registrado.</h1>");
        }

        out.println("</body></html>");
    }
}