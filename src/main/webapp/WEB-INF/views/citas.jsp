<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Citas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">

    <nav>
        <span class="nav-brand">🏥 Sistema de Citas Médicas</span>
        <a href="${pageContext.request.contextPath}/disponibilidad">👨‍⚕️ Médicos</a>
        <a href="${pageContext.request.contextPath}/login?action=logout">🚪 Cerrar Sesión</a>
    </nav>

    <h1>Mis Citas Médicas</h1>

    <a class="btn btn-success" href="${pageContext.request.contextPath}/disponibilidad">
        ➕ Agendar Nueva Cita
    </a>

    <c:choose>
        <c:when test="${empty citas}">
            <div class="info" style="margin-top: 1.5rem;">
                📋 No tienes citas registradas aún. ¡Agenda una con los médicos disponibles!
            </div>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>👨‍⚕️ Médico</th>
                    <th>🎯 Especialidad</th>
                    <th>📅 Fecha</th>
                    <th>🕐 Hora</th>
                    <th>📝 Motivo</th>
                    <th>✅ Estado</th>
                    <th>⚙️ Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="c" items="${citas}">
                    <tr>
                        <td><c:out value="${c.id}"/></td>
                        <td><strong><c:out value="${c.medico.nombre}"/></strong></td>
                        <td><c:out value="${c.medico.especialidad}"/></td>
                        <td><c:out value="${c.fecha}"/></td>
                        <td><c:out value="${c.hora}"/></td>
                        <td><span style="font-size: 0.85rem;"><c:out value="${c.motivo}"/></span></td>
                        <td>
                            <span class="badge
                                <c:choose>
                                    <c:when test='${c.estado == "CONFIRMADA"}'>badge-confirmada</c:when>
                                    <c:when test='${c.estado == "CANCELADA"}'>badge-cancelada</c:when>
                                    <c:otherwise>badge-pendiente</c:otherwise>
                                </c:choose>">
                                <c:out value="${c.estado}"/>
                            </span>
                        </td>
                        <td class="acciones">
                            <c:if test="${c.estado != 'CANCELADA'}">
                                <a class="btn btn-sm btn-danger"
                                   href="${pageContext.request.contextPath}/citas?action=cancelar&id=${c.id}"
                                   onclick="return confirm('¿Estás seguro de que deseas cancelar esta cita?')">
                                    ❌ Cancelar
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
