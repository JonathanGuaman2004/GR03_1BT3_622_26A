<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Médicos — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        body { background:#f1f5f9; }
        .form-card {
            background:white; border-radius:16px; padding:2rem;
            border:1px solid rgba(0,0,0,0.06); max-width:560px;
            margin-bottom:2rem;
        }
        .form-group { margin-bottom:1.1rem; }
        .form-label {
            display:block; font-size:0.82rem; font-weight:600;
            color:#374151; margin-bottom:0.4rem;
        }
        .form-input {
            width:100%; padding:0.7rem 1rem;
            border:1.5px solid #e5e7eb; border-radius:9px;
            font-size:0.9rem; font-family:'DM Sans',sans-serif;
            background:#fafafa; box-sizing:border-box; transition:all 0.2s;
        }
        .form-input:focus {
            outline:none; border-color:#0d5a9e; background:white;
            box-shadow:0 0 0 3px rgba(13,90,158,0.1);
        }
        .btn-registrar {
            padding:0.7rem 1.6rem; background:#0d5a9e; color:white;
            border:none; border-radius:9px; font-size:0.9rem;
            font-weight:600; cursor:pointer; transition:all 0.2s;
        }
        .btn-registrar:hover { background:#084577; }
        .tabla-wrap {
            background:white; border-radius:16px;
            border:1px solid rgba(0,0,0,0.06); overflow:hidden;
        }
        .tabla-medicos {
            width:100%; border-collapse:collapse; font-size:0.875rem;
        }
        .tabla-medicos thead tr { background:#f8fafc; }
        .tabla-medicos th {
            padding:0.8rem 1rem; text-align:left; font-size:0.75rem;
            font-weight:600; text-transform:uppercase; color:#9ca3af;
        }
        .tabla-medicos td {
            padding:0.85rem 1rem;
            border-bottom:1px solid rgba(0,0,0,0.05); color:#374151;
        }
        .tabla-medicos tbody tr:last-child td { border-bottom:none; }
        .badge-activo    { background:#d1fae5; color:#065f46; padding:2px 9px;
                           border-radius:12px; font-size:0.75rem; font-weight:600; }
        .badge-suspendido{ background:#fee2e2; color:#991b1b; padding:2px 9px;
                           border-radius:12px; font-size:0.75rem; font-weight:600; }
        .btn-accion {
            padding:4px 10px; border-radius:7px; font-size:0.78rem;
            font-weight:600; text-decoration:none; margin-right:4px;
            transition:all 0.18s; display:inline-block;
        }
        .btn-credencial { background:#eff6ff; color:#1e40af;
                          border:1px solid #bfdbfe; }
        .btn-suspender  { background:#fff1f2; color:#be123c;
                          border:1px solid #fecdd3; }
        .credenciales-box {
            background:#f0fdf4; border:1px solid #22c55e; border-radius:12px;
            padding:1.25rem; margin-bottom:1.5rem;
        }
        .credenciales-box p { margin:0.25rem 0; font-size:0.9rem; color:#166534; }
        .credenciales-box strong { color:#14532d; }
        [data-theme="dark"] .form-card,
        [data-theme="dark"] .tabla-wrap { background:#1e293b; border-color:rgba(255,255,255,0.07); }
        [data-theme="dark"] .tabla-medicos th { color:#64748b; }
        [data-theme="dark"] .tabla-medicos td { color:#cbd5e1; }
    </style>
</head>
<body class="app-body">
<% request.setAttribute("currentPage", "medicos"); %>
<jsp:include page="/WEB-INF/includes/layout.jsp"/>

<div class="app-layout">
    <div class="app-main">

        <div class="page-header">
            <h1 class="page-title">Gestión de Médicos</h1>
            <p class="page-subtitle">Registra, gestiona credenciales y suspende médicos</p>
        </div>

        <%-- Mensajes globales --%>
        <c:if test="${not empty error}">
            <div class="auth-alert error" style="margin-bottom:1.25rem;">
                <c:out value="${error}"/>
            </div>
        </c:if>
        <c:if test="${not empty exito}">
            <div class="auth-alert success" style="margin-bottom:1.25rem;">
                <c:out value="${exito}"/>
            </div>
        </c:if>

        <%-- Bloque de credenciales generadas --%>
        <c:if test="${not empty medicoConCredenciales}">
            <div class="credenciales-box">
                <p><strong>Credenciales generadas para:
                    <c:out value="${medicoConCredenciales.nombre}"/></strong></p>
                <p>Email: <strong><c:out value="${medicoConCredenciales.email}"/></strong></p>
                <p>Contraseña temporal:
                    <strong><c:out value="${medicoConCredenciales.password}"/></strong></p>
                <p style="font-size:0.8rem;margin-top:0.5rem;">
                    Entregue estas credenciales al médico.
                    Deberá cambiar su contraseña en el primer inicio de sesión.
                </p>
            </div>
        </c:if>

        <%-- Formulario de registro --%>
        <div class="form-card">
            <h2 style="font-family:'DM Serif Display',serif;font-size:1.2rem;
                       margin:0 0 1.25rem;">Registrar nuevo médico</h2>

            <c:if test="${not empty medicoRegistrado}">
                <div class="auth-alert success" style="margin-bottom:1rem;">
                    Médico <strong><c:out value="${medicoRegistrado.nombre}"/></strong>
                    registrado exitosamente.
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/medicos"
                  method="post">
                <div class="form-group">
                    <label class="form-label" for="nombre">Nombre completo</label>
                    <input class="form-input" type="text" id="nombre"
                           name="nombre"
                           value="<c:out value='${datos.nombre}'/>"
                           placeholder="Dr. Juan Pérez" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="especialidad">Especialidad</label>
                    <input class="form-input" type="text" id="especialidad"
                           name="especialidad"
                           value="<c:out value='${datos.especialidad}'/>"
                           placeholder="Cardiología" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="nroLicencia">
                        Número de licencia</label>
                    <input class="form-input" type="text" id="nroLicencia"
                           name="nroLicencia"
                           value="<c:out value='${datos.nroLicencia}'/>"
                           placeholder="MED-010" required>
                </div>
                <button type="submit" class="btn-registrar">
                    Registrar médico
                </button>
            </form>
        </div>

        <%-- Tabla de médicos --%>
        <div class="tabla-wrap">
            <table class="tabla-medicos">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nombre</th>
                        <th>Especialidad</th>
                        <th>Licencia</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="m" items="${medicos}">
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
                                        <span class="badge-suspendido">
                                            SUSPENDIDO</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a class="btn-accion btn-credencial"
                                   href="${pageContext.request.contextPath}/admin/medicos?action=generarCredenciales&medicoId=${m.id}">
                                    Generar credenciales
                                </a>
                                <c:if test="${m.estado == 'ACTIVO'}">
                                    <a class="btn-accion btn-suspender"
                                       href="${pageContext.request.contextPath}/admin/medicos?action=suspender&medicoId=${m.id}">
                                        Suspender
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty medicos}">
                        <tr>
                            <td colspan="6" style="text-align:center;
                                color:#9ca3af;padding:2rem;">
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