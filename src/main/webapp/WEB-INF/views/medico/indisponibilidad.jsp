<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Indisponibilidad — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }
        .form-card {
            background: white; border-radius: 16px; padding: 2rem;
            border: 1px solid rgba(0,0,0,0.06); max-width: 520px;
            margin-bottom: 1.5rem;
        }
        .form-group { margin-bottom: 1rem; }
        .form-label {
            display: block; font-size: 0.82rem; font-weight: 600;
            color: #374151; margin-bottom: 0.4rem;
        }
        .form-input, .form-textarea {
            width: 100%; padding: 0.7rem 1rem;
            border: 1.5px solid #e5e7eb; border-radius: 9px;
            font-size: 0.9rem; font-family: 'DM Sans', sans-serif;
            background: #fafafa; box-sizing: border-box; transition: all 0.2s;
        }
        .form-input:focus, .form-textarea:focus {
            outline: none; border-color: #0d5a9e; background: white;
            box-shadow: 0 0 0 3px rgba(13,90,158,0.1);
        }
        .form-textarea { resize: vertical; min-height: 80px; }
        .btn-registrar {
            padding: 0.7rem 1.6rem; background: #0d5a9e; color: white;
            border: none; border-radius: 9px; font-size: 0.9rem;
            font-weight: 600; cursor: pointer; transition: all 0.2s;
            font-family: 'DM Sans', sans-serif;
        }
        .btn-registrar:hover { background: #084577; }
        .advertencia {
            background: #fffbeb; border: 1px solid #fcd34d; border-radius: 10px;
            padding: 0.85rem 1rem; margin-bottom: 1.25rem;
            font-size: 0.85rem; color: #92400e;
        }
        .tabla-wrap {
            background: white; border-radius: 16px;
            border: 1px solid rgba(0,0,0,0.06); overflow: hidden;
            margin-top: 1.5rem;
        }
        .tabla-indisp { width: 100%; border-collapse: collapse; font-size: 0.875rem; }
        .tabla-indisp thead tr { background: #f8fafc; }
        .tabla-indisp th {
            padding: 0.8rem 1rem; text-align: left; font-size: 0.75rem;
            font-weight: 600; text-transform: uppercase; color: #9ca3af;
        }
        .tabla-indisp td {
            padding: 0.85rem 1rem;
            border-bottom: 1px solid rgba(0,0,0,0.05); color: #374151;
        }
        .tabla-indisp tbody tr:last-child td { border-bottom: none; }
        /* DESPUÉS */
        [data-theme="dark"] .form-card,
        [data-theme="dark"] .tabla-wrap     { background: #1e293b; border-color: rgba(255,255,255,0.07); }
        [data-theme="dark"] .tabla-indisp thead tr { background: #162032; }
        [data-theme="dark"] .tabla-indisp th      { color: #94a3b8; }
        [data-theme="dark"] .tabla-indisp td      { color: #cbd5e1; border-color: rgba(255,255,255,0.05); }
        [data-theme="dark"] .tabla-indisp tbody tr:hover { background: #253447; }
        [data-theme="dark"] .tabla-titulo         { color: #f1f5f9; }
        [data-theme="dark"] .form-label           { color: #cbd5e1; }
        [data-theme="dark"] .form-input,
        [data-theme="dark"] .form-textarea        { background: #0f172a; border-color: #334155; color: #e2e8f0; }
    </style>
</head>
<body class="app-body">
<% request.setAttribute("currentPage", "horarios"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <div class="page-header">
            <h1 class="page-title">Registrar indisponibilidad</h1>
            <p class="page-subtitle">
                Informa los días en que no podrás atender consultas
            </p>
        </div>

        <div class="advertencia">
            <strong>Importante:</strong> Solo puedes registrar indisponibilidad si tu
            cita más próxima afectada ocurre en más de 2 horas. Si no cumple esta
            condición, contacta al administrador.
        </div>

        <c:if test="${not empty error}">
            <div class="auth-alert error" style="margin-bottom: 1rem;">
                <c:out value="${error}"/>
            </div>
        </c:if>
        <c:if test="${not empty exito}">
            <div class="auth-alert success" style="margin-bottom: 1rem;">
                <c:out value="${exito}"/>
            </div>
        </c:if>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/medico/indisponibilidad"
                  method="post">
                <div class="form-group">
                    <label class="form-label" for="fecha">Fecha de indisponibilidad</label>
                    <input class="form-input" type="date" id="fecha" name="fecha"
                           value="<c:out value='${fechaPrevia}'/>" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="motivo">Motivo</label>
                    <textarea class="form-textarea" id="motivo" name="motivo"
                              placeholder="Ej: Enfermedad, congreso médico, trámite personal..."
                              required><c:out value="${motivoPrevio}"/></textarea>
                </div>
                <button type="submit" class="btn-registrar">Registrar indisponibilidad</button>
            </form>
        </div>

        <%-- Listado de indisponibilidades registradas --%>
        <div class="tabla-wrap">
            <div class="tabla-titulo"
                 style="padding: 1rem 1.25rem; border-bottom: 1px solid rgba(0,0,0,0.05);
            font-size: 0.95rem; font-weight: 600; color: #111827;">
                Indisponibilidades registradas
            </div>
            <table class="tabla-indisp">
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Motivo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" items="${indisponibilidades}">
                        <tr>
                            <td><c:out value="${i.fecha}"/></td>
                            <td><c:out value="${i.motivo}"/></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty indisponibilidades}">
                        <tr>
                            <td colspan="2" style="text-align: center;
                                color: #9ca3af; padding: 2rem;">
                                No tienes indisponibilidades registradas.
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