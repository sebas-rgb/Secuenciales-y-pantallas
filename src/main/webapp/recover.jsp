<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recuperar Contraseña</title>
</head>
<body>
<h1>Recuperar Contraseña</h1>
<form action="recover-servlet" method="post">
    <label for="email">Correo Electrónico:</label>
    <input type="email" id="email" name="email" required><br><br>
    <input type="submit" value="Recuperar">
</form>
</body>
</html>