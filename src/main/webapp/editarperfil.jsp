<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Perfil</title>
</head>
<body>
<h1>Editar Perfil</h1>

<%
    // Obtener la información del perfil desde la sesión
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");
    String profileImage = (String) session.getAttribute("profileImage");
%>

<!-- editarperfil.jsp -->
<form action="update-profile-servlet" method="post" enctype="multipart/form-data">
    <label for="username">Nombre de Usuario:</label>
    <input type="text" id="username" name="username" value="<%= username %>" required><br><br>
    <label for="email">Correo Electrónico:</label>
    <input type="email" id="email" name="email" value="<%= email %>" required><br><br>
    <label for="profileImage">Imagen de Perfil:</label>
    <input type="file" id="profileImage" name="profileImage"><br><br>
    <input type="submit" value="Actualizar Perfil">
    <button type="submit" name="action" value="delete">Eliminar Perfil</button>
</form>

</body>
</html>
