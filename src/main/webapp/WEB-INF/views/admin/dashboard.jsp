<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }

        /* ── Hero igual al del paciente ── */
        .welcome-hero {
            background: linear-gradient(135deg, #0d5a9e 0%, #1e7eb8 60%, #10b981 100%);
            border-radius: 18px; padding: 2.5rem; color: white;
            margin-bottom: 2rem; position: relative; overflow: hidden;
        }
        .welcome-hero::before {
            content: ''; position: absolute; top: -60px; right: -60px;
            width: 240px; height: 240px;
            background: rgba(255,255,255,0.06); border-radius: 50%;
        }
        .welcome-greeting {
            font-size: 0.85rem; font-weight: 500; letter-spacing: 0.06em;
            text-transform: uppercase; opacity: 0.75; margin-bottom: 0.5rem;
        }
        .welcome-title {
            font-family: 'DM Serif Display', serif; font-size: 2.2rem;
            font-weight: 400; margin: 0 0 0.75rem; line-height: 1.2;
            position: relative; z-index: 1;
        }
        .welcome-desc {
            font-size: 0.95rem; opacity: 0.85; max-width: 480px;
            line-height: 1.65; margin: 0 0 1.75rem; position: relative; z-index: 1;
        }
        .welcome-actions { display: flex; gap: 0.75rem; flex-wrap: wrap; position: relative; z-index: 1; }
        .welcome-btn {
            display: inline-flex; align-items: center; gap: 7px;
            padding: 0.65rem 1.4rem; border-radius: 9px; text-decoration: none;
            font-family: 'DM Sans', sans-serif; font-size: 0.875rem;
            font-weight: 600; transition: all 0.2s;
        }
        .welcome-btn.primary { background: white; color: #0d5a9e; }
        .welcome-btn.primary:hover { background: #f0f9ff; transform: translateY(-2px); }
        .welcome-btn.secondary {
            background: rgba(255,255,255,0.15); color: white;
            border: 1px solid rgba(255,255,255,0.25);
        }
        .welcome-btn.secondary:hover { background: rgba(255,255,255,0.22); }

        /* ── Tarjetas de estadísticas ── */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 1rem;
            margin-bottom: 2rem;
        }
        .stat-card {
            background: white; border-radius: 14px; padding: 1.4rem;
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
            width: 46px; height: 46px; border-radius: 12px;
            display: flex; align-items: center; justify-content: center; flex-shrink: 0;
        }
        .stat-icon.blue   { background: #eff6ff; color: #0d5a9e; }
        .stat-icon.green  { background: #f0fdf4; color: #059669; }
        .stat-icon.amber  { background: #fffbeb; color: #d97706; }
        .stat-icon.red    { background: #fff1f2; color: #be123c; }
        .stat-icon.purple { background: #faf5ff; color: #7c3aed; }
        .stat-info {}
        .stat-label {
            font-size: 0.75rem; font-weight: 600; color: #9ca3af;
            text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 3px;
        }
        .stat-value {
            font-size: 1.7rem; font-weight: 700; color: #111827; line-height: 1;
        }
        .stat-sub {
            font-size: 0.78rem; color: #6b7280; margin-top: 3px;
        }

        /* ── Sección de acciones rápidas ── */
        .section-header {
            font-family: 'DM Serif Display', serif; font-size: 1.2rem;
            color: #111827; margin: 0 0 1rem;
        }
        .quick-links {
            display: grid; grid-template-columns: repeat(2, 1fr); gap: 1rem;
            margin-bottom: 2rem;
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
        .ql-info h3 { font-size: 0.9rem; font-weight: 600; color: #111827; margin: 0 0 4px; }
        .ql-info p  { font-size: 0.8rem; color: #6b7280; margin: 0; line-height: 1.5; }

        /* ── Tabla últimos médicos ── */
        .tabla-wrap {
            background: white; border-radius: 16px;
            border: 1px solid rgba(0,0,0,0.06); overflow: hidden;
            margin-bottom: 2rem;
        }
        .tabla-header {
            padding: 1.1rem 1.25rem; border-bottom: 1px solid rgba(0,0,0,0.05);
            display: flex; justify-content: space-between; align-items: center;
        }
        .tabla-titulo { font-size: 0.95rem; font-weight: 600; color: #111827; }
        .tabla-link {
            font-size: 0.8rem; color: #0d5a9e; text-decoration: none; font-weight: 500;
        }
        .tabla-link:hover { text-decoration: underline; }
        .tabla-medicos { width: 100%; border-collapse: collapse; font-size: 0.875rem; }
        .tabla-medicos thead tr { background: #f8fafc; }
        .tabla-medicos th {
            padding: 0.75rem 1rem; text-align: left; font-size: 0.75rem;
            font-weight: 600; text-transform: uppercase; color: #9ca3af;
        }
        .tabla-medicos td {
            padding: 0.85rem 1rem;
            border-bottom: 1px solid rgba(0,0,0,0.05); color: #374151;
        }
        .tabla-medicos tbody tr:last-child td { border-bottom: none; }
        .badge-activo    { background: #d1fae5; color: #065f46; padding: 2px 9px;
                           border-radius: 12px; font-size: 0.75rem; font-weight: 600; }
        .badge-suspendido{ background: #fee2e2; color: #991b1b; padding: 2px 9px;
                           border-radius: 12px; font-size: 0.75rem; font-weight: 600; }

        /* ── Dark mode ── */
        [data-theme="dark"] .stat-card,
        [data-theme="dark"] .quick-link-card,
        [data-theme="dark"] .tabla-wrap { background: #1e293b; border-color: rgba(255,255,255,0.07); }
        [data-theme="dark"] .stat-value { color: #f1f5f9; }
        [data-theme="dark"] .section-header { color: #f1f5f9; }
        [data-theme="dark"] .tabla-titulo   { color: #f1f5f9; }
        [data-theme="dark"] .tabla-medicos th { color: #64748b; }
        [data-theme="dark"] .tabla-medicos td { color: #cbd5e1; }
        [data-theme="dark"] .ql-info h3       { color: #f1f5f9; }
        [data-theme="dark"] .stat-icon.blue   { background: #1d3a5e; }
        [data-theme="dark"] .stat-icon.green  { background: #064e3b; }
        [data-theme="dark"] .stat-icon.amber  { background: #451a03; }
        [data-theme="dark"] .stat-icon.red    { background: #4c0519; }
        [data-theme="dark"] .stat-icon.purple { background: #2e1065; }
        [data-theme="dark"] .ql-icon.blue     { background: #1d3a5e; }
        [data-theme="dark"] .ql-icon.green    { background: #064e3b; }
        [data-theme="dark"] .ql-icon.amber    { background: #451a03; }
    </style>
</head>
<body class="app-body">
<% request.setAttribute("currentPage", "dashboard"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <%-- Hero de bienvenida --%>
        <div class="welcome-hero">
            <div class="welcome-greeting">Panel de administración</div>
            <h1 class="welcome-title">¡Hola, <c:out value="${sessionScope.usuarioNom}"/>!</h1>
            <p class="welcome-desc">
                Gestiona médicos, supervisa las citas del sistema y
                mantén el control total de la plataforma MediCitas.
            </p>
            <div class="welcome-actions">
                <a href="${pageContext.request.contextPath}/admin/medicos"
                   class="welcome-btn primary">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <line x1="19" y1="8" x2="19" y2="14"/>
                        <line x1="22" y1="11" x2="16" y2="11"/>
                    </svg>
                    Gestionar médicos
                </a>
            </div>
        </div>

        <%-- Estadísticas generales --%>
        <p class="section-header">Resumen del sistema</p>
        <div class="stats-grid">

            <div class="stat-card">
                <div class="stat-icon blue">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <line x1="19" y1="8" x2="19" y2="14"/>
                        <line x1="22" y1="11" x2="16" y2="11"/>
                    </svg>
                </div>
                <div class="stat-info">
                    <div class="stat-label">Médicos</div>
                    <div class="stat-value"><c:out value="${totalMedicos}"/></div>
                    <div class="stat-sub"><c:out value="${medicosActivos}"/> activos</div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon green">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                    </svg>
                </div>
                <div class="stat-info">
                    <div class="stat-label">Pacientes</div>
                    <div class="stat-value"><c:out value="${totalPacientes}"/></div>
                    <div class="stat-sub">registrados</div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon amber">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                        <line x1="16" y1="2" x2="16" y2="6"/>
                        <line x1="8"  y1="2" x2="8"  y2="6"/>
                        <line x1="3"  y1="10" x2="21" y2="10"/>
                    </svg>
                </div>
                <div class="stat-info">
                    <div class="stat-label">Citas activas</div>
                    <div class="stat-value"><c:out value="${citasProgramadas}"/></div>
                    <div class="stat-sub">de <c:out value="${totalCitas}"/> totales</div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon red">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"/>
                        <line x1="15" y1="9" x2="9" y2="15"/>
                        <line x1="9"  y1="9" x2="15" y2="15"/>
                    </svg>
                </div>
                <div class="stat-info">
                    <div class="stat-label">Suspendidos</div>
                    <div class="stat-value"><c:out value="${medicosSuspendidos}"/></div>
                    <div class="stat-sub">médicos suspendidos</div>
                </div>
            </div>

        </div>

        <%-- Accesos rápidos --%>
        <p class="section-header">Accesos rápidos</p>
        <div class="quick-links">
            <a href="${pageContext.request.contextPath}/admin/medicos"
               class="quick-link-card">
                <div class="ql-icon blue">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <line x1="19" y1="8" x2="19" y2="14"/>
                        <line x1="22" y1="11" x2="16" y2="11"/>
                    </svg>
                </div>
                <div class="ql-info">
                    <h3>Gestión de médicos</h3>
                    <p>Registra nuevos médicos, genera credenciales y suspende accesos.</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/admin/medicos?filtro=suspendidos"
               class="quick-link-card">
                <div class="ql-icon amber">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"/>
                        <line x1="12" y1="8"  x2="12" y2="12"/>
                        <line x1="12" y1="16" x2="12.01" y2="16"/>
                    </svg>
                </div>
                <div class="ql-info">
                    <h3>Médicos suspendidos</h3>
                    <p>Revisa y gestiona los médicos con acceso suspendido al sistema.</p>
                </div>
            </a>
        </div>

        <%-- Tabla de últimos médicos registrados --%>
        <div class="tabla-wrap">
            <div class="tabla-header">
                <span class="tabla-titulo">Médicos recientes</span>
                <a href="${pageContext.request.contextPath}/admin/medicos"
                   class="tabla-link">Ver todos →</a>
            </div>
            <table class="tabla-medicos">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nombre</th>
                        <th>Especialidad</th>
                        <th>Licencia</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="m" items="${ultimosMedicos}">
                        <tr>
                            <td><c:out value="${m.id}"/></td>
                            <td><strong><c:out value="${m.nombre}"/></strong></td>
                            <td><c:out value="${m.especialidad}"/></td>
                            <td><c:out value="${m.nroLicencia}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${m.estado == 'ACTIVO'}">
                                        <span class="badge-activo">ACTIVO</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-suspendido">SUSPENDIDO</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty ultimosMedicos}">
                        <tr>
                            <td colspan="5" style="text-align:center;color:#9ca3af;padding:2rem;">
                                No hay médicos registrados aún.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

    </div>
</div>
</body>
</html>