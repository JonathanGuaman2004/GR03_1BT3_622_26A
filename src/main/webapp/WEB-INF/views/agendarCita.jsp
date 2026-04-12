<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmar Cita — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }
        .form-card {
            background: white;
            border-radius: 18px;
            padding: 2rem;
            border: 1px solid rgba(0,0,0,0.06);
            max-width: 540px;
        }
        .form-label {
            display:block; font-size:0.82rem; font-weight:600;
            color:#374151; margin-bottom:0.45rem;
        }
        .form-textarea {
            width:100%; padding:0.75rem 1rem;
            border:1.5px solid #e5e7eb; border-radius:10px;
            font-size:0.92rem; font-family:'DM Sans',sans-serif;
            color:#111827; background:#fafafa;
            transition:all 0.2s; box-sizing:border-box;
            resize:vertical; min-height:110px;
        }
        .form-textarea:focus {
            outline:none; border-color:#0d5a9e; background:white;
            box-shadow: 0 0 0 4px rgba(13,90,158,0.1);
        }
        .form-textarea::placeholder { color:#d1d5db; }
        .form-hint { font-size:0.75rem; color:#9ca3af; margin-top:0.4rem; }
        .form-actions { display:flex; gap:0.75rem; margin-top:1.5rem; flex-wrap:wrap; }
        .btn-submit {
            padding:0.75rem 1.75rem; background:#0d5a9e; color:white;
            border:none; border-radius:10px; font-size:0.9rem; font-weight:600;
            font-family:'DM Sans',sans-serif; cursor:pointer; transition:all 0.2s;
        }
        .btn-submit:hover { background:#084577; transform:translateY(-1px); box-shadow:0 4px 14px rgba(13,90,158,0.25); }
        .btn-back {
            display:inline-flex; align-items:center; gap:6px;
            color:#6b7280; text-decoration:none; font-size:0.875rem;
            margin-bottom:1.25rem; transition:color 0.18s;
        }
        .btn-back:hover { color:#0d5a9e; }
        .btn-link {
            display:inline-flex; align-items:center; gap:6px;
            color:#6b7280; text-decoration:none; font-size:0.875rem;
            padding:0.75rem 1.2rem; border-radius:10px; border:1px solid #e5e7eb;
            transition:all 0.18s; font-family:'DM Sans',sans-serif; font-weight:500;
        }
        .btn-link:hover { background:#f3f4f6; color:#374151; }
        [data-theme="dark"] .form-card { background:#1e293b; border-color:rgba(255,255,255,0.07); }
        [data-theme="dark"] .form-label { color:#cbd5e1; }
        [data-theme="dark"] .form-textarea { background:#0f172a; border-color:#334155; color:#e2e8f0; }
        [data-theme="dark"] .form-textarea:focus { border-color:#3b82f6; background:#0f172a; }
        [data-theme="dark"] .btn-link { border-color:#334155; color:#94a3b8; }
        [data-theme="dark"] .btn-link:hover { background:#334155; color:#e2e8f0; }
    </style>
</head>
<body class="app-body">
<%
    request.setAttribute("currentPage", "disponibilidad");
%>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">
        <a href="${pageContext.request.contextPath}/disponibilidad" class="btn-back">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="12" x2="5" y2="12"/><polyline points="12 19 5 12 12 5"/></svg>
            Seleccionar otro médico
        </a>

        <div class="page-header">
            <h1 class="page-title">Confirmar tu cita</h1>
            <p class="page-subtitle">Describe brevemente el motivo de tu consulta</p>
        </div>

        <c:if test="${not empty error}">
            <div class="auth-alert error" style="max-width:540px;margin-bottom:1.25rem;padding:0.85rem 1rem;border-radius:10px;font-size:0.875rem;border-left:3px solid #f43f5e;background:#fff1f2;color:#9f1239;">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/citas" method="post">
                <input type="hidden" name="horarioId" value="<c:out value='${horarioId}'/>">

                <div style="margin-bottom:1.25rem;">
                    <label class="form-label" for="motivo">Motivo de consulta</label>
                    <textarea class="form-textarea" id="motivo" name="motivo" rows="5"
                              placeholder="Describe brevemente los síntomas o razón de tu consulta. Máximo 500 caracteres."
                              maxlength="500" required></textarea>
                    <p class="form-hint">Esta información ayudará al médico a prepararse mejor para tu consulta.</p>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-submit">Confirmar cita</button>
                    <a href="${pageContext.request.contextPath}/disponibilidad" class="btn-link">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
