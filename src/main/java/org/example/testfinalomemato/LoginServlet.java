package org.example.testfinalomemato;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String usernameOrEmail = request.getParameter("username");
        String password = request.getParameter("password");

        // Ruta del archivo
        String filePath = getServletContext().getRealPath("/WEB-INF/usuarios.txt");
        File file = new File(filePath);

        boolean isValidUser = false;
        String email = null;
        String profileImage = null;

        // Leer la información del usuario del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(", ");
                String fileUsername = userDetails[0].split(": ")[1];
                String fileEmail = userDetails[1].split(": ")[1];
                String filePassword = userDetails[2].split(": ")[1];

                if ((fileUsername.equals(usernameOrEmail) || fileEmail.equals(usernameOrEmail)) && filePassword.equals(password)) {
                    isValidUser = true;
                    email = fileEmail;
                    profileImage = userDetails[4].split(": ")[1];
                    break;
                }
            }
        }

        if (isValidUser) {
            // Set session attributes
            HttpSession session = request.getSession();
            session.setAttribute("username", usernameOrEmail);
            session.setAttribute("email", email);
            session.setAttribute("profileImage", profileImage);

            // Redirect to menuusuarios.jsp
            response.sendRedirect("menuusuarios.jsp");
        } else {
            // Redirect back to login page with error message
            response.sendRedirect("login.jsp?error=Invalid username or password");
        }
    }
}
