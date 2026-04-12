<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MediCitas — Sistema de Gestión de Citas Médicas</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/landing.css">
</head>
<body class="landing-body">

    <!-- Fondo decorativo -->
    <div class="landing-bg">
        <div class="blob blob-1"></div>
        <div class="blob blob-2"></div>
        <div class="blob blob-3"></div>
        <div class="grid-overlay"></div>
    </div>

    <!-- Nav superior -->
    <header class="landing-header">
        <div class="landing-logo">
            <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
                <rect width="28" height="28" rx="8" fill="#0d5a9e"/>
                <path d="M14 7v14M7 14h14" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
            </svg>
            <span>MediCitas</span>
        </div>
        <nav class="landing-nav">
            <a href="${pageContext.request.contextPath}/login" class="nav-link">Iniciar Sesión</a>
            <a href="${pageContext.request.contextPath}/registro" class="btn-landing-outline">Registrarse</a>
        </nav>
    </header>

    <!-- Hero principal -->
    <main class="landing-main">
        <div class="hero-content">
            <div class="hero-badge">
                <span class="badge-dot"></span>
                Sistema de salud digital
            </div>
            <h1 class="hero-title">
                Tu salud, <br>
                <em>a un clic</em><br>
                de distancia
            </h1>
            <p class="hero-subtitle">
                Agenda citas con los mejores especialistas, gestiona tu historial médico y recibe confirmaciones al instante. Todo desde un solo lugar.
            </p>
            <div class="hero-actions">
                <a href="${pageContext.request.contextPath}/registro" class="btn-hero-primary">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><line x1="19" y1="8" x2="19" y2="14"/><line x1="22" y1="11" x2="16" y2="11"/></svg>
                    Crear cuenta gratis
                </a>
                <a href="${pageContext.request.contextPath}/login" class="btn-hero-secondary">
                    Iniciar sesión
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/></svg>
                </a>
            </div>
            <div class="hero-stats">
                <div class="stat-item">
                    <span class="stat-num">3+</span>
                    <span class="stat-label">Especialidades</span>
                </div>
                <div class="stat-divider"></div>
                <div class="stat-item">
                    <span class="stat-num">100%</span>
                    <span class="stat-label">Digital</span>
                </div>
                <div class="stat-divider"></div>
                <div class="stat-item">
                    <span class="stat-num">24/7</span>
                    <span class="stat-label">Disponible</span>
                </div>
            </div>
        </div>

        <!-- Panel decorativo derecho -->
        <div class="hero-visual">
            <div class="visual-card card-main">
                <div class="vc-header">
                    <div class="vc-avatar">CM</div>
                    <div>
                        <div class="vc-name">Dr. Carlos Mendoza</div>
                        <div class="vc-spec">Medicina General</div>
                    </div>
                    <div class="vc-badge available">Disponible</div>
                </div>
                <div class="vc-slots">
                    <div class="slot active">Lun 08:00</div>
                    <div class="slot active">Lun 09:00</div>
                    <div class="slot taken">Mié 08:00</div>
                    <div class="slot active">Vie 14:00</div>
                </div>
                <div class="vc-cta">Ver horarios completos →</div>
            </div>
            <div class="visual-card card-confirm">
                <div class="confirm-icon">✓</div>
                <div class="confirm-text">
                    <strong>¡Cita confirmada!</strong>
                    <span>Notificación enviada a tu correo</span>
                </div>
            </div>
            <div class="visual-card card-spec">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#0d5a9e" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 12h-4l-3 9L9 3l-3 9H2"/></svg>
                <span>Cardiología · Pediatría · Medicina General</span>
            </div>
        </div>
    </main>

    <!-- Features -->
    <section class="landing-features">
        <div class="features-grid">
            <div class="feature-card">
                <div class="feature-icon">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
                </div>
                <h3>Agenda en segundos</h3>
                <p>Elige tu médico, selecciona el horario y confirma. Sin llamadas, sin esperas.</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                </div>
                <h3>Especialistas certificados</h3>
                <p>Médicos con licencia verificada en diversas especialidades clínicas.</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07A19.5 19.5 0 0 1 4.69 11 19.79 19.79 0 0 1 1.61 2.22 2 2 0 0 1 3.6.01L6.6 0a2 2 0 0 1 2 1.72c.127.96.361 1.903.7 2.81a2 2 0 0 1-.45 2.11L7.91 7.91a16 16 0 0 0 6.17 6.17l1.27-1.27a2 2 0 0 1 2.11-.45c.907.339 1.85.573 2.81.7A2 2 0 0 1 22 16.92z"/></svg>
                </div>
                <h3>Notificaciones instantáneas</h3>
                <p>Recibe confirmación inmediata de tu cita. Tanto tú como el médico son notificados.</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><polyline points="10 9 9 9 8 9"/></svg>
                </div>
                <h3>Historial organizado</h3>
                <p>Consulta todas tus citas pasadas y futuras desde tu perfil personal.</p>
            </div>
        </div>
    </section>

    <footer class="landing-footer">
        <p>© 2025 MediCitas · Sistema de Gestión de Citas Médicas</p>
    </footer>

</body>
</html>
