<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Paciente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">
    <h1>Registro de Paciente</h1>

    <c:if test="${not empty mensaje}">
        <div class="alert alert-success"><c:out value="${mensaje}"/></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>

    <form action="${pageContext.request.contextPath}/registro" method="post">

        <div class="form-group">
            <label for="nombre">Nombre completo:</label>
            <input type="text" id="nombre" name="nombre"
                   value="<c:out value='${datos.nombre}'/>"
                   placeholder="Ej: Ana Torres" required maxlength="150">
        </div>

        <div class="form-group">
            <label for="cedula">Cédula:</label>
            <input type="text" id="cedula" name="cedula"
                   value="<c:out value='${datos.cedula}'/>"
                   placeholder="Ej: 1712345678" required maxlength="20">
        </div>

        <div class="form-group">
            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono"
                   value="<c:out value='${datos.telefono}'/>"
                   placeholder="Ej: 0987654321" maxlength="20">
        </div>

        <div class="form-group">
            <label for="email">Correo electrónico:</label>
            <input type="email" id="email" name="email"
                   value="<c:out value='${datos.email}'/>"
                   placeholder="correo@ejemplo.com" required maxlength="150">
        </div>

        <div class="form-group">
            <label for="password">Contraseña (mínimo 6 caracteres):</label>
            <input type="password" id="password" name="password"
                   placeholder="••••••••" required maxlength="100">
        </div>

        <div class="form-actions">
            <input type="submit" class="btn btn-success" value="Registrarse">
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/login">Ya tengo cuenta</a>
        </div>
    </form>
</div>
</body>
</html>
