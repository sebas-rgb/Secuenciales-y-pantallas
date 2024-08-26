// UpdateProfileServlet.java
package org.example.testfinalomemato;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "updateProfileServlet", value = "/update-profile-servlet")
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            deleteProfile(request, response);
        } else {
            updateProfile(request, response);
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUsername = request.getParameter("username");
        String email = request.getParameter("email");
        Part filePart = request.getPart("profileImage");

        // Obtener el nombre de usuario actual desde la sesión
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("username");
        String currentEmail = (String) session.getAttribute("email");

        // Ruta del archivo
        String filePath = getServletContext().getRealPath("/WEB-INF/usuarios.txt");
        File file = new File(filePath);

        boolean userFound = false;
        boolean usernameExists = false;
        boolean emailExists = false;

        // Leer la información del usuario del archivo
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(", ");
                String fileUsername = userDetails[0].split(": ")[1];
                String fileEmail = userDetails[1].split(": ")[1];

                if (fileUsername.equals(currentUsername)) {
                    // Actualizar información del usuario
                    if (usernameExists && !fileUsername.equals(newUsername)) {
                        usernameExists = true;
                    }
                    if (emailExists && !fileEmail.equals(email)) {
                        emailExists = true;
                    }

                    // Guardar la imagen de perfil
                    String profileImagePath = filePart != null && filePart.getSize() > 0 ? saveProfileImage(filePart) : userDetails[4].split(": ")[1];
                    line = "Username: " + newUsername + ", Email: " + email + ", Password: " + userDetails[2].split(": ")[1] + ", Gender: " + userDetails[3].split(": ")[1] + ", Picture: " + profileImagePath;
                    userFound = true;
                } else {
                    if (fileUsername.equals(newUsername)) {
                        usernameExists = true;
                    }
                    if (fileEmail.equals(email)) {
                        emailExists = true;
                    }
                }
                fileContent.append(line).append("\n");
            }
        }

        if (usernameExists) {
            // Redirigir a la página de perfil con mensaje de error
            response.sendRedirect("editarperfil.jsp?error=Username already exists");
        } else if (emailExists) {
            // Redirigir a la página de perfil con mensaje de error
            response.sendRedirect("editarperfil.jsp?error=Email already exists");
        } else if (userFound) {
            // Escribir la información actualizada en el archivo
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(fileContent.toString());
            }

            // Actualizar la información en la sesión
            session.setAttribute("username", newUsername);
            session.setAttribute("email", email);
            session.setAttribute("profileImage", filePart != null && filePart.getSize() > 0 ? saveProfileImage(filePart) : null);

            // Redirigir a la página de perfil o a otra página
            response.sendRedirect("editarperfil.jsp");
        } else {
            // Redirigir a la página de perfil con mensaje de error
            response.sendRedirect("editarperfil.jsp?error=User not found");
        }
    }

    private void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el nombre de usuario actual desde la sesión
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("username");

        // Ruta del archivo
        String filePath = getServletContext().getRealPath("/WEB-INF/usuarios.txt");
        File file = new File(filePath);

        // Leer la información del usuario del archivo
        StringBuilder fileContent = new StringBuilder();
        boolean userFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(", ");
                String fileUsername = userDetails[0].split(": ")[1];

                if (!fileUsername.equals(currentUsername)) {
                    fileContent.append(line).append("\n");
                } else {
                    userFound = true;
                }
            }
        }

        if (userFound) {
            // Escribir la información actualizada en el archivo
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(fileContent.toString());
            }

            // Invalidar la sesión
            session.invalidate();

            // Redirigir a la página de inicio de sesión o a otra página
            response.sendRedirect("login.jsp");
        } else {
            // Redirigir a la página de perfil con mensaje de error
            response.sendRedirect("editarperfil.jsp?error=User not found");
        }
    }

    private String saveProfileImage(Part filePart) throws IOException {
        String uploadDir = getServletContext().getRealPath("/resources/profilepictures/userprofilepictures");
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // Generar un nombre de archivo único usando UUID
        String fileName = UUID.randomUUID().toString() + getFileExtension(filePart);

        // Validar el tipo de archivo
        String contentType = filePart.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new IOException("Invalid file type. Only images are allowed.");
        }

        // Guardar el archivo
        File file = new File(uploadDirFile, fileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return "profilepictures/userprofilepictures/" + fileName;
    }

    private String getFileExtension(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
    }
}