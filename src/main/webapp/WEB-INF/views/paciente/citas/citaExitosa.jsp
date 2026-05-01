<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cita Confirmada — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background:#f1f5f9; }
        .success-card {
            background:white; border-radius:20px; padding:2.5rem;
            border:1px solid rgba(0,0,0,0.06); max-width:520px;
            text-align:center;
        }
        .success-icon-wrap {
            width:72px; height:72px; border-radius:50%;
            background:#d1fae5; color:#059669; font-size:2rem;
            display:flex; align-items:center; justify-content:center;
            margin:0 auto 1.5rem;
            animation:bounceIn 0.5s cubic-bezier(0.34,1.56,0.64,1) both;
        }
        @keyframes bounceIn {
            from { opacity:0; transform:scale(0.5); }
            to   { opacity:1; transform:scale(1); }
        }
        .success-title {
            font-family:'DM Serif Display',serif; font-size:1.6rem;
            font-weight:400; color:#111827; margin:0 0 0.5rem;
        }
        .success-subtitle {
            font-size:0.875rem; color:#6b7280;
            margin:0 0 2rem; line-height:1.6;
        }
        .detail-grid {
            background:#f8fafc; border-radius:12px;
            padding:1.25rem; margin-bottom:1.75rem;
            display:grid; grid-template-columns:auto 1fr;
            gap:0.6rem 1rem; text-align:left;
        }
        .detail-key {
            font-size:0.78rem; color:#9ca3af; font-weight:500;
            text-transform:uppercase; letter-spacing:0.04em; padding-top:2px;
        }
        .detail-val { font-size:0.88rem; color:#111827; font-weight:500; }
        .estado-tag {
            display:inline-block; padding:2px 9px; border-radius:12px;
            font-size:0.75rem; font-weight:600;
            background:#dbeafe; color:#1e40af;
        }
        .success-actions {
            display:flex; gap:0.75rem; justify-content:center; flex-wrap:wrap;
        }
        .btn-primary {
            padding:0.65rem 1.4rem; background:#0d5a9e; color:white;
            border-radius:9px; text-decoration:none;
            font-size:0.875rem; font-weight:600; transition:all 0.2s;
        }
        .btn-primary:hover { background:#084577; transform:translateY(-1px); }
        .btn-secondary {
            padding:0.65rem 1.4rem; border:1px solid #e5e7eb; color:#374151;
            border-radius:9px; text-decoration:none;
            font-size:0.875rem; font-weight:500; transition:all 0.2s;
        }
        .btn-secondary:hover { background:#f3f4f6; }
        [data-theme="dark"] .success-card  { background:#1e293b; border-color:rgba(255,255,255,0.07); }
        [data-theme="dark"] .success-title { color:#f1f5f9; }
        [data-theme="dark"] .detail-grid   { background:#162032; }
        [data-theme="dark"] .detail-val    { color:#e2e8f0; }
        [data-theme="dark"] .btn-secondary { border-color:#334155; color:#94a3b8; }
        [data-theme="dark"] .btn-secondary:hover { background:#334155; }
    </style>
</head>
<body class="app-body">
<%  request.setAttribute("currentPage", "citas"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">
        <div class="success-card">
            <div class="success-icon-wrap">✓</div>
            <h1 class="success-title">¡Cita agendada!</h1>
            <p class="success-subtitle">
                Tu solicitud fue recibida correctamente.<br>
                Recibirás una notificación en tu correo electrónico.
            </p>

            <c:if test="${not empty citaConfirmada}">
                <div class="detail-grid">
                    <span class="detail-key">Médico</span>
                    <span class="detail-val">
                        <c:out value="${citaConfirmada.medico.nombre}"/>
                    </span>

                    <span class="detail-key">Especialidad</span>
                    <span class="detail-val">
                        <c:out value="${citaConfirmada.medico.especialidad}"/>
                    </span>

                    <span class="detail-key">Fecha</span>
                    <span class="detail-val">
                        <c:out value="${citaConfirmada.bloque.fecha}"/>
                    </span>

                    <span class="detail-key">Hora</span>
                    <span class="detail-val">
                        <c:out value="${citaConfirmada.bloque.horaInicio}"/>
                    </span>

                    <span class="detail-key">Motivo</span>
                    <span class="detail-val">
                        <c:out value="${citaConfirmada.motivo}"/>
                    </span>

                    <span class="detail-key">Estado</span>
                    <span class="detail-val">
                        <span class="estado-tag">PROGRAMADA</span>
                    </span>
                </div>
            </c:if>

            <div class="success-actions">
                <a href="${pageContext.request.contextPath}/paciente/citas"
                   class="btn-primary">Ver mis citas</a>
                <a href="${pageContext.request.contextPath}/paciente/disponibilidad"
                   class="btn-secondary">Agendar otra cita</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>