<%@ page import="java.io.File" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menú de Usuario</title>
</head>
<body>
<%
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");
    String profileImage = (String) session.getAttribute("profileImage");
%>

<h1>Bienvenido, <%= username %>!</h1>
<img src="resources/<%= profileImage %>" alt="Imagen de Perfil" width="150" height="150"/>
<p>Email: <%= email %></p>

<!-- Opciones del menú de usuario -->
<ul>
    <li><a href="editarperfil.jsp">Editar Perfil</a></li>
    <li><a href="cerrarsesion.jsp">Cerrar Sesión</a></li>
</ul>

</body>
</html>