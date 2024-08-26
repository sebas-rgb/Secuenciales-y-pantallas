<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión</title>
</head>
<body>
<h1>Iniciar Sesión</h1>
<form action="login-servlet" method="post">
    <label for="username">Nombre de Usuario o Correo Electrónico:</label>
    <input type="text" id="username" name="username" required><br><br>
    <label for="password">Contraseña:</label>
    <input type="password" id="password" name="password" required><br><br>
    <input type="submit" value="Iniciar Sesión">
</form>
</body>
</html>
