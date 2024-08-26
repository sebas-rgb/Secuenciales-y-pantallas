package org.example.testfinalomemato;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "registerServlet", value = "/register-servlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");

        // Set default profile image based on gender
        String profileImage;
        if ("male".equals(gender)) {
            profileImage = "profilepictures/defaultprofilepictures/defaultmaleuser.png";
        } else {
            profileImage = "profilepictures/defaultprofilepictures/defaultfemaleuser.png";
        }

        // Ruta del archivo
        String filePath = getServletContext().getRealPath("/WEB-INF/usuarios.txt");
        File file = new File(filePath);

        boolean userExists = false;

        // Revisar si el usuario ya existe en el archivo
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(", ");
                String fileUsername = userDetails[0].split(": ")[1];
                String fileEmail = userDetails[1].split(": ")[1];

                if (fileUsername.equals(username) || fileEmail.equals(email)) {
                    userExists = true;
                    break;
                }
            }
        }

        if (userExists) {
            // Redirect back to registration page with error message
            response.sendRedirect("register.jsp?error=Username or email already exists");
        } else {
            // Guardar la información del usuario en el archivo
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write("Username: " + username + ", Email: " + email + ", Password: " + password + ", Gender: " + gender + ", Picture: " + profileImage);
                bw.newLine();
            }

            // Set session attributes
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("email", email);
            session.setAttribute("profileImage", profileImage);

            // Redirect to menuusuarios.jsp
            response.sendRedirect("menuusuarios.jsp");
        }
    }
}
