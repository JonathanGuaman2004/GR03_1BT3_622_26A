<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Paciente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">
    <h1>Crear Cuenta de Paciente</h1>

    <c:if test="${not empty mensaje}">
        <div class="alert alert-success">
            <strong>¡Éxito!</strong> <c:out value="${mensaje}"/>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <strong>Error:</strong> <c:out value="${error}"/>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/registro" method="post">

        <div class="form-group">
            <label for="nombre">👤 Nombre Completo</label>
            <input type="text" id="nombre" name="nombre"
                   value="<c:out value='${datos.nombre}'/>"
                   placeholder="Ej: Ana María Torres García" required maxlength="150">
        </div>

        <div class="form-group">
            <label for="cedula">📋 Cédula de Identidad</label>
            <input type="text" id="cedula" name="cedula"
                   value="<c:out value='${datos.cedula}'/>"
                   placeholder="Ej: 1712345678" required maxlength="20">
        </div>

        <div class="form-group">
            <label for="telefono">📱 Teléfono/Celular</label>
            <input type="text" id="telefono" name="telefono"
                   value="<c:out value='${datos.telefono}'/>"
                   placeholder="Ej: 0987654321" maxlength="20">
        </div>

        <div class="form-group">
            <label for="email">📧 Correo Electrónico</label>
            <input type="email" id="email" name="email"
                   value="<c:out value='${datos.email}'/>"
                   placeholder="tu.correo@ejemplo.com" required maxlength="150">
        </div>

        <div class="form-group">
            <label for="password">🔐 Contraseña (mínimo 6 caracteres)</label>
            <input type="password" id="password" name="password"
                   placeholder="Crea una contraseña segura" required maxlength="100">
        </div>

        <div class="form-actions">
            <input type="submit" class="btn btn-success" value="Crear Cuenta">
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/login">
                Ya tengo cuenta, iniciar sesión
            </a>
        </div>
    </form>
</div>
</body>
</html>
