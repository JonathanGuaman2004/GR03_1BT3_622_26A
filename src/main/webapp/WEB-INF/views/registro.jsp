<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Cuenta — MediCitas</title>
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

    <div class="auth-card" style="max-width:500px;">
        <div class="auth-logo">
            <svg width="26" height="26" viewBox="0 0 28 28" fill="none">
                <rect width="28" height="28" rx="8" fill="#0d5a9e"/>
                <path d="M14 7v14M7 14h14" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
            </svg>
            MediCitas
        </div>

        <h1 class="auth-title">Crea tu cuenta</h1>
        <p class="auth-subtitle">Únete a MediCitas y agenda tus citas médicas fácilmente</p>

        <c:if test="${not empty error}">
            <div class="auth-alert error">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/registro" method="post">

            <div style="display:grid;grid-template-columns:1fr 1fr;gap:0 1rem;">
                <div class="auth-field" style="grid-column:1/-1;">
                    <label class="auth-label" for="nombre">Nombre completo</label>
                    <input class="auth-input" type="text" id="nombre" name="nombre"
                           value="<c:out value='${datos.nombre}'/>"
                           placeholder="Ej: Ana María Torres García"
                           autocomplete="name" required maxlength="150">
                </div>

                <div class="auth-field">
                    <label class="auth-label" for="cedula">Cédula de identidad</label>
                    <input class="auth-input" type="text" id="cedula" name="cedula"
                           value="<c:out value='${datos.cedula}'/>"
                           placeholder="Ej: 1712345678"
                           autocomplete="off" required maxlength="20">
                </div>

                <div class="auth-field">
                    <label class="auth-label" for="telefono">Teléfono / Celular</label>
                    <input class="auth-input" type="text" id="telefono" name="telefono"
                           value="<c:out value='${datos.telefono}'/>"
                           placeholder="Ej: 0987654321"
                           autocomplete="tel" maxlength="20">
                </div>

                <div class="auth-field" style="grid-column:1/-1;">
                    <label class="auth-label" for="email">Correo electrónico</label>
                    <input class="auth-input" type="email" id="email" name="email"
                           value="<c:out value='${datos.email}'/>"
                           placeholder="tu.correo@ejemplo.com"
                           autocomplete="email" required maxlength="150">
                </div>

                <div class="auth-field" style="grid-column:1/-1;">
                    <label class="auth-label" for="password">Contraseña <span style="color:#9ca3af;font-weight:400;">(mínimo 6 caracteres)</span></label>
                    <input class="auth-input" type="password" id="password" name="password"
                           placeholder="Crea una contraseña segura"
                           autocomplete="new-password" required maxlength="100">
                </div>
            </div>

            <button type="submit" class="auth-btn">Crear cuenta</button>
        </form>

        <div class="auth-divider"></div>

        <div class="auth-footer">
            ¿Ya tienes cuenta?
            <a href="${pageContext.request.contextPath}/login">Iniciar sesión</a>
        </div>

        <div class="auth-footer" style="margin-top:0.5rem;">
            <a href="${pageContext.request.contextPath}/">← Volver al inicio</a>
        </div>
    </div>

</body>
</html>
