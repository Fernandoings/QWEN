# FinanceApp - Aplicación de Finanzas Personales para Android

## Descripción
FinanceApp es una aplicación completa de finanzas personales para Android, diseñada para ayudar a los usuarios a gestionar sus finanzas domésticas de manera eficiente. Incluye funcionalidades similares a Cashaw y otras aplicaciones líderes del mercado.

## Características Principales

### 1. Gestión de Transacciones
- Registro de ingresos y gastos
- Categorización personalizada
- Transacciones recurrentes (diarias, semanales, mensuales, anuales)
- Búsqueda y filtrado por fecha, categoría o tipo
- Historial completo de transacciones

### 2. Presupuestos
- Creación de presupuestos mensuales, semanales o anuales
- Seguimiento en tiempo real del gasto
- Alertas cuando se acerca al límite
- Presupuestos por categoría o generales
- Gráficos de progreso

### 3. Cuentas Múltiples
- Efectivo
- Cuentas bancarias
- Tarjetas de crédito
- Ahorros
- Inversiones
- Balance total consolidado

### 4. Análisis y Reportes
- Gráficos de gastos por categoría
- Tendencias mensuales
- Comparativas período vs período
- Exportación de datos (CSV, PDF)
- Dashboard personalizado

### 5. Características Adicionales
- Notificaciones push para recordatorios
- Copia de seguridad automática
- Exportar/Importar datos
- Multi-moneda
- Modo oscuro
- Biometría (huella/facial)
- Widgets para pantalla de inicio

## Arquitectura Técnica

### Stack Tecnológico
- **Lenguaje**: Kotlin
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Base de Datos**: Room Database (SQLite)
- **Navegación**: Navigation Component
- **UI**: Material Design 3
- **Corrutinas**: Para operaciones asíncronas
- **Flow**: Para streams de datos reactivos
- **WorkManager**: Para tareas programadas (transacciones recurrentes)

### Estructura del Proyecto
```
app/
├── src/main/
│   ├── java/com/financeapp/android/
│   │   ├── data/
│   │   │   ├── model/          # Data classes (Transaction, Budget, Account, Category)
│   │   │   ├── dao/            # Data Access Objects
│   │   │   ├── database/       # Room Database configuration
│   │   │   └── repository/     # Repository pattern implementation
│   │   ├── ui/
│   │   │   ├── home/           # Pantalla principal
│   │   │   ├── transactions/   # Gestión de transacciones
│   │   │   ├── budgets/        # Gestión de presupuestos
│   │   │   ├── analytics/      # Análisis y reportes
│   │   │   └── settings/       # Configuración
│   │   ├── util/               # Utilidades y helpers
│   │   ├── MainActivity.kt
│   │   ├── FinanceApp.kt       # Application class
│   │   └── RecurringTransactionWorker.kt
│   ├── res/
│   │   ├── layout/             # XML layouts
│   │   ├── values/             # Colors, strings, themes
│   │   ├── drawable/           # Assets gráficos
│   │   ├── menu/               # Menús de navegación
│   │   └── navigation/         # Navigation graph
│   └── AndroidManifest.xml
└── build.gradle
```

## Funcionalidades Detalladas

### Transacciones
- **Agregar**: Formulario con monto, tipo, categoría, descripción, fecha y cuenta
- **Editar**: Modificar cualquier transacción existente
- **Eliminar**: Borrado seguro con confirmación
- **Recurrentes**: Configuración de frecuencia y duración
- **Adjuntos**: Posibilidad de agregar fotos de recibos

### Presupuestos
- **Límites**: Establecer límites de gasto por categoría
- **Períodos**: Diario, semanal, mensual, anual o personalizado
- **Alertas**: Notificaciones al 80% y 100% del presupuesto
- **Rollover**: Opción de llevar saldo no utilizado al siguiente período

### Categorías
- **Predeterminadas**: Comida, Transporte, Servicios, Entretenimiento, etc.
- **Personalizadas**: Crear categorías propias con iconos y colores
- **Subcategorías**: Jerarquía de categorías ilimitada
- **Iconos**: Biblioteca de iconos integrada

### Seguridad
- **Bloqueo**: PIN, patrón o biometría
- **Encriptación**: Datos encriptados localmente
- **Backup**: Copia de seguridad en la nube opcional

## Mejoras Continuas Planificadas

### Versión 1.1 (Próximamente)
- Integración con bancos (API open banking)
- Reconocimiento OCR de recibos
- Predicción de gastos con IA
- Metas de ahorro

### Versión 1.2
- Modo multi-usuario (familias)
- Compartición de gastos
- Sincronización en tiempo real

### Versiones Futuras
- Inversiones y seguimiento de portfolio
- Criptomonedas
- Planificación financiera a largo plazo
- Asistente financiero con IA

## Requisitos del Sistema
- Android 7.0 (API 24) o superior
- 100 MB de espacio libre
- Conexión a internet (opcional, para backup en la nube)

## Instalación y Desarrollo

### Prerrequisitos
- Android Studio Arctic Fox o superior
- JDK 17
- SDK de Android 34

### Pasos para Compilar
1. Clonar el repositorio
2. Abrir en Android Studio
3. Sincronizar Gradle
4. Ejecutar en emulador o dispositivo físico

### Dependencias Principales
```gradle
- androidx.room:room-runtime:2.6.0
- androidx.navigation:navigation-fragment-ktx:2.7.5
- com.google.android.material:material:1.10.0
- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
- com.github.PhilJay:MPAndroidChart:v3.1.0
- androidx.work:work-runtime-ktx:2.9.0
```

## Contribución
Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama para tu feature
3. Commit tus cambios
4. Push a la rama
5. Abre un Pull Request

## Licencia
Este proyecto está bajo licencia MIT.

## Contacto
Para preguntas o sugerencias, contactar a: support@financeapp.com

---

**Nota**: Esta aplicación almacena todos los datos localmente por defecto. La sincronización en la nube es opcional y requiere configuración adicional.
