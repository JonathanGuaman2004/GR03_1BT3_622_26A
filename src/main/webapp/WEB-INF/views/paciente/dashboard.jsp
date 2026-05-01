<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background:#f1f5f9; }
        .welcome-hero {
            background: linear-gradient(135deg,#0d5a9e 0%,#1e7eb8 60%,#10b981 100%);
            border-radius:18px; padding:2.5rem; color:white;
            margin-bottom:2rem; position:relative; overflow:hidden;
        }
        .welcome-hero::before {
            content:''; position:absolute; top:-60px; right:-60px;
            width:240px; height:240px;
            background:rgba(255,255,255,0.06); border-radius:50%;
        }
        .welcome-greeting {
            font-size:0.85rem; font-weight:500; letter-spacing:0.06em;
            text-transform:uppercase; opacity:0.75; margin-bottom:0.5rem;
        }
        .welcome-title {
            font-family:'DM Serif Display',serif; font-size:2.2rem;
            font-weight:400; margin:0 0 0.75rem; line-height:1.2;
            position:relative; z-index:1;
        }
        .welcome-desc {
            font-size:0.95rem; opacity:0.85; max-width:480px;
            line-height:1.65; margin:0 0 1.75rem; position:relative; z-index:1;
        }
        .welcome-actions { display:flex; gap:0.75rem; flex-wrap:wrap; position:relative; z-index:1; }
        .welcome-btn {
            display:inline-flex; align-items:center; gap:7px;
            padding:0.65rem 1.4rem; border-radius:9px; text-decoration:none;
            font-family:'DM Sans',sans-serif; font-size:0.875rem;
            font-weight:600; transition:all 0.2s;
        }
        .welcome-btn.primary { background:white; color:#0d5a9e; }
        .welcome-btn.primary:hover { background:#f0f9ff; transform:translateY(-2px); }
        .welcome-btn.secondary {
            background:rgba(255,255,255,0.15); color:white;
            border:1px solid rgba(255,255,255,0.25);
        }
        .welcome-btn.secondary:hover { background:rgba(255,255,255,0.22); }

        .proxima-card {
            background:white; border-radius:14px; padding:1.5rem;
            border:1px solid rgba(0,0,0,0.06); margin-bottom:2rem;
            display:flex; align-items:center; gap:1.25rem;
        }
        .proxima-icon {
            width:50px; height:50px; border-radius:12px;
            background:#eff6ff; color:#0d5a9e;
            display:flex; align-items:center; justify-content:center; flex-shrink:0;
        }
        .proxima-info { flex:1; }
        .proxima-titulo { font-size:0.78rem; color:#9ca3af; font-weight:600;
            text-transform:uppercase; letter-spacing:0.05em; margin-bottom:4px; }
        .proxima-medico { font-size:1rem; font-weight:600; color:#111827; }
        .proxima-detalle { font-size:0.85rem; color:#6b7280; margin-top:2px; }
        .espera-badge {
            display:inline-block; padding:4px 10px; border-radius:12px;
            background:#fef9c3; color:#854d0e; font-size:0.78rem; font-weight:600;
        }

        .quick-links { display:grid; grid-template-columns:repeat(2,1fr); gap:1rem; }
        .quick-link-card {
            background:white; border-radius:14px; padding:1.5rem;
            border:1px solid rgba(0,0,0,0.06); text-decoration:none;
            display:flex; align-items:flex-start; gap:1rem; transition:all 0.2s;
        }
        .quick-link-card:hover {
            border-color:rgba(13,90,158,0.25);
            box-shadow:0 8px 24px rgba(13,90,158,0.1);
            transform:translateY(-3px);
        }
        .ql-icon {
            width:42px; height:42px; border-radius:10px;
            display:flex; align-items:center; justify-content:center; flex-shrink:0;
        }
        .ql-icon.blue { background:#eff6ff; color:#0d5a9e; }
        .ql-icon.green { background:#f0fdf4; color:#059669; }
        .ql-icon.amber { background:#fffbeb; color:#d97706; }
        .ql-info h3 { font-size:0.9rem; font-weight:600; color:#111827; margin:0 0 4px; }
        .ql-info p  { font-size:0.8rem; color:#6b7280; margin:0; line-height:1.5; }

        [data-theme="dark"] .proxima-card,
        [data-theme="dark"] .quick-link-card { background:#1e293b; border-color:rgba(255,255,255,0.07); }
        [data-theme="dark"] .proxima-medico  { color:#f1f5f9; }
        [data-theme="dark"] .ql-info h3      { color:#f1f5f9; }
        [data-theme="dark"] .proxima-icon,
        [data-theme="dark"] .ql-icon.blue    { background:#1d3a5e; }
        [data-theme="dark"] .ql-icon.green   { background:#064e3b; }
        [data-theme="dark"] .ql-icon.amber   { background:#451a03; }
    </style>
</head>
<body class="app-body">
<%  request.setAttribute("currentPage", "dashboard"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <div class="welcome-hero">
            <div class="welcome-greeting">Bienvenido al sistema</div>
            <h1 class="welcome-title">¡Hola, <c:out value="${sessionScope.usuarioNom}"/>!</h1>
            <p class="welcome-desc">
                Gestiona tus citas médicas, consulta médicos disponibles
                y lleva el control de tu historial de salud.
            </p>
            <div class="welcome-actions">
                <a href="${pageContext.request.contextPath}/paciente/disponibilidad"
                   class="welcome-btn primary">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                        <line x1="16" y1="2" x2="16" y2="6"/>
                        <line x1="8"  y1="2" x2="8"  y2="6"/>
                        <line x1="3"  y1="10" x2="21" y2="10"/>
                    </svg>
                    Agendar cita
                </a>
                <a href="${pageContext.request.contextPath}/paciente/citas"
                   class="welcome-btn secondary">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12
                                 a2 2 0 0 0 2-2V8z"/>
                        <polyline points="14 2 14 8 20 8"/>
                    </svg>
                    Ver mis citas
                </a>
            </div>
        </div>

        <%-- Próxima cita --%>
        <c:choose>
            <c:when test="${not empty proximaCita}">
                <div class="proxima-card">
                    <div class="proxima-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2"
                             stroke-linecap="round" stroke-linejoin="round">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                            <line x1="16" y1="2" x2="16" y2="6"/>
                            <line x1="8"  y1="2" x2="8"  y2="6"/>
                            <line x1="3"  y1="10" x2="21" y2="10"/>
                        </svg>
                    </div>
                    <div class="proxima-info">
                        <div class="proxima-titulo">Próxima cita</div>
                        <div class="proxima-medico">
                            <c:out value="${proximaCita.medico.nombre}"/>
                            — <c:out value="${proximaCita.medico.especialidad}"/>
                        </div>
                        <div class="proxima-detalle">
                            <c:out value="${proximaCita.bloque.fecha}"/>
                            a las <c:out value="${proximaCita.bloque.horaInicio}"/>
                            <c:if test="${proximaCita.estado == 'EN_ESPERA_REASIGNACION'}">
                                &nbsp;<span class="espera-badge">En espera de reasignación</span>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="proxima-card">
                    <div class="proxima-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2"
                             stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="12" cy="12" r="10"/>
                            <line x1="12" y1="8" x2="12" y2="12"/>
                            <line x1="12" y1="16" x2="12.01" y2="16"/>
                        </svg>
                    </div>
                    <div class="proxima-info">
                        <div class="proxima-titulo">Sin citas activas</div>
                        <div class="proxima-medico">No tienes citas programadas</div>
                        <div class="proxima-detalle">
                            ¡Agenda tu primera cita con un médico disponible!
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <p class="section-title" style="font-family:'DM Serif Display',serif;
           font-size:1.2rem;color:#111827;margin:0 0 1rem;">
            Accesos rápidos
        </p>
        <div class="quick-links">
            <a href="${pageContext.request.contextPath}/paciente/disponibilidad"
               class="quick-link-card">
                <div class="ql-icon blue">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                    </svg>
                </div>
                <div class="ql-info">
                    <h3>Médicos disponibles</h3>
                    <p>Explora especialistas y consulta sus horarios publicados.</p>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/paciente/citas"
               class="quick-link-card">
                <div class="ql-icon green">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12
                                 a2 2 0 0 0 2-2V8z"/>
                        <polyline points="14 2 14 8 20 8"/>
                        <line x1="16" y1="13" x2="8" y2="13"/>
                        <line x1="16" y1="17" x2="8" y2="17"/>
                    </svg>
                </div>
                <div class="ql-info">
                    <h3>Mis citas activas</h3>
                    <p>Revisa y gestiona tus citas programadas o reagendadas.</p>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/paciente/citas?action=historial"
               class="quick-link-card">
                <div class="ql-icon amber">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"/>
                        <polyline points="12 6 12 12 16 14"/>
                    </svg>
                </div>
                <div class="ql-info">
                    <h3>Historial de citas</h3>
                    <p>Consulta el registro completo de tus consultas anteriores.</p>
                </div>
            </a>
        </div>

    </div>
</div>
</body>
</html>