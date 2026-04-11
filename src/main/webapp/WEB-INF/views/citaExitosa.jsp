<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Cita Agendada</title>
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

    <h1>¡Cita Agendada Exitosamente!</h1>

    <div class="alert alert-success">
        Tu cita ha sido registrada. Recibirás una confirmación en tu correo.
    </div>

    <c:if test="${not empty citaConfirmada}">
        <table>
            <tr><th>Médico</th>
                <td><c:out value="${citaConfirmada.medico.nombre}"/></td></tr>
            <tr><th>Especialidad</th>
                <td><c:out value="${citaConfirmada.medico.especialidad}"/></td></tr>
            <tr><th>Fecha</th>
                <td><c:out value="${citaConfirmada.fecha}"/></td></tr>
            <tr><th>Hora</th>
                <td><c:out value="${citaConfirmada.hora}"/></td></tr>
            <tr><th>Motivo</th>
                <td><c:out value="${citaConfirmada.motivo}"/></td></tr>
            <tr><th>Estado</th>
                <td><span class="badge badge-pendiente">
                    <c:out value="${citaConfirmada.estado}"/></span>
                </td></tr>
        </table>
    </c:if>

    <div class="form-actions mt-2">
        <a class="btn" href="${pageContext.request.contextPath}/citas">Ver mis citas</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/disponibilidad">
            Agendar otra cita
        </a>
    </div>
</div>
</body>
</html>
