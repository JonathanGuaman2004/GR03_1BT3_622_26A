<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }
        .welcome-hero {
            background: linear-gradient(135deg, #0d5a9e 0%, #1e7eb8 60%, #10b981 100%);
            border-radius: 18px;
            padding: 2.5rem 2.5rem 2.5rem;
            color: white;
            margin-bottom: 2rem;
            position: relative;
            overflow: hidden;
        }
        .welcome-hero::before {
            content: '';
            position: absolute;
            top: -60px; right: -60px;
            width: 240px; height: 240px;
            background: rgba(255,255,255,0.06);
            border-radius: 50%;
        }
        .welcome-hero::after {
            content: '';
            position: absolute;
            bottom: -40px; right: 80px;
            width: 160px; height: 160px;
            background: rgba(255,255,255,0.04);
            border-radius: 50%;
        }
        .welcome-greeting {
            font-size: 0.85rem;
            font-weight: 500;
            letter-spacing: 0.06em;
            text-transform: uppercase;
            opacity: 0.75;
            margin-bottom: 0.5rem;
        }
        .welcome-title {
            font-family: 'DM Serif Display', serif;
            font-size: 2.2rem;
            font-weight: 400;
            margin: 0 0 0.75rem;
            line-height: 1.2;
            position: relative;
            z-index: 1;
        }
        .welcome-desc {
            font-size: 0.95rem;
            opacity: 0.85;
            max-width: 480px;
            line-height: 1.65;
            margin: 0 0 1.75rem;
            position: relative;
            z-index: 1;
        }
        .welcome-actions {
            display: flex;
            gap: 0.75rem;
            flex-wrap: wrap;
            position: relative;
            z-index: 1;
        }
        .welcome-btn {
            display: inline-flex;
            align-items: center;
            gap: 7px;
            padding: 0.65rem 1.4rem;
            border-radius: 9px;
            text-decoration: none;
            font-family: 'DM Sans', sans-serif;
            font-size: 0.875rem;
            font-weight: 600;
            transition: all 0.2s;
        }
        .welcome-btn.primary {
            background: white;
            color: #0d5a9e;
        }
        .welcome-btn.primary:hover {
            background: #f0f9ff;
            transform: translateY(-2px);
            box-shadow: 0 6px 18px rgba(0,0,0,0.15);
        }
        .welcome-btn.secondary {
            background: rgba(255,255,255,0.15);
            color: white;
            border: 1px solid rgba(255,255,255,0.25);
        }
        .welcome-btn.secondary:hover {
            background: rgba(255,255,255,0.22);
            transform: translateY(-2px);
        }
        .quick-stats {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 1rem;
            margin-bottom: 2rem;
        }
        .stat-card {
            background: white;
            border-radius: 14px;
            padding: 1.4rem 1.5rem;
            border: 1px solid rgba(0,0,0,0.06);
            display: flex;
            align-items: center;
            gap: 1rem;
            transition: box-shadow 0.2s;
        }
        .stat-card:hover {
            box-shadow: 0 8px 24px rgba(13,90,158,0.1);
        }
        .stat-icon {
            width: 46px; height: 46px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-shrink: 0;
        }
        .stat-icon.blue { background: #eff6ff; color: #0d5a9e; }
        .stat-icon.green { background: #f0fdf4; color: #059669; }
        .stat-icon.amber { background: #fffbeb; color: #d97706; }
        .stat-info { flex: 1; }
        .stat-num {
            font-family: 'DM Serif Display', serif;
            font-size: 1.5rem;
            color: #111827;
            line-height: 1;
            margin-bottom: 3px;
        }
        .stat-label { font-size: 0.78rem; color: #6b7280; }
        .section-title {
            font-family: 'DM Serif Display', serif;
            font-size: 1.2rem;
            font-weight: 400;
            color: #111827;
            margin: 0 0 1rem;
        }
        .quick-links {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1rem;
        }
        .quick-link-card {
            background: white;
            border-radius: 14px;
            padding: 1.5rem;
            border: 1px solid rgba(0,0,0,0.06);
            text-decoration: none;
            display: flex;
            align-items: flex-start;
            gap: 1rem;
            transition: all 0.2s;
        }
        .quick-link-card:hover {
            border-color: rgba(13,90,158,0.25);
            box-shadow: 0 8px 24px rgba(13,90,158,0.1);
            transform: translateY(-3px);
        }
        .ql-icon {
            width: 42px; height: 42px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-shrink: 0;
        }
        .ql-icon.blue { background: #eff6ff; color: #0d5a9e; }
        .ql-icon.green { background: #f0fdf4; color: #059669; }
        .ql-info h3 {
            font-size: 0.9rem;
            font-weight: 600;
            color: #111827;
            margin: 0 0 4px;
        }
        .ql-info p {
            font-size: 0.8rem;
            color: #6b7280;
            margin: 0;
            line-height: 1.5;
        }
        /* Dark mode overrides */
        [data-theme="dark"] .welcome-hero { box-shadow: 0 8px 32px rgba(0,0,0,0.4); }
        [data-theme="dark"] .stat-card,
        [data-theme="dark"] .quick-link-card {
            background: #1e293b;
            border-color: rgba(255,255,255,0.07);
        }
        [data-theme="dark"] .stat-num { color: #f1f5f9; }
        [data-theme="dark"] .section-title { color: #f1f5f9; }
        [data-theme="dark"] .ql-info h3 { color: #f1f5f9; }
        [data-theme="dark"] .stat-icon.blue { background: #1d3a5e; }
        [data-theme="dark"] .stat-icon.green { background: #064e3b; }
        [data-theme="dark"] .stat-icon.amber { background: #451a03; }
        [data-theme="dark"] .ql-icon.blue { background: #1d3a5e; }
        [data-theme="dark"] .ql-icon.green { background: #064e3b; }
    </style>
</head>
<body class="app-body">
<%
    request.setAttribute("currentPage", "inicio");
%>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <!-- Bienvenida hero -->
        <div class="welcome-hero">
            <div class="welcome-greeting">Bienvenido al sistema</div>
            <h1 class="welcome-title">¡Hola, <c:out value="${sessionScope.usuarioNom}"/>!</h1>
            <p class="welcome-desc">
                Estás en <strong>MediCitas</strong>, tu plataforma de gestión de citas médicas. Aquí puedes explorar médicos disponibles, agendar tus citas y llevar un seguimiento de tu historial de consultas.
            </p>
            <div class="welcome-actions">
                <a href="${pageContext.request.contextPath}/disponibilidad" class="welcome-btn primary">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
                    Agendar cita
                </a>
                <a href="${pageContext.request.contextPath}/citas" class="welcome-btn secondary">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>
                    Ver mis citas
                </a>
            </div>
        </div>

        <!-- Stats rápidas -->
        <div class="quick-stats">
            <div class="stat-card">
                <div class="stat-icon blue">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                </div>
                <div class="stat-info">
                    <div class="stat-num">3</div>
                    <div class="stat-label">Médicos disponibles</div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon green">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg>
                </div>
                <div class="stat-info">
                    <div class="stat-num">3</div>
                    <div class="stat-label">Especialidades</div>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon amber">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
                </div>
                <div class="stat-info">
                    <div class="stat-num">10</div>
                    <div class="stat-label">Horarios disponibles</div>
                </div>
            </div>
        </div>

        <!-- Accesos rápidos -->
        <p class="section-title">Accesos rápidos</p>
        <div class="quick-links">
            <a href="${pageContext.request.contextPath}/disponibilidad" class="quick-link-card">
                <div class="ql-icon blue">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                </div>
                <div class="ql-info">
                    <h3>Médicos disponibles</h3>
                    <p>Explora la lista de especialistas y consulta sus horarios disponibles para agendar tu próxima cita.</p>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/citas" class="quick-link-card">
                <div class="ql-icon green">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/></svg>
                </div>
                <div class="ql-info">
                    <h3>Mis citas</h3>
                    <p>Revisa el estado de tus citas programadas, pendientes y canceladas. Cancela cuando lo necesites.</p>
                </div>
            </a>
        </div>

    </div>
</div>

</body>
</html>
