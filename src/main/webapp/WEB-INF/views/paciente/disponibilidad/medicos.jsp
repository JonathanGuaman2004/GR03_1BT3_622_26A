<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Médicos Disponibles — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background:#f1f5f9; }
        .filtro-bar {
            display:flex; gap:0.75rem; margin-bottom:1.5rem; flex-wrap:wrap;
            align-items:center;
        }
        .filtro-bar select {
            padding:0.55rem 1rem; border:1.5px solid #e5e7eb;
            border-radius:9px; font-family:'DM Sans',sans-serif;
            font-size:0.875rem; color:#374151; background:#fff;
            cursor:pointer; transition:border-color 0.2s;
        }
        .filtro-bar select:focus {
            outline:none; border-color:#0d5a9e;
            box-shadow:0 0 0 3px rgba(13,90,158,0.1);
        }
        .filtro-bar button {
            padding:0.55rem 1.2rem; background:#0d5a9e; color:white;
            border:none; border-radius:9px; font-family:'DM Sans',sans-serif;
            font-size:0.875rem; font-weight:600; cursor:pointer; transition:all 0.2s;
        }
        .filtro-bar button:hover { background:#084577; }
        .medicos-grid {
            display:grid; grid-template-columns:repeat(auto-fill,minmax(260px,1fr));
            gap:1.25rem;
        }
        .medico-card {
            background:white; border-radius:16px; padding:1.5rem;
            border:1px solid rgba(0,0,0,0.06); transition:all 0.22s;
            display:flex; flex-direction:column;
        }
        .medico-card:hover {
            border-color:rgba(13,90,158,0.25);
            box-shadow:0 10px 28px rgba(13,90,158,0.1);
            transform:translateY(-4px);
        }
        .medico-avatar {
            width:52px; height:52px; border-radius:50%;
            background:linear-gradient(135deg,#1e7eb8,#0d5a9e);
            color:white; font-size:1.1rem; font-weight:700;
            display:flex; align-items:center; justify-content:center;
            margin-bottom:1rem;
        }
        .medico-nombre { font-size:1rem; font-weight:600; color:#111827; margin:0 0 4px; }
        .medico-especialidad {
            font-size:0.82rem; color:#0d5a9e; font-weight:500;
            background:#eff6ff; display:inline-block;
            padding:2px 9px; border-radius:20px; margin-bottom:0.75rem;
        }
        .medico-licencia { font-size:0.78rem; color:#6b7280; margin-bottom:1.25rem; }
        .medico-btn {
            margin-top:auto; display:inline-flex;
            align-items:center; justify-content:center; gap:6px;
            padding:0.6rem 1.1rem; background:#0d5a9e; color:white;
            border-radius:9px; text-decoration:none; font-size:0.85rem;
            font-weight:600; transition:all 0.18s;
        }
        .medico-btn:hover { background:#084577; transform:translateY(-1px); }
        .empty-state {
            background:white; border-radius:16px; padding:3rem;
            text-align:center; border:1px solid rgba(0,0,0,0.06); color:#6b7280;
        }
        [data-theme="dark"] .medico-card { background:#1e293b; border-color:rgba(255,255,255,0.07); }
        [data-theme="dark"] .medico-nombre { color:#f1f5f9; }
        [data-theme="dark"] .medico-especialidad { background:#1d3a5e; color:#93c5fd; }
        [data-theme="dark"] .empty-state { background:#1e293b; border-color:rgba(255,255,255,0.07); }
        [data-theme="dark"] .filtro-bar select { background:#1e293b; border-color:#334155; color:#cbd5e1; }
    </style>
</head>
<body class="app-body">
<%  request.setAttribute("currentPage", "disponibilidad"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">
        <div class="page-header">
            <h1 class="page-title">Médicos disponibles</h1>
            <p class="page-subtitle">
                Selecciona un médico para revisar sus horarios y agendar tu cita
            </p>
        </div>

        <c:if test="${not empty error}">
            <div class="auth-alert error" style="margin-bottom:1.5rem;">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <!-- Filtro por especialidad -->
        <form method="get"
              action="${pageContext.request.contextPath}/paciente/disponibilidad"
              class="filtro-bar">
            <select name="especialidad">
                <option value="">Todas las especialidades</option>
                <option value="Medicina General"
                    ${especialidad == 'Medicina General' ? 'selected' : ''}>
                    Medicina General
                </option>
                <option value="Pediatría"
                    ${especialidad == 'Pediatría' ? 'selected' : ''}>
                    Pediatría
                </option>
                <option value="Cardiología"
                    ${especialidad == 'Cardiología' ? 'selected' : ''}>
                    Cardiología
                </option>
            </select>
            <button type="submit">Filtrar</button>
        </form>

        <c:choose>
            <c:when test="${empty medicos}">
                <div class="empty-state">
                    <svg width="48" height="48" viewBox="0 0 24 24" fill="none"
                         stroke="#d1d5db" stroke-width="1.5"
                         stroke-linecap="round" stroke-linejoin="round"
                         style="margin-bottom:1rem;display:block;
                                margin-left:auto;margin-right:auto;">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                    </svg>
                    <p>No hay médicos disponibles en este momento. Intenta más tarde.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="medicos-grid">
                    <c:forEach var="m" items="${medicos}">
                        <c:set var="nomMedico" value="${m.nombre}"/>
                        <c:set var="palabras"  value="${fn:split(nomMedico, ' ')}"/>
                        <div class="medico-card">
                            <div class="medico-avatar">
                                <c:choose>
                                    <c:when test="${fn:length(palabras) >= 2}">
                                        <c:out value="${fn:toUpperCase(fn:substring(palabras[0],0,1))}"/>
                                        <c:out value="${fn:toUpperCase(fn:substring(palabras[1],0,1))}"/>
                                    </c:when>
                                    <c:otherwise>DR</c:otherwise>
                                </c:choose>
                            </div>
                            <div class="medico-nombre"><c:out value="${m.nombre}"/></div>
                            <span class="medico-especialidad">
                                <c:out value="${m.especialidad}"/>
                            </span>
                            <div class="medico-licencia">
                                Lic. <c:out value="${m.nroLicencia}"/>
                            </div>
                            <a class="medico-btn"
                               href="${pageContext.request.contextPath}/paciente/disponibilidad?medicoId=${m.id}">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="none"
                                     stroke="currentColor" stroke-width="2.5"
                                     stroke-linecap="round" stroke-linejoin="round">
                                    <rect x="3" y="4" width="18" height="18" rx="2"/>
                                    <line x1="16" y1="2" x2="16" y2="6"/>
                                    <line x1="8"  y1="2" x2="8"  y2="6"/>
                                    <line x1="3"  y1="10" x2="21" y2="10"/>
                                </svg>
                                Ver horarios
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>