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
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }
        .medicos-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
            gap: 1.25rem;
        }
        .medico-card {
            background: white;
            border-radius: 16px;
            padding: 1.5rem;
            border: 1px solid rgba(0,0,0,0.06);
            transition: all 0.22s;
            display: flex;
            flex-direction: column;
        }
        .medico-card:hover {
            border-color: rgba(13,90,158,0.25);
            box-shadow: 0 10px 28px rgba(13,90,158,0.1);
            transform: translateY(-4px);
        }
        .medico-avatar {
            width: 52px; height: 52px;
            border-radius: 50%;
            background: linear-gradient(135deg, #1e7eb8, #0d5a9e);
            color: white;
            font-size: 1.1rem;
            font-weight: 700;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 1rem;
            font-family: 'DM Sans', sans-serif;
        }
        .medico-nombre {
            font-size: 1rem;
            font-weight: 600;
            color: #111827;
            margin: 0 0 4px;
            font-family: 'DM Sans', sans-serif;
        }
        .medico-especialidad {
            font-size: 0.82rem;
            color: #0d5a9e;
            font-weight: 500;
            background: #eff6ff;
            display: inline-block;
            padding: 2px 9px;
            border-radius: 20px;
            margin-bottom: 0.75rem;
        }
        .medico-licencia {
            font-size: 0.78rem;
            color: #6b7280;
            margin-bottom: 1.25rem;
        }
        .medico-btn {
            margin-top: auto;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 6px;
            padding: 0.6rem 1.1rem;
            background: #0d5a9e;
            color: white;
            border-radius: 9px;
            text-decoration: none;
            font-size: 0.85rem;
            font-weight: 600;
            font-family: 'DM Sans', sans-serif;
            transition: all 0.18s;
        }
        .medico-btn:hover {
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
        .empty-state svg { color: #d1d5db; margin-bottom: 1rem; }
        [data-theme="dark"] .medico-card { background: #1e293b; border-color: rgba(255,255,255,0.07); }
        [data-theme="dark"] .medico-nombre { color: #f1f5f9; }
        [data-theme="dark"] .medico-especialidad { background: #1d3a5e; color: #93c5fd; }
        [data-theme="dark"] .empty-state { background: #1e293b; border-color: rgba(255,255,255,0.07); }
    </style>
</head>
<body class="app-body">
<%
    request.setAttribute("currentPage", "disponibilidad");
%>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">
        <div class="page-header">
            <h1 class="page-title">Médicos disponibles</h1>
            <p class="page-subtitle">Selecciona un médico para revisar sus horarios y agendar tu cita</p>
        </div>

        <c:if test="${not empty error}">
            <div class="auth-alert error" style="margin-bottom:1.5rem;">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${empty medicos}">
                <div class="empty-state">
                    <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg>
                    <p>No hay médicos registrados en el sistema en este momento. Intenta más tarde.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="medicos-grid">
                    <c:forEach var="m" items="${medicos}">
                        <div class="medico-card">
                            <%-- Calcular iniciales del médico --%>
                            <c:set var="nomMedico" value="${m.nombre}"/>
                            <c:set var="palabras" value="${fn:split(nomMedico, ' ')}"/>
                            <c:set var="iniciales">
                                <c:forEach var="palabra" items="${palabras}" end="1" varStatus="loop">
                                    <c:if test="${fn:length(palabra) > 0 && loop.index < 2}">
                                        <c:out value="${fn:substring(palabra, 0, 1)}"/>
                                    </c:if>
                                </c:forEach>
                            </c:set>

                            <div class="medico-avatar">
                                <c:choose>
                                    <c:when test="${fn:length(iniciales) > 0}">
                                        <c:out value="${fn:toUpperCase(iniciales)}"/>
                                    </c:when>
                                    <c:otherwise>DR</c:otherwise>
                                </c:choose>
                            </div>
                            <div class="medico-nombre"><c:out value="${m.nombre}"/></div>
                            <span class="medico-especialidad"><c:out value="${m.especialidad}"/></span>
                            <div class="medico-licencia">Lic. <c:out value="${m.nroLicencia}"/></div>
                            <a class="medico-btn"
                               href="${pageContext.request.contextPath}/disponibilidad?medicoId=${m.id}">
                                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="4" width="18" height="18" rx="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
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
