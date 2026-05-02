<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cambiar Contraseña — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
</head>
<body class="auth-body">
<div class="auth-bg">
    <div class="blob blob-1"></div>
    <div class="blob blob-2"></div>
    <div class="blob blob-3"></div>
    <div class="grid-overlay"></div>
</div>

<div class="auth-card">
    <div class="auth-logo">
        <svg width="26" height="26" viewBox="0 0 28 28" fill="none">
            <rect width="28" height="28" rx="8" fill="#0d5a9e"/>
            <path d="M14 7v14M7 14h14" stroke="white" stroke-width="2.5"
                  stroke-linecap="round"/>
        </svg>
        MediCitas
    </div>

    <h1 class="auth-title">Cambiar contraseña</h1>
    <p class="auth-subtitle">
        Establece una nueva contraseña para continuar usando el sistema.
    </p>

    <c:if test="${not empty error}">
        <div class="auth-alert error">
            <c:out value="${error}"/>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/cambiar-password"
          method="post">
        <div class="auth-field">
            <label class="auth-label" for="passwordActual">
                Contraseña actual</label>
            <input class="auth-input" type="password"
                   id="passwordActual" name="passwordActual"
                   placeholder="Tu contraseña temporal" required>
        </div>
        <div class="auth-field">
            <label class="auth-label" for="passwordNueva">
                Nueva contraseña</label>
            <input class="auth-input" type="password"
                   id="passwordNueva" name="passwordNueva"
                   placeholder="Mínimo 6 caracteres" required minlength="6">
        </div>
        <div class="auth-field">
            <label class="auth-label" for="passwordConfirmacion">
                Confirmar nueva contraseña</label>
            <input class="auth-input" type="password"
                   id="passwordConfirmacion" name="passwordConfirmacion"
                   placeholder="Repite la nueva contraseña"
                   required minlength="6">
        </div>
        <button type="submit" class="auth-btn">Actualizar contraseña</button>
    </form>
</div>
</body>
</html>