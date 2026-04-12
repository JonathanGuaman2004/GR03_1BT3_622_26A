<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">
    <h1>Iniciar Sesión</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <strong>Error:</strong> <c:out value="${error}"/>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">

        <div class="form-group">
            <label for="email">📧 Correo Electrónico</label>
            <input type="email" id="email" name="email"
                   value="<c:out value='${email}'/>"
                   placeholder="tu.correo@ejemplo.com" required>
        </div>

        <div class="form-group">
            <label for="password">🔐 Contraseña</label>
            <input type="password" id="password" name="password"
                   placeholder="Ingresa tu contraseña" required>
        </div>

        <div class="form-actions">
            <input type="submit" class="btn" value="Iniciar Sesión">
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/registro">
                ¿No tienes cuenta? Regístrate
            </a>
        </div>
    </form>
</div>
</body>
</html>
