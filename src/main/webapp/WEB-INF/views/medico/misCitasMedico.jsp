<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Citas del Día — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }
        .cita-card {
            background: white; border-radius: 14px; padding: 1.25rem;
            border: 1px solid rgba(0,0,0,0.06); margin-bottom: 1rem;
            display: flex; align-items: center; gap: 1rem; transition: all 0.2s;
        }
        .cita-card:hover {
            border-color: rgba(13,90,158,0.2);
            box-shadow: 0 4px 14px rgba(13,90,158,0.08);
        }
        .cita-hora {
            font-family: 'DM Serif Display', serif; font-size: 1.3rem;
            color: #0d5a9e; min-width: 60px; text-align: center;
        }
        .cita-info { flex: 1; }
        .cita-paciente { font-size: 0.95rem; font-weight: 600; color: #111827; }
        .cita-motivo { font-size: 0.82rem; color: #6b7280; margin-top: 2px; }
        .badge-programada { background: #dbeafe; color: #1e40af; padding: 3px 9px;
            border-radius: 12px; font-size: 0.75rem; font-weight: 600; }
        .badge-reagendada { background: #d1fae5; color: #065f46; padding: 3px 9px;
            border-radius: 12px; font-size: 0.75rem; font-weight: 600; }
        .empty-agenda {
            background: white; border-radius: 14px; padding: 3rem;
            text-align: center; border: 1px solid rgba(0,0,0,0.06); color: #6b7280;
        }
        [data-theme="dark"] .cita-card,
        [data-theme="dark"] .empty-agenda { background: #1e293b; border-color: rgba(255,255,255,0.07); }
        [data-theme="dark"] .cita-paciente { color: #f1f5f9; }
    </style>
</head>
<body class="app-body">
<% request.setAttribute("currentPage", "citas"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">
        <div class="page-header">
            <h1 class="page-title">Mis citas de hoy</h1>
            <p class="page-subtitle">Citas programadas para el día de hoy en orden cronológico</p>
        </div>

        <c:choose>
            <c:when test="${empty citasDelDia}">
                <div class="empty-agenda">
                    <svg width="48" height="48" viewBox="0 0 24 24" fill="none"
                         stroke="#d1d5db" stroke-width="1.5"
                         stroke-linecap="round" stroke-linejoin="round"
                         style="margin-bottom:1rem;display:block;
                                margin-left:auto;margin-right:auto;">
                        <rect x="3" y="4" width="18" height="18" rx="2"/>
                        <line x1="16" y1="2" x2="16" y2="6"/>
                        <line x1="8"  y1="2" x2="8"  y2="6"/>
                        <line x1="3"  y1="10" x2="21" y2="10"/>
                    </svg>
                    <p>No tienes citas programadas para hoy.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="c" items="${citasDelDia}">
                    <div class="cita-card">
                        <div class="cita-hora">
                            <c:out value="${c.bloque.horaInicio}"/>
                        </div>
                        <div class="cita-info">
                            <div class="cita-paciente">
                                <c:out value="${c.paciente.nombre}"/>
                            </div>
                            <div class="cita-motivo">
                                <c:out value="${c.motivo}"/>
                            </div>
                        </div>
                        <div>
                            <c:choose>
                                <c:when test="${c.estado == 'PROGRAMADA'}">
                                    <span class="badge-programada">PROGRAMADA</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge-reagendada">
                                        <c:out value="${c.estado}"/>
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>