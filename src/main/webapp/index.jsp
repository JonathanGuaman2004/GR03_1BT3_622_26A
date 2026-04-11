<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema de Gestión de Citas Médicas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">
    <h1>Sistema de Gestión de Citas Médicas</h1>
    <p>Bienvenido. Inicia sesión para gestionar tus citas médicas o regístrate si aún no tienes cuenta.</p>
    <div class="form-actions mt-2">
        <a class="btn" href="${pageContext.request.contextPath}/login">Iniciar Sesión</a>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/registro">Registrarse como Paciente</a>
    </div>
</div>
</body>
</html>
