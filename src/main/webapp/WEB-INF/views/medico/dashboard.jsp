<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Médico — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <style>
        body { background: #f1f5f9; }

        /* Hero */
        .medico-hero {
            background: linear-gradient(135deg, #064e3b 0%, #065f46 55%, #0d5a9e 100%);
            border-radius: 18px; padding: 2.5rem; color: white;
            margin-bottom: 2rem; position: relative; overflow: hidden;
        }
        .medico-hero::before {
            content: ''; position: absolute; top: -60px; right: -40px;
            width: 220px; height: 220px;
            background: rgba(255,255,255,0.06); border-radius: 50%;
        }
        .medico-hero::after {
            content: ''; position: absolute; bottom: -80px; left: 40%;
            width: 180px; height: 180px;
            background: rgba(255,255,255,0.04); border-radius: 50%;
        }
        .hero-greeting {
            font-size: 0.82rem; font-weight: 600; letter-spacing: 0.07em;
            text-transform: uppercase; opacity: 0.7; margin-bottom: 0.4rem;
        }
        .hero-nombre {
            font-family: 'DM Serif Display', serif; font-size: 2rem;
            font-weight: 400; margin: 0 0 0.4rem; line-height: 1.2;
            position: relative; z-index: 1;
        }
        .hero-especialidad {
            font-size: 0.9rem; opacity: 0.8;
            margin: 0 0 1.5rem; position: relative; z-index: 1;
        }
        .hero-licencia {
            display: inline-flex; align-items: center; gap: 6px;
            background: rgba(255,255,255,0.15); border: 1px solid rgba(255,255,255,0.2);
            border-radius: 20px; padding: 0.35rem 0.9rem;
            font-size: 0.78rem; font-weight: 500;
            position: relative; z-index: 1;
        }

        /* Stats grid */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 1rem; margin-bottom: 2rem;
        }
        .stat-card {
            background: white; border-radius: 14px; padding: 1.35rem;
            border: 1px solid rgba(0,0,0,0.06);
            display: flex; align-items: center; gap: 1rem;
            transition: all 0.2s;
        }
        .stat-card:hover {
            border-color: rgba(13,90,158,0.2);
            box-shadow: 0 6px 20px rgba(13,90,158,0.08);
            transform: translateY(-2px);
        }
        .stat-icon {
            width: 44px; height: 44px; border-radius: 11px;
            display: flex; align-items: center; justify-content: center; flex-shrink: 0;
        }
        .stat-icon.blue   { background: #eff6ff; color: #0d5a9e; }
        .stat-icon.green  { background: #f0fdf4; color: #059669; }
        .stat-icon.amber  { background: #fffbeb; color: #d97706; }
        .stat-icon.red    { background: #fff1f2; color: #be123c; }
        .stat-label {
            font-size: 0.72rem; font-weight: 600; color: #9ca3af;
            text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 3px;
        }
        .stat-value { font-size: 1.65rem; font-weight: 700; color: #111827; line-height: 1; }

        /* Próxima cita */
        .proxima-card {
            background: white; border-radius: 14px; padding: 1.5rem;
            border: 1px solid rgba(0,0,0,0.06); margin-bottom: 2rem;
            display: flex; align-items: center; gap: 1.25rem;
        }
        .proxima-icon {
            width: 50px; height: 50px; border-radius: 12px;
            background: #f0fdf4; color: #059669;
            display: flex; align-items: center; justify-content: center; flex-shrink: 0;
        }
        .proxima-titulo {
            font-size: 0.75rem; color: #9ca3af; font-weight: 600;
            text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 3px;
        }
        .proxima-paciente { font-size: 1rem; font-weight: 600; color: #111827; }
        .proxima-detalle  { font-size: 0.85rem; color: #6b7280; margin-top: 2px; }

        /* Quick links */
        .section-title {
            font-family: 'DM Serif Display', serif; font-size: 1.15rem;
            color: #111827; margin: 0 0 1rem;
        }
        .quick-links {
            display: grid; grid-template-columns: repeat(2, 1fr); gap: 1rem;
        }
        .quick-link-card {
            background: white; border-radius: 14px; padding: 1.5rem;
            border: 1px solid rgba(0,0,0,0.06); text-decoration: none;
            display: flex; align-items: flex-start; gap: 1rem; transition: all 0.2s;
        }
        .quick-link-card:hover {
            border-color: rgba(13,90,158,0.25);
            box-shadow: 0 8px 24px rgba(13,90,158,0.1);
            transform: translateY(-3px);
        }
        .ql-icon {
            width: 42px; height: 42px; border-radius: 10px;
            display: flex; align-items: center; justify-content: center; flex-shrink: 0;
        }
        .ql-icon.blue   { background: #eff6ff; color: #0d5a9e; }
        .ql-icon.green  { background: #f0fdf4; color: #059669; }
        .ql-icon.amber  { background: #fffbeb; color: #d97706; }
        .ql-info h3 { font-size: 0.9rem; font-weight: 600; color: #111827; margin: 0 0 3px; }
        .ql-info p  { font-size: 0.8rem; color: #6b7280; margin: 0; line-height: 1.5; }

        /* Dark mode */
        [data-theme="dark"] body { background: #0f172a; }
        [data-theme="dark"] .stat-card,
        [data-theme="dark"] .proxima-card,
        [data-theme="dark"] .quick-link-card { background: #1e293b; border-color: rgba(255,255,255,0.07); }
        [data-theme="dark"] .stat-value       { color: #f1f5f9; }
        [data-theme="dark"] .section-title    { color: #f1f5f9; }
        [data-theme="dark"] .proxima-paciente { color: #f1f5f9; }
        [data-theme="dark"] .ql-info h3       { color: #f1f5f9; }
        [data-theme="dark"] .stat-icon.blue   { background: #1d3a5e; }
        [data-theme="dark"] .stat-icon.green  { background: #064e3b; }
        [data-theme="dark"] .stat-icon.amber  { background: #451a03; }
        [data-theme="dark"] .stat-icon.red    { background: #4c0519; }
        [data-theme="dark"] .ql-icon.blue     { background: #1d3a5e; }
        [data-theme="dark"] .ql-icon.green    { background: #064e3b; }
        [data-theme="dark"] .ql-icon.amber    { background: #451a03; }
        [data-theme="dark"] .proxima-icon     { background: #064e3b; }

        @media (max-width: 900px) {
            .stats-grid  { grid-template-columns: repeat(2, 1fr); }
            .quick-links { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body class="app-body">
<% request.setAttribute("currentPage", "dashboard"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <%-- Hero del médico --%>
        <div class="medico-hero">
            <div class="hero-greeting">Panel médico</div>
            <h1 class="hero-nombre">
                ¡Hola, <c:out value="${sessionScope.usuarioNom}"/>!
            </h1>
            <p class="hero-especialidad">
                <c:out value="${medico.especialidad}"/> · MediCitas
            </p>
            <span class="hero-licencia">
                <svg width="13" height="13" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2"
                     stroke-linecap="round" stroke-linejoin="round">
                    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                    <polyline points="14 2 14 8 20 8"/>
                </svg>
                Licencia: <c:out value="${medico.nroLicencia}"/>
            </span>
        </div>

        <%-- Estadísticas --%>
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon blue">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                        <line x1="16" y1="2" x2="16" y2="6"/>
                        <line x1="8"  y1="2" x2="8"  y2="6"/>
                        <line x1="3"  y1="10" x2="21" y2="10"/>
                    </svg>
                </div>
                <div>
                    <div class="stat-label">Citas activas</div>
                    <div class="stat-value"><c:out value="${citasProgramadas}"/></div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon green">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <polyline points="20 6 9 17 4 12"/>
                    </svg>
                </div>
                <div>
                    <div class="stat-label">Completadas</div>
                    <div class="stat-value"><c:out value="${citasCompletadas}"/></div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon amber">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"/>
                        <polyline points="12 6 12 12 16 14"/>
                    </svg>
                </div>
                <div>
                    <div class="stat-label">Horarios libres</div>
                    <div class="stat-value"><c:out value="${bloquesDisponibles}"/></div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon red">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <line x1="18" y1="6" x2="6"  y2="18"/>
                        <line x1="6"  y1="6" x2="18" y2="18"/>
                    </svg>
                </div>
                <div>
                    <div class="stat-label">Canceladas</div>
                    <div class="stat-value"><c:out value="${citasCanceladas}"/></div>
                </div>
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
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                            <circle cx="9" cy="7" r="4"/>
                        </svg>
                    </div>
                    <div>
                        <div class="proxima-titulo">Próxima consulta</div>
                        <div class="proxima-paciente">
                            <c:out value="${proximaCita.paciente.nombre}"/>
                        </div>
                        <div class="proxima-detalle">
                            <c:out value="${proximaCita.bloque.fecha}"/>
                            a las <c:out value="${proximaCita.bloque.horaInicio}"/>
                            · <c:out value="${proximaCita.motivo}"/>
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
                            <line x1="12" y1="8"  x2="12" y2="12"/>
                            <line x1="12" y1="16" x2="12.01" y2="16"/>
                        </svg>
                    </div>
                    <div>
                        <div class="proxima-titulo">Sin citas próximas</div>
                        <div class="proxima-paciente">No tienes consultas programadas</div>
                        <div class="proxima-detalle">
                            Los pacientes podrán agendar cuando tengas horarios publicados.
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <%-- Accesos rápidos --%>
        <p class="section-title">Accesos rápidos</p>
        <div class="quick-links">
            <a href="${pageContext.request.contextPath}/medico/citas"
               class="quick-link-card">
                <div class="ql-icon blue">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                        <polyline points="14 2 14 8 20 8"/>
                        <line x1="16" y1="13" x2="8" y2="13"/>
                        <line x1="16" y1="17" x2="8" y2="17"/>
                    </svg>
                </div>
                <div class="ql-info">
                    <h3>Mis citas</h3>
                    <p>Consulta y gestiona todas las citas que tienes agendadas.</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/medico/horarios"
               class="quick-link-card">
                <div class="ql-icon green">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                        <line x1="16" y1="2" x2="16" y2="6"/>
                        <line x1="8"  y1="2" x2="8"  y2="6"/>
                        <line x1="3"  y1="10" x2="21" y2="10"/>
                    </svg>
                </div>
                <div class="ql-info">
                    <h3>Gestionar horarios</h3>
                    <p>Publica y administra tus bloques de atención disponibles.</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/medico/citas?action=historial"
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
                    <h3>Historial de consultas</h3>
                    <p>Revisa el registro completo de pacientes atendidos.</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/medico/perfil"
               class="quick-link-card">
                <div class="ql-icon" style="background:#f5f3ff;color:#7c3aed;">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                        <circle cx="12" cy="7" r="4"/>
                    </svg>
                </div>
                <div class="ql-info">
                    <h3>Mi perfil</h3>
                    <p>Actualiza tu información profesional y datos de contacto.</p>
                </div>
            </a>
        </div>

    </div>
</div>
</body>
</html>
