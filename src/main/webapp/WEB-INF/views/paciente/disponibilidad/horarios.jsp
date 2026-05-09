<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Horarios — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background:#f1f5f9; }
        .medico-header {
            background:white; border-radius:16px; padding:1.5rem;
            display:flex; align-items:center; gap:1rem;
            margin-bottom:1.5rem; border:1px solid rgba(0,0,0,0.06);
        }
        .medico-av {
            width:54px; height:54px; border-radius:50%;
            background:linear-gradient(135deg,#1e7eb8,#0d5a9e);
            color:white; font-size:1.1rem; font-weight:700;
            display:flex; align-items:center; justify-content:center;
        }
        .filtro-fecha {
            background: white; border-radius: 12px; padding: 1rem 1.25rem;
            border: 1px solid rgba(0,0,0,0.06); margin-bottom: 1.5rem;
            display: flex; align-items: center; gap: 1rem; flex-wrap: wrap;
        }
        .filtro-fecha label {
            font-size: 0.85rem; font-weight: 600; color: #374151;
            white-space: nowrap;
        }
        .filtro-fecha select {
            padding: 0.5rem 1rem; border: 1.5px solid #e5e7eb;
            border-radius: 9px; font-family: 'DM Sans', sans-serif;
            font-size: 0.875rem; color: #374151; background: #fafafa;
            cursor: pointer; transition: border-color 0.2s;
        }
        .filtro-fecha select:focus {
            outline: none; border-color: #0d5a9e;
            box-shadow: 0 0 0 3px rgba(13,90,158,0.1);
        }
        .filtro-fecha button {
            padding: 0.5rem 1.1rem; background: #0d5a9e; color: white;
            border: none; border-radius: 9px; font-family: 'DM Sans', sans-serif;
            font-size: 0.875rem; font-weight: 600; cursor: pointer; transition: all 0.2s;
        }
        .filtro-fecha button:hover { background: #084577; }
        .horarios-grid {
            display:grid; grid-template-columns:repeat(auto-fill,minmax(200px,1fr));
            gap:1rem;
        }
        .horario-card {
            background:white; border-radius:14px; padding:1.25rem;
            border:2px solid rgba(0,0,0,0.06); transition:all 0.2s;
            display:flex; flex-direction:column; gap:0.5rem;
        }
        .horario-card.disponible:hover {
            border-color:#0d5a9e;
            box-shadow:0 8px 22px rgba(13,90,158,0.12);
            transform:translateY(-3px);
        }
        .horario-card.ocupado {
            opacity: 0.6;
        }
        .horario-fecha {
            font-size:0.75rem; font-weight:600; text-transform:uppercase;
            letter-spacing:0.06em; color:#9ca3af;
        }
        .horario-hora {
            font-family:'DM Serif Display',serif;
            font-size:1.25rem; color:#111827; line-height:1.1;
        }
        .badge-disponible {
            font-size:0.72rem; font-weight:600; padding:2px 8px;
            border-radius:12px; background:#d1fae5; color:#065f46;
            display:inline-block; margin-top:2px;
        }
        .badge-ocupado {
            font-size:0.72rem; font-weight:600; padding:2px 8px;
            border-radius:12px; background:#fee2e2; color:#991b1b;
            display:inline-block; margin-top:2px;
        }
        .btn-agendar {
            display:inline-flex; align-items:center; justify-content:center; gap:5px;
            margin-top:0.5rem; padding:0.5rem 0.9rem;
            background:#0d5a9e; color:white; border-radius:8px;
            text-decoration:none; font-size:0.8rem; font-weight:600;
            transition:all 0.18s;
        }
        .btn-agendar:hover { background:#084577; }
        .btn-back {
            display:inline-flex; align-items:center; gap:6px;
            color:#6b7280; text-decoration:none; font-size:0.875rem;
            margin-bottom:1.25rem; transition:color 0.18s;
        }
        .btn-back:hover { color:#0d5a9e; }
        [data-theme="dark"] .medico-header,
        [data-theme="dark"] .horario-card,
        [data-theme="dark"] .filtro-fecha { background:#1e293b; border-color:rgba(255,255,255,0.07); }
        [data-theme="dark"] .horario-hora { color:#f1f5f9; }
        [data-theme="dark"] .filtro-fecha label { color:#cbd5e1; }
        [data-theme="dark"] .filtro-fecha select { background:#0f172a; border-color:#334155; color:#e2e8f0; }
    </style>
</head>
<body class="app-body">
<%  request.setAttribute("currentPage", "disponibilidad"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <a href="${pageContext.request.contextPath}/paciente/disponibilidad"
           class="btn-back">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"/>
                <polyline points="12 19 5 12 12 5"/>
            </svg>
            Volver a médicos
        </a>

        <div class="medico-header">
            <div class="medico-av">DR</div>
            <div>
                <div style="font-size:1.05rem;font-weight:600;
                            color:#111827;font-family:'DM Sans',sans-serif;">
                    <c:out value="${medico.nombre}"/>
                </div>
                <div style="font-size:0.82rem;color:#0d5a9e;
                            font-weight:500;margin-top:2px;">
                    <c:out value="${medico.especialidad}"/>
                </div>
            </div>
        </div>

        <div class="page-header">
            <h1 class="page-title">Horarios disponibles</h1>
            <p class="page-subtitle">Filtra por día y selecciona un bloque disponible para agendar tu cita</p>
        </div>

        <c:choose>
            <c:when test="${sinDisponibilidad}">
                <div class="auth-alert"
                     style="background:#fffbeb;color:#92400e;border-left:3px solid #f59e0b;
                            padding:1rem;border-radius:10px;font-size:0.875rem;
                            margin-bottom:1.5rem;">
                    Este médico no tiene horarios publicados. Intenta con otro médico.
                </div>
            </c:when>
            <c:otherwise>
                <%-- Filtro por día --%>
                <div class="filtro-fecha">
                    <label for="selectFecha">Filtrar por día:</label>
                    <form method="get"
                          action="${pageContext.request.contextPath}/paciente/disponibilidad"
                          style="display:flex;align-items:center;gap:0.75rem;">
                        <input type="hidden" name="medicoId" value="${medico.id}">
                        <select id="selectFecha" name="fecha" onchange="this.form.submit()">
                            <option value="">-- Todos los días --</option>
                            <c:forEach var="f" items="${fechasDisponibles}">
                                <option value="${f}"
                                    ${fechaSeleccionada == f ? 'selected' : ''}>
                                    <c:out value="${f}"/>
                                </option>
                            </c:forEach>
                        </select>
                        <button type="submit">Ver horarios</button>
                    </form>
                </div>

                <c:choose>
                    <c:when test="${empty bloques}">
                        <div class="auth-alert"
                             style="background:#fffbeb;color:#92400e;border-left:3px solid #f59e0b;
                                    padding:1rem;border-radius:10px;font-size:0.875rem;">
                            No hay bloques disponibles para el día seleccionado.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="horarios-grid">
                            <c:forEach var="b" items="${bloques}">
                                <div class="horario-card ${b.estado == 'DISPONIBLE' ? 'disponible' : 'ocupado'}">
                                    <div class="horario-fecha">
                                        <c:out value="${b.fecha}"/>
                                    </div>
                                    <div class="horario-hora">
                                        <c:out value="${b.horaInicio}"/> –
                                        <c:out value="${b.horaFin}"/>
                                    </div>
                                    <c:choose>
                                        <c:when test="${b.estado == 'DISPONIBLE'}">
                                            <span class="badge-disponible">Disponible</span>
                                            <a class="btn-agendar"
                                               href="${pageContext.request.contextPath}/paciente/citas?action=agendar&bloqueId=${b.id}">
                                                <svg width="12" height="12" viewBox="0 0 24 24" fill="none"
                                                     stroke="currentColor" stroke-width="2.5"
                                                     stroke-linecap="round" stroke-linejoin="round">
                                                    <line x1="12" y1="5" x2="12" y2="19"/>
                                                    <line x1="5"  y1="12" x2="19" y2="12"/>
                                                </svg>
                                                Agendar
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge-ocupado">Ocupado</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>