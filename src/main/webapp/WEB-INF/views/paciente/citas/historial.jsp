<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Citas — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background:#f1f5f9; }
        .citas-table-wrap {
            background:white; border-radius:16px;
            border:1px solid rgba(0,0,0,0.06); overflow:hidden;
        }
        .citas-table {
            width:100%; border-collapse:collapse;
            font-family:'DM Sans',sans-serif; font-size:0.875rem;
        }
        .citas-table thead tr {
            background:#f8fafc; border-bottom:1px solid rgba(0,0,0,0.07);
        }
        .citas-table th {
            padding:0.85rem 1rem; text-align:left;
            font-size:0.75rem; font-weight:600;
            text-transform:uppercase; letter-spacing:0.05em; color:#9ca3af;
        }
        .citas-table td {
            padding:0.9rem 1rem;
            border-bottom:1px solid rgba(0,0,0,0.05); color:#374151;
        }
        .citas-table tbody tr:last-child td { border-bottom:none; }
        .citas-table tbody tr:hover         { background:#f9fafb; }
        .estado-badge {
            display:inline-block; padding:3px 10px;
            border-radius:20px; font-size:0.75rem; font-weight:600;
        }
        .estado-COMPLETADA { background:#d1fae5; color:#065f46; }
        .estado-AUSENTE    { background:#fef9c3; color:#854d0e; }
        .estado-CANCELADA  { background:#fee2e2; color:#991b1b; }
        .estado-REAGENDADA { background:#dbeafe; color:#1e40af; }
        .empty-state {
            background:white; border-radius:16px; padding:3rem;
            text-align:center; border:1px solid rgba(0,0,0,0.06); color:#6b7280;
        }
        .btn-volver {
            display:inline-flex; align-items:center; gap:6px;
            color:#6b7280; text-decoration:none; font-size:0.875rem;
            margin-bottom:1.25rem; transition:color 0.18s;
        }
        .btn-volver:hover { color:#0d5a9e; }
        [data-theme="dark"] .citas-table-wrap { background:#1e293b; border-color:rgba(255,255,255,0.07); }
        [data-theme="dark"] .citas-table thead tr { background:#162032; }
        [data-theme="dark"] .citas-table th  { color:#64748b; }
        [data-theme="dark"] .citas-table td  { color:#cbd5e1; border-color:rgba(255,255,255,0.05); }
        [data-theme="dark"] .citas-table tbody tr:hover { background:#253447; }
        [data-theme="dark"] .empty-state { background:#1e293b; border-color:rgba(255,255,255,0.07); }
    </style>
</head>
<body class="app-body">
<%  request.setAttribute("currentPage", "historial"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">
        <a href="${pageContext.request.contextPath}/paciente/citas"
           class="btn-volver">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"/>
                <polyline points="12 19 5 12 12 5"/>
            </svg>
            Volver a mis citas
        </a>

        <div class="page-header">
            <h1 class="page-title">Historial de citas</h1>
            <p class="page-subtitle">
                Registro completo de tus consultas anteriores
            </p>
        </div>

        <c:choose>
            <c:when test="${empty historial}">
                <div class="empty-state">
                    <svg width="48" height="48" viewBox="0 0 24 24" fill="none"
                         stroke="#d1d5db" stroke-width="1.5"
                         stroke-linecap="round" stroke-linejoin="round"
                         style="margin-bottom:1rem;display:block;
                                margin-left:auto;margin-right:auto;">
                        <circle cx="12" cy="12" r="10"/>
                        <polyline points="12 6 12 12 16 14"/>
                    </svg>
                    <p>No tienes citas en el historial todavía.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="citas-table-wrap">
                    <table class="citas-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Médico</th>
                                <th>Especialidad</th>
                                <th>Fecha</th>
                                <th>Hora</th>
                                <th>Motivo</th>
                                <th>Estado final</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${historial}">
                                <tr>
                                    <td><c:out value="${c.id}"/></td>
                                    <td><strong><c:out value="${c.medico.nombre}"/></strong></td>
                                    <td><c:out value="${c.medico.especialidad}"/></td>
                                    <td><c:out value="${c.bloque.fecha}"/></td>
                                    <td><c:out value="${c.bloque.horaInicio}"/></td>
                                    <td style="max-width:150px;font-size:0.8rem;">
                                        <c:out value="${c.motivo}"/>
                                    </td>
                                    <td>
                                        <span class="estado-badge estado-${c.estado}">
                                            <c:out value="${c.estado}"/>
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>