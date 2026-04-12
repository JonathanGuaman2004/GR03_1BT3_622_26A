<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Gestión de Citas Médicas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">
    <h1>Sistema de Gestión de Citas Médicas</h1>
    
    <p style="font-size: 1.1rem; margin-top: 1rem; color: #6b7280;">
        Accede a nuestro sistema para agendar, gestionar y realizar seguimiento de tus citas médicas de forma segura y eficiente.
    </p>
    
    <div class="form-actions mt-2">
        <a class="btn" href="${pageContext.request.contextPath}/login">
            Iniciar Sesión
        </a>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/registro">
            Crear Nueva Cuenta
        </a>
    </div>
</div>
</body>
</html>
