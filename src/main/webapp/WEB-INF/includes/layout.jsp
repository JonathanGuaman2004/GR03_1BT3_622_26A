<%-- /WEB-INF/includes/layout.jsp --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    String usuarioNom = (String) session.getAttribute("usuarioNom");
    String iniciales = "US";
    if (usuarioNom != null && !usuarioNom.isEmpty()) {
        String[] partes = usuarioNom.trim().split("\\s+");
        if (partes.length >= 2) {
            iniciales = String.valueOf(partes[0].charAt(0))
                      + String.valueOf(partes[1].charAt(0));
        } else {
            iniciales = usuarioNom.substring(0, Math.min(2, usuarioNom.length()));
        }
        iniciales = iniciales.toUpperCase();
    }
    request.setAttribute("iniciales", iniciales);

    String rol = (String) session.getAttribute("usuarioRol");
    if (rol == null) rol = "PACIENTE";
    request.setAttribute("usuarioRol", rol);
%>

<header class="app-topbar">
    <a href="${pageContext.request.contextPath}/paciente/dashboard" class="topbar-logo">
        <svg width="26" height="26" viewBox="0 0 28 28" fill="none">
            <rect width="28" height="28" rx="8" fill="#0d5a9e"/>
            <path d="M14 7v14M7 14h14" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
        </svg>
        MediCitas
    </a>
    <div class="topbar-spacer"></div>
    <div class="topbar-right">
        <div class="topbar-user">
            <div class="topbar-avatar"><c:out value="${iniciales}"/></div>
            <span class="topbar-username"><c:out value="${sessionScope.usuarioNom}"/></span>
        </div>
        <button class="topbar-btn" onclick="abrirConfiguracion()" title="Configuración">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="3"/>
                <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83
                         l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21
                         a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0
                         -1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0
                         4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65
                         0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1
                         2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0
                         1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51
                         1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83
                         l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21
                         a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/>
            </svg>
            Configuración
        </button>
        <a href="${pageContext.request.contextPath}/login?action=logout"
           class="topbar-btn danger" title="Cerrar sesión">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round">
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
                <polyline points="16 17 21 12 16 7"/>
                <line x1="21" y1="12" x2="9" y2="12"/>
            </svg>
            Cerrar sesión
        </a>
    </div>
</header>

<aside class="app-sidebar" id="app-sidebar">
    <div class="sidebar-section">
        <span class="sidebar-label">Principal</span>
        <a href="${pageContext.request.contextPath}/paciente/dashboard"
           class="sidebar-link ${currentPage == 'dashboard' ? 'active' : ''}">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round">
                <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
                <polyline points="9 22 9 12 15 12 15 22"/>
            </svg>
            Inicio
        </a>
    </div>

    <div class="sidebar-divider"></div>

    <c:if test="${sessionScope.usuarioRol == 'ADMINISTRADOR'}">
        <div class="sidebar-section">
            <span class="sidebar-label">Administración</span>
            <a href="${pageContext.request.contextPath}/admin/medicos"
               class="sidebar-link ${currentPage == 'medicos' ? 'active' : ''}">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2"
                     stroke-linecap="round" stroke-linejoin="round">
                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                    <circle cx="9" cy="7" r="4"/>
                    <line x1="19" y1="8" x2="19" y2="14"/>
                    <line x1="22" y1="11" x2="16" y2="11"/>
                </svg>
                Gestión de Médicos
            </a>
        </div>
    </c:if>

    <div class="sidebar-section">
        <span class="sidebar-label">Médicos y citas</span>
        <div class="sidebar-group
             ${currentPage == 'disponibilidad' || currentPage == 'citas'
               || currentPage == 'historial' ? 'open' : ''}"
             id="citas-group">
            <button class="sidebar-group-toggle" onclick="toggleGrupo('citas-group')">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2"
                     stroke-linecap="round" stroke-linejoin="round">
                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                    <line x1="16" y1="2" x2="16" y2="6"/>
                    <line x1="8"  y1="2" x2="8"  y2="6"/>
                    <line x1="3"  y1="10" x2="21" y2="10"/>
                </svg>
                Gestión de Citas
                <svg class="toggle-icon" width="14" height="14" viewBox="0 0 24 24"
                     fill="none" stroke="currentColor" stroke-width="2"
                     stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="6 9 12 15 18 9"/>
                </svg>
            </button>
            <div class="sidebar-group-items">
                <a href="${pageContext.request.contextPath}/paciente/disponibilidad"
                   class="sidebar-link ${currentPage == 'disponibilidad' ? 'active' : ''}">
                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                    </svg>
                    Médicos disponibles
                </a>
                <a href="${pageContext.request.contextPath}/paciente/citas"
                   class="sidebar-link ${currentPage == 'citas' ? 'active' : ''}">
                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12
                                 a2 2 0 0 0 2-2V8z"/>
                        <polyline points="14 2 14 8 20 8"/>
                        <line x1="16" y1="13" x2="8" y2="13"/>
                        <line x1="16" y1="17" x2="8" y2="17"/>
                    </svg>
                    Mis citas
                </a>
                <a href="${pageContext.request.contextPath}/paciente/citas?action=historial"
                   class="sidebar-link ${currentPage == 'historial' ? 'active' : ''}">
                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"/>
                        <polyline points="12 6 12 12 16 14"/>
                    </svg>
                    Historial
                </a>
            </div>
        </div>
    </div>
