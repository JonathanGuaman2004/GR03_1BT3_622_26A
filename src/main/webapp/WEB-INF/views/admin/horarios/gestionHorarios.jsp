<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Horarios — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }
        .form-card {
            background: white; border-radius: 16px; padding: 2rem;
            border: 1px solid rgba(0,0,0,0.06); max-width: 600px;
            margin-bottom: 1.5rem;
        }
        .form-group { margin-bottom: 1rem; }
        .form-label {
            display: block; font-size: 0.82rem; font-weight: 600;
            color: #374151; margin-bottom: 0.4rem;
        }
        .form-input, .form-select {
            width: 100%; padding: 0.7rem 1rem;
            border: 1.5px solid #e5e7eb; border-radius: 9px;
            font-size: 0.9rem; font-family: 'DM Sans', sans-serif;
            background: #fafafa; box-sizing: border-box; transition: all 0.2s;
        }
        .form-input:focus, .form-select:focus {
            outline: none; border-color: #0d5a9e; background: white;
            box-shadow: 0 0 0 3px rgba(13,90,158,0.1);
        }
        .btn-generar {
            padding: 0.7rem 1.6rem; background: #0d5a9e; color: white;
            border: none; border-radius: 9px; font-size: 0.9rem;
            font-weight: 600; cursor: pointer; transition: all 0.2s;
            font-family: 'DM Sans', sans-serif;
        }
        .btn-generar:hover { background: #084577; }
        .btn-publicar {
            padding: 0.7rem 1.6rem; background: #059669; color: white;
            border: none; border-radius: 9px; font-size: 0.9rem;
            font-weight: 600; cursor: pointer; transition: all 0.2s;
            text-decoration: none; display: inline-block; margin-top: 1rem;
        }
        .btn-publicar:hover { background: #047857; }
        .tabla-wrap {
            background: white; border-radius: 16px;
            border: 1px solid rgba(0,0,0,0.06); overflow: hidden;
            margin-top: 1.5rem;
        }
        .tabla-bloques { width: 100%; border-collapse: collapse; font-size: 0.875rem; }
        .tabla-bloques thead tr { background: #f8fafc; }
        .tabla-bloques th {
            padding: 0.8rem 1rem; text-align: left; font-size: 0.75rem;
            font-weight: 600; text-transform: uppercase; color: #9ca3af;
        }
        .tabla-bloques td {
            padding: 0.8rem 1rem;
            border-bottom: 1px solid rgba(0,0,0,0.05); color: #374151;
        }
        .tabla-bloques tbody tr:last-child td { border-bottom: none; }
        .badge-disponible { background: #d1fae5; color: #065f46; padding: 2px 8px;
                            border-radius: 12px; font-size: 0.75rem; font-weight: 600; }
        [data-theme="dark"] .form-card,
        [data-theme="dark"] .tabla-wrap { background: #1e293b; border-color: rgba(255,255,255,0.07); }
        [data-theme="dark"] .tabla-bloques th { color: #64748b; }
        [data-theme="dark"] .tabla-bloques td { color: #cbd5e1; }
    </style>
</head>
<body class="app-body">
<% request.setAttribute("currentPage", "horarios"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <div class="page-header">
            <h1 class="page-title">Gestión de Horarios</h1>
            <p class="page-subtitle">
                Genera y publica bloques horarios basados en la plantilla semanal
            </p>
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

        <%-- Formulario de generación --%>
        <div class="form-card">
            <h2 style="font-family: 'DM Serif Display', serif; font-size: 1.1rem;
                       margin: 0 0 1.25rem;">Generar bloques horarios</h2>

            <form action="${pageContext.request.contextPath}/admin/horarios"
                  method="post">
                <div class="form-group">
                    <label class="form-label" for="medicoId">Médico</label>
                    <select class="form-select" id="medicoId" name="medicoId" required>
                        <option value="">-- Seleccione un médico --</option>
                        <c:forEach var="m" items="${medicos}">
                            <option value="${m.id}"
                                ${medicoSeleccionado != null && medicoSeleccionado.id == m.id ? 'selected' : ''}>
                                <c:out value="${m.nombre}"/> — <c:out value="${m.especialidad}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label class="form-label" for="fechaInicio">Fecha inicio</label>
                    <input class="form-input" type="date" id="fechaInicio"
                           name="fechaInicio" value="<c:out value='${fechaInicio}'/>" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="fechaFin">Fecha fin</label>
                    <input class="form-input" type="date" id="fechaFin"
                           name="fechaFin" value="<c:out value='${fechaFin}'/>" required>
                </div>
                <button type="submit" class="btn-generar">Generar bloques</button>
            </form>
        </div>

        <%-- Previsualización de bloques generados --%>
        <c:if test="${not empty bloquesGenerados}">
            <div class="tabla-wrap">
                <div style="padding: 1rem 1.25rem; border-bottom: 1px solid rgba(0,0,0,0.05);
                            display: flex; justify-content: space-between; align-items: center;">
                    <span style="font-size: 0.95rem; font-weight: 600; color: #111827;">
                        Previsualización — ${bloquesGenerados.size()} bloques generados
                    </span>
                    <a class="btn-publicar"
                       href="${pageContext.request.contextPath}/admin/horarios?action=publicar&medicoId=${medicoSeleccionado.id}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}">
                        Confirmar y publicar
                    </a>
                </div>
                <table class="tabla-bloques">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Hora inicio</th>
                            <th>Hora fin</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="b" items="${bloquesGenerados}">
                            <tr>
                                <td><c:out value="${b.fecha}"/></td>
                                <td><c:out value="${b.horaInicio}"/></td>
                                <td><c:out value="${b.horaFin}"/></td>
                                <td><span class="badge-disponible">DISPONIBLE</span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

    </div>
</div>
</body>
</html>