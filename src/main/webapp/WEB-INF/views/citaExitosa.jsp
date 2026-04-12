<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>¡Cita Agendada Exitosamente!</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        .success-icon {
            font-size: 3.5rem;
            text-align: center;
            margin: 1.5rem 0;
            animation: bounce 0.6s ease-out;
        }
        @keyframes bounce {
            0% { transform: scale(0) rotate(-20deg); opacity: 0; }
            50% { transform: scale(1.1); }
            100% { transform: scale(1) rotate(0); opacity: 1; }
        }
        .confirmation-table {
            background: var(--bg-lighter);
            border-radius: 12px;
            padding: 1.5rem;
            margin: 1.5rem 0;
        }
        .confirmation-table tr {
            display: flex;
            padding: 0.8rem 0;
            border-bottom: 1px solid var(--border-color);
        }
        .confirmation-table tr:last-child {
            border-bottom: none;
        }
        .confirmation-table th {
            color: var(--primary-color);
            font-weight: 600;
            width: 30%;
            background: none;
            padding: 0;
            text-align: left;
        }
        .confirmation-table td {
            flex-grow: 1;
            border: none;
            padding: 0;
            margin-left: 1rem;
        }
    </style>
</head>
<body>
<div class="container">

    <nav>
        <span class="nav-brand">🏥 Sistema de Citas Médicas</span>
        <a href="${pageContext.request.contextPath}/disponibilidad">👨‍⚕️ Médicos</a>
        <a href="${pageContext.request.contextPath}/citas">📋 Mis Citas</a>
        <a href="${pageContext.request.contextPath}/login?action=logout">🚪 Cerrar Sesión</a>
    </nav>

    <div class="success-icon">✅</div>
    <h1 style="text-align: center; margin-bottom: 0.5rem;">¡Cita Agendada Exitosamente!</h1>
    
    <div class="alert alert-success" style="text-align: center; justify-content: center;">
        Tu cita ha sido registrada correctamente. Recibirás una confirmación en tu correo electrónico.
    </div>

    <c:if test="${not empty citaConfirmada}">
        <div class="confirmation-table">
            <table style="width: 100%; border-collapse: collapse;">
                <tr>
                    <th>👨‍⚕️ Médico</th>
                    <td><strong><c:out value="${citaConfirmada.medico.nombre}"/></strong></td>
                </tr>
                <tr>
                    <th>🎯 Especialidad</th>
                    <td><c:out value="${citaConfirmada.medico.especialidad}"/></td>
                </tr>
                <tr>
                    <th>📅 Fecha</th>
                    <td><strong><c:out value="${citaConfirmada.fecha}"/></strong></td>
                </tr>
                <tr>
                    <th>🕐 Hora</th>
                    <td><strong><c:out value="${citaConfirmada.hora}"/></strong></td>
                </tr>
                <tr>
                    <th>📝 Motivo</th>
                    <td><c:out value="${citaConfirmada.motivo}"/></td>
                </tr>
                <tr>
                    <th>✅ Estado</th>
                    <td>
                        <span class="badge badge-pendiente">
                            <c:out value="${citaConfirmada.estado}"/>
                        </span>
                    </td>
                </tr>
            </table>
        </div>
    </c:if>

    <div class="form-actions mt-2" style="justify-content: center;">
        <a class="btn" href="${pageContext.request.contextPath}/citas">
            📋 Ver Mis Citas
        </a>
        <a class="btn btn-success" href="${pageContext.request.contextPath}/disponibilidad">
            ➕ Agendar Otra Cita
        </a>
    </div>
</div>
</body>
</html>