</aside>

<!-- MODAL CONFIGURACIÓN -->
<div class="modal-overlay" id="config-modal" onclick="cerrarConfigSiOverlay(event)">
    <div class="modal-card">
        <div class="modal-header">
            <h2 class="modal-title">Configuración</h2>
            <button class="modal-close" onclick="cerrarConfiguracion()">✕</button>
        </div>
        <div class="setting-section">
            <span class="setting-label">Tipo de pantalla</span>
            <div class="theme-toggle">
                <button class="theme-btn" id="theme-light" onclick="setTema('light')">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="5"/>
                        <line x1="12" y1="1"  x2="12" y2="3"/>
                        <line x1="12" y1="21" x2="12" y2="23"/>
                        <line x1="4.22"  y1="4.22"  x2="5.64"  y2="5.64"/>
                        <line x1="18.36" y1="18.36" x2="19.78" y2="19.78"/>
                        <line x1="1"  y1="12" x2="3"  y2="12"/>
                        <line x1="21" y1="12" x2="23" y2="12"/>
                        <line x1="4.22"  y1="19.78" x2="5.64"  y2="18.36"/>
                        <line x1="18.36" y1="5.64"  x2="19.78" y2="4.22"/>
                    </svg>
                    Claro
                </button>
                <button class="theme-btn" id="theme-dark" onclick="setTema('dark')">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round">
                        <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"/>
                    </svg>
                    Oscuro
                </button>
            </div>
        </div>
        <div class="setting-section">
            <span class="setting-label">Tamaño de letra</span>
            <div class="font-sizes">
                <button class="font-size-btn" data-size="xs" onclick="setFontSize('xs')">
                    <span class="sample">A</span><span>Muy pequeño</span>
                </button>
                <button class="font-size-btn" data-size="sm" onclick="setFontSize('sm')">
                    <span class="sample">A</span><span>Pequeño</span>
                </button>
                <button class="font-size-btn" data-size="md" onclick="setFontSize('md')">
                    <span class="sample">A</span><span>Mediano</span>
                </button>
                <button class="font-size-btn" data-size="lg" onclick="setFontSize('lg')">
                    <span class="sample">A</span><span>Grande</span>
                </button>
                <button class="font-size-btn" data-size="xl" onclick="setFontSize('xl')">
                    <span class="sample">A</span><span>Muy grande</span>
                </button>
            </div>
        </div>
    </div>
</div>

<script>
(function() {
    var tema     = localStorage.getItem('medicitas-theme')    || 'light';
    var fontSize = localStorage.getItem('medicitas-fontsize') || 'md';
    document.documentElement.setAttribute('data-theme',    tema);
    document.documentElement.setAttribute('data-fontsize', fontSize);
    window.addEventListener('DOMContentLoaded', function () {
        actualizarBotonesTema(tema);
        actualizarBotonesFont(fontSize);
        var grupo = document.getElementById('citas-group');
        if (grupo && grupo.classList.contains('open')) {
            grupo.querySelector('.sidebar-group-items').style.maxHeight = '300px';
        }
    });
})();

function toggleGrupo(id) {
    document.getElementById(id).classList.toggle('open');
}
function abrirConfiguracion() {
    document.getElementById('config-modal').classList.add('open');
    actualizarBotonesTema(localStorage.getItem('medicitas-theme')    || 'light');
    actualizarBotonesFont(localStorage.getItem('medicitas-fontsize') || 'md');
}
function cerrarConfiguracion() {
    document.getElementById('config-modal').classList.remove('open');
}
function cerrarConfigSiOverlay(e) {
    if (e.target === e.currentTarget) cerrarConfiguracion();
}
function setTema(tema) {
    document.documentElement.setAttribute('data-theme', tema);
    localStorage.setItem('medicitas-theme', tema);
    actualizarBotonesTema(tema);
}
function actualizarBotonesTema(tema) {
    document.querySelectorAll('.theme-btn').forEach(function(b) {
        b.classList.remove('active');
    });
    var a = document.getElementById('theme-' + tema);
    if (a) a.classList.add('active');
}
function setFontSize(size) {
    document.documentElement.setAttribute('data-fontsize', size);
    localStorage.setItem('medicitas-fontsize', size);
    actualizarBotonesFont(size);
}
function actualizarBotonesFont(size) {
    document.querySelectorAll('.font-size-btn').forEach(function(b) {
        b.classList.toggle('active', b.getAttribute('data-size') === size);
    });
}
</script>