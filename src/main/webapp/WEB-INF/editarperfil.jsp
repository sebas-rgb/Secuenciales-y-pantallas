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

<form action="update-profile-servlet" method="post">
    <label for="username">Nombre de Usuario:</label>
    <input type="text" id="username" name="username" value="<%= username %>" required><br><br>
    <label for="email">Correo Electrónico:</label>
    <input type="email" id="email" name="email" value="<%= email %>" required><br><br>
    <label for="profileImage">Imagen de Perfil:</label>
    <input type="text" id="profileImage" name="profileImage" value="<%= profileImage %>" required><br><br>
    <input type="submit" value="Actualizar Perfil">
</form>

</body>
</html>
