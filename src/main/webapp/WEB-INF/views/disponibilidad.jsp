<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Médicos Disponibles</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">

    <nav>
        <span class="nav-brand">Sistema de Citas Médicas</span>
        <a href="${pageContext.request.contextPath}/citas">Mis Citas</a>
        <a href="${pageContext.request.contextPath}/login?action=logout">Cerrar Sesión</a>
    </nav>

    <h1>Médicos Disponibles</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>

    <c:choose>
        <c:when test="${empty medicos}">
            <p class="info">No hay médicos registrados en el sistema aún.</p>
        </c:when>
        <c:otherwise>
            <p>Selecciona un médico para ver sus horarios disponibles.</p>
            <div class="card-grid">
                <c:forEach var="m" items="${medicos}">
                    <div class="card">
                        <h3><c:out value="${m.nombre}"/></h3>
                        <p><strong>Especialidad:</strong> <c:out value="${m.especialidad}"/></p>
                        <p><strong>Licencia:</strong> <c:out value="${m.nroLicencia}"/></p>
                        <a class="btn btn-sm"
                           href="${pageContext.request.contextPath}/disponibilidad?medicoId=${m.id}">
                            Ver Horarios
                        </a>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
