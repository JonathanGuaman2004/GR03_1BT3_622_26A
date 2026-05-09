<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plantilla Semanal — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background: #f1f5f9; }
        .form-card {
            background: white; border-radius: 16px; padding: 2rem;
            border: 1px solid rgba(0,0,0,0.06); margin-bottom: 1.5rem;
        }
        .form-row { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 1rem; }
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
        .btn-agregar {
            padding: 0.7rem 1.6rem; background: #0d5a9e; color: white;
            border: none; border-radius: 9px; font-size: 0.9rem;
            font-weight: 600; cursor: pointer; transition: all 0.2s;
            font-family: 'DM Sans', sans-serif;
        }
        .btn-agregar:hover { background: #084577; }
        .tabla-wrap {
            background: white; border-radius: 16px;
            border: 1px solid rgba(0,0,0,0.06); overflow: hidden;
        }
        .tabla-plantilla { width: 100%; border-collapse: collapse; font-size: 0.875rem; }
        .tabla-plantilla thead tr { background: #f8fafc; }
        .tabla-plantilla th {
            padding: 0.8rem 1rem; text-align: left; font-size: 0.75rem;
            font-weight: 600; text-transform: uppercase; color: #9ca3af;
        }
        .tabla-plantilla td {
            padding: 0.85rem 1rem;
            border-bottom: 1px solid rgba(0,0,0,0.05); color: #374151;
        }
        .tabla-plantilla tbody tr:last-child td { border-bottom: none; }
        .btn-eliminar {
            padding: 3px 9px; border-radius: 7px; font-size: 0.78rem;
            font-weight: 600; background: #fff1f2; color: #be123c;
            border: 1px solid #fecdd3; text-decoration: none;
            transition: all 0.18s; display: inline-block;
        }
        .btn-eliminar:hover { background: #ffe4e6; }
        .selector-card {
            background: white; border-radius: 16px; padding: 1.5rem;
            border: 1px solid rgba(0,0,0,0.06); margin-bottom: 1.5rem;
            display: flex; align-items: center; gap: 1rem;
        }
        [data-theme="dark"] .form-card,
        [data-theme="dark"] .tabla-wrap,
        [data-theme="dark"] .selector-card { background: #1e293b; border-color: rgba(255,255,255,0.07); }
        [data-theme="dark"] .tabla-plantilla th { color: #64748b; }
        [data-theme="dark"] .tabla-plantilla td { color: #cbd5e1; }
    </style>
</head>
<body class="app-body">
<% request.setAttribute("currentPage", "plantilla"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <div class="page-header">
            <h1 class="page-title">Plantilla Semanal</h1>
            <p class="page-subtitle">Configura los días y franjas horarias de cada médico</p>
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

        <%-- Selector de médico --%>
        <div class="selector-card">
            <form method="get"
                  action="${pageContext.request.contextPath}/admin/plantilla"
                  style="display: flex; align-items: center; gap: 1rem; width: 100%;">
                <label class="form-label" style="margin: 0; white-space: nowrap;">
                    Seleccionar médico:
                </label>
                <select name="medicoId" class="form-select" style="max-width: 350px;"
                        onchange="this.form.submit()">
                    <option value="">-- Seleccione un médico --</option>
                    <c:forEach var="m" items="${medicos}">
                        <option value="${m.id}"
                            ${medicoSeleccionado != null && medicoSeleccionado.id == m.id ? 'selected' : ''}>
                            <c:out value="${m.nombre}"/> — <c:out value="${m.especialidad}"/>
                        </option>
                    </c:forEach>
                </select>
            </form>
        </div>

        <c:if test="${not empty medicoSeleccionado}">
            <%-- Formulario para agregar franja --%>
            <div class="form-card">
                <h2 style="font-family: 'DM Serif Display', serif; font-size: 1.1rem;
                           margin: 0 0 1.25rem;">
                    Agregar franja — <c:out value="${medicoSeleccionado.nombre}"/>
                </h2>

                <form action="${pageContext.request.contextPath}/admin/plantilla"
                      method="post">
                    <input type="hidden" name="medicoId"
                           value="${medicoSeleccionado.id}">

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label" for="diaSemana">Día de semana</label>
                            <select class="form-select" id="diaSemana" name="diaSemana" required>
                                <option value="">-- Seleccione --</option>
                                <option value="LUNES">Lunes</option>
                                <option value="MARTES">Martes</option>
                                <option value="MIERCOLES">Miércoles</option>
                                <option value="JUEVES">Jueves</option>
                                <option value="VIERNES">Viernes</option>
                                <option value="SABADO">Sábado</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="horaInicio">Hora inicio</label>
                            <input class="form-input" type="time" id="horaInicio"
                                   name="horaInicio" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="horaFin">Hora fin</label>
                            <input class="form-input" type="time" id="horaFin"
                                   name="horaFin" required>
                        </div>
                    </div>
                    <button type="submit" class="btn-agregar">Agregar franja</button>
                </form>
            </div>

            <%-- Tabla de franjas configuradas --%>
            <div class="tabla-wrap">
                <div style="padding: 1rem 1.25rem; border-bottom: 1px solid rgba(0,0,0,0.05);
                            font-size: 0.95rem; font-weight: 600; color: #111827;">
                    Franjas configuradas
                </div>
                <table class="tabla-plantilla">
                    <thead>
                        <tr>
                            <th>Día</th>
                            <th>Hora inicio</th>
                            <th>Hora fin</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="f" items="${plantilla}">
                            <tr>
                                <td><c:out value="${f.diaSemana}"/></td>
                                <td><c:out value="${f.horaInicio}"/></td>
                                <td><c:out value="${f.horaFin}"/></td>
                                <td>
                                    <a class="btn-eliminar"
                                       href="${pageContext.request.contextPath}/admin/plantilla?action=eliminar&franjaId=${f.id}&medicoId=${medicoSeleccionado.id}">
                                        Eliminar
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty plantilla}">
                            <tr>
                                <td colspan="4" style="text-align: center;
                                    color: #9ca3af; padding: 2rem;">
                                    No hay franjas configuradas para este médico.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </c:if>

    </div>
</div>
</body>
</html>