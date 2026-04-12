<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmar Cita Médica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">

    <nav>
        <span class="nav-brand">🏥 Sistema de Citas Médicas</span>
        <a href="${pageContext.request.contextPath}/disponibilidad">👨‍⚕️ Médicos</a>
        <a href="${pageContext.request.contextPath}/citas">📋 Mis Citas</a>
        <a href="${pageContext.request.contextPath}/login?action=logout">🚪 Cerrar Sesión</a>
    </nav>

    <h1>Confirmar Tu Cita Médica</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <strong>Error:</strong> <c:out value="${error}"/>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/citas" method="post">
        <input type="hidden" name="horarioId" value="<c:out value='${horarioId}'/>">

        <div class="form-group">
            <label for="motivo">📝 Motivo de Consulta</label>
            <textarea id="motivo" name="motivo" rows="4"
                      placeholder="Describe brevemente los síntomas o razón de tu consulta. Máximo 500 caracteres."
                      maxlength="500" required></textarea>
            <small style="color: #9ca3af; display: block; margin-top: 0.5rem;">
                Esta información ayudará al médico a prepararse mejor para tu consulta.
            </small>
        </div>

        <div class="form-actions">
            <input type="submit" class="btn btn-success" value="✅ Confirmar Cita">
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/disponibilidad">
                ← Seleccionar Otro Médico
            </a>
        </div>
    </form>
</div>
</body>
</html>
