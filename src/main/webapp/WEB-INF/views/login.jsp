<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión — MediCitas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
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
                <path d="M14 7v14M7 14h14" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
            </svg>
            MediCitas
        </div>

        <h1 class="auth-title">Bienvenido de nuevo</h1>
        <p class="auth-subtitle">Ingresa tus credenciales para acceder a tu cuenta</p>

        <c:if test="${not empty param.registered}">
            <div class="auth-alert success">
                ✓ Cuenta creada exitosamente. ¡Ya puedes iniciar sesión!
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="auth-alert error">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">

            <div class="auth-field">
                <label class="auth-label" for="email">Correo electrónico</label>
                <input class="auth-input" type="email" id="email" name="email"
                       value="<c:out value='${email}'/>"
                       placeholder="tu.correo@ejemplo.com"
                       autocomplete="email" required>
            </div>

            <div class="auth-field">
                <label class="auth-label" for="password">Contraseña</label>
                <input class="auth-input" type="password" id="password" name="password"
                       placeholder="Ingresa tu contraseña"
                       autocomplete="current-password" required>
            </div>

            <button type="submit" class="auth-btn">Iniciar sesión</button>
        </form>

        <div class="auth-divider"></div>

        <div class="auth-footer">
            ¿No tienes cuenta?
            <a href="${pageContext.request.contextPath}/registro">Crear cuenta gratis</a>
        </div>

        <div class="auth-footer" style="margin-top:0.5rem;">
            <a href="${pageContext.request.contextPath}/">← Volver al inicio</a>
        </div>
    </div>

</body>
</html>
