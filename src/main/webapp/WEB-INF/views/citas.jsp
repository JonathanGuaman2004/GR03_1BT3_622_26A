<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Citas — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }
        .citas-table-wrap {
            background: white;
            border-radius: 16px;
            border: 1px solid rgba(0,0,0,0.06);
            overflow: hidden;
        }
        .citas-table {
            width: 100%;
            border-collapse: collapse;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.875rem;
        }
        .citas-table thead tr {
            background: #f8fafc;
            border-bottom: 1px solid rgba(0,0,0,0.07);
        }
        .citas-table th {
            padding: 0.85rem 1rem;
            text-align: left;
            font-size: 0.75rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
            color: #9ca3af;
        }
        .citas-table td {
            padding: 0.9rem 1rem;
            border-bottom: 1px solid rgba(0,0,0,0.05);
            color: #374151;
        }
        .citas-table tbody tr:last-child td { border-bottom: none; }
        .citas-table tbody tr:hover { background: #f9fafb; }
        .estado-badge {
            display: inline-block;
            padding: 3px 10px;
            border-radius: 20px;
            font-size: 0.75rem;
            font-weight: 600;
        }
        .estado-PENDIENTE { background: #fef9c3; color: #854d0e; }
        .estado-CONFIRMADA { background: #dcfce7; color: #166534; }
        .estado-CANCELADA { background: #fee2e2; color: #991b1b; }
        .btn-cancelar {
            display: inline-flex;
            align-items: center;
            gap: 4px;
            padding: 4px 10px;
            border-radius: 7px;
            background: #fff1f2;
            color: #be123c;
            border: 1px solid #fecdd3;
            font-size: 0.78rem;
            font-weight: 600;
            font-family: 'DM Sans', sans-serif;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.18s;
        }
        .btn-cancelar:hover { background: #ffe4e6; }
        .btn-nueva {
            display: inline-flex;
            align-items: center;
            gap: 7px;
            padding: 0.6rem 1.3rem;
            background: #0d5a9e;
            color: white;
            border-radius: 9px;
            text-decoration: none;
            font-size: 0.875rem;
            font-weight: 600;
            font-family: 'DM Sans', sans-serif;
            margin-bottom: 1.5rem;
            transition: all 0.2s;
        }
        .btn-nueva:hover {
            background: #084577;
            transform: translateY(-1px);
            box-shadow: 0 4px 14px rgba(13,90,158,0.25);
        }
        .empty-state {
            background: white;
            border-radius: 16px;
            padding: 3rem;
            text-align: center;
            border: 1px solid rgba(0,0,0,0.06);
            color: #6b7280;
        }
        [data-theme="dark"] .citas-table-wrap { background: #1e293b; border-color: rgba(255,255,255,0.07); }
        [data-theme="dark"] .citas-table thead tr { background: #162032; border-color: rgba(255,255,255,0.06); }
        [data-theme="dark"] .citas-table th { color: #64748b; }
        [data-theme="dark"] .citas-table td { color: #cbd5e1; border-color: rgba(255,255,255,0.05); }
        [data-theme="dark"] .citas-table tbody tr:hover { background: #253447; }
        [data-theme="dark"] .empty-state { background: #1e293b; border-color: rgba(255,255,255,0.07); }

        /* Modal confirmación cancelar */
        #modal-cancelar { display:none; }
        #modal-cancelar.open { display:flex; }
    </style>
</head>
<body class="app-body">
<%
    request.setAttribute("currentPage", "citas");
%>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">
        <div class="page-header">
            <h1 class="page-title">Mis citas</h1>
            <p class="page-subtitle">Historial y estado de todas tus citas médicas</p>
        </div>

        <a href="${pageContext.request.contextPath}/disponibilidad" class="btn-nueva">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            Agendar nueva cita
        </a>

        <c:choose>
            <c:when test="${empty citas}">
                <div class="empty-state">
                    <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="#d1d5db" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" style="margin-bottom:1rem;display:block;margin-left:auto;margin-right:auto;"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>
                    <p>No tienes citas registradas aún. ¡Agenda una con los médicos disponibles!</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="citas-table-wrap">
                    <table class="citas-table">
                        <caption class="sr-only">Listado de mis citas médicas</caption>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Médico</th>
                                <th scope="col">Especialidad</th>
                                <th scope="col">Fecha</th>
                                <th scope="col">Hora</th>
                                <th scope="col">Motivo</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Acción</th>
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
                                    <td style="max-width:160px;font-size:0.8rem;"><c:out value="${c.motivo}"/></td>
                                    <td>
                                        <span class="estado-badge estado-${c.estado}">
                                            <c:out value="${c.estado}"/>
                                        </span>
                                    </td>
                                    <td>
                                        <c:if test="${c.estado != 'CANCELADA'}">
                                            <a class="btn-cancelar"
                                               href="#"
                                               onclick="confirmarCancelar(${c.id}); return false;">
                                                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
                                                Cancelar
                                            </a>
                                        </c:if>
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

<!-- Modal cancelar (reemplaza el confirm nativo) -->
<div class="modal-overlay" id="modal-cancelar" onclick="cerrarModal(event)">
    <div class="modal-card" style="max-width:360px;">
        <div class="modal-header">
            <h2 class="modal-title">Cancelar cita</h2>
            <button class="modal-close" onclick="document.getElementById('modal-cancelar').classList.remove('open')">✕</button>
        </div>
        <p style="font-size:0.9rem;color:#6b7280;margin:0 0 1.5rem;line-height:1.6;">
            ¿Estás seguro de que deseas cancelar esta cita? El horario quedará libre para otros pacientes.
        </p>
        <div style="display:flex;gap:0.75rem;justify-content:flex-end;">
            <button class="topbar-btn" onclick="document.getElementById('modal-cancelar').classList.remove('open')"
                    style="border:1px solid #e5e7eb;padding:0.55rem 1.2rem;border-radius:8px;">Mantener cita</button>
            <a id="cancelar-link" href="#" class="btn-cancelar" style="padding:0.55rem 1.2rem;font-size:0.875rem;">
                Sí, cancelar
            </a>
        </div>
    </div>
</div>

<script>
var ctxPath = '${pageContext.request.contextPath}';
function confirmarCancelar(id) {
    document.getElementById('cancelar-link').href = ctxPath + '/citas?action=cancelar&id=' + id;
    document.getElementById('modal-cancelar').classList.add('open');
}
function cerrarModal(e) {
    if (e.target === e.currentTarget) e.currentTarget.classList.remove('open');
}
</script>
</body>
</html>
