<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Horarios Disponibles</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="container">

    <nav>
        <span class="nav-brand">Sistema de Citas Médicas</span>
        <a href="${pageContext.request.contextPath}/disponibilidad">Médicos</a>
        <a href="${pageContext.request.contextPath}/citas">Mis Citas</a>
        <a href="${pageContext.request.contextPath}/login?action=logout">Cerrar Sesión</a>
    </nav>

    <h1>Horarios de Dr/Dra. <c:out value="${medico.nombre}"/></h1>
    <p><strong>Especialidad:</strong> <c:out value="${medico.especialidad}"/></p>

    <c:choose>
        <c:when test="${sinDisponibilidad}">
            <div class="alert alert-info mt-1">
                Este médico no tiene horarios disponibles en este momento.
            </div>
            <div class="mt-1">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/disponibilidad">
                    Elegir otro médico
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>Día</th>
                    <th>Hora Inicio</th>
                    <th>Hora Fin</th>
                    <th>Acción</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="h" items="${horarios}">
                    <tr>
                        <td><c:out value="${h.diaSemana}"/></td>
                        <td><c:out value="${h.horaInicio}"/></td>
                        <td><c:out value="${h.horaFin}"/></td>
                        <td>
                            <a class="btn btn-sm btn-success"
                               href="${pageContext.request.contextPath}/citas?action=agendar&horarioId=${h.id}">
                                Agendar
                            </a>
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
