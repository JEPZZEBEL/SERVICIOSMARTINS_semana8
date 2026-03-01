# SERVICIOS MARTINS — Semana 8 (Desarrollo de Apps Móviles II)

Aplicación Android desarrollada con **Jetpack Compose** y arquitectura **MVVM**, que permite gestionar servicios (CRUD) con persistencia local (**Room**) e incluye integración de librerías externas, debugging profesional y diagnóstico de memoria.

---

## ✅ Funcionalidades
- CRUD de servicios: **crear, listar, buscar/filtrar, actualizar y eliminar**
- Persistencia local con **Room**
- Navegación con **Navigation Compose**
- Asincronía con **Coroutines** (`Dispatchers.IO`)
- **Consumo de API** con Retrofit (carga de frase) + OkHttp Logging
- **Carga de imágenes** remotas con Coil
- Debugging estructurado con **Timber** (tags: VM / DB / HTTP)
- **Simulación de error controlado** (título contiene “ERROR”) para evidencia en Logcat
- Diagnóstico de memoria con **LeakCanary** + **Memory Profiler**

---

## 🧱 Arquitectura (MVVM)
**Capas:**
- `ui/` → pantallas Compose (`ServicesScreen`, `DetailScreen`) y navegación (`AppNav`)
- `ui/ServicesViewModel` → validaciones, estados, llamadas a repositorios
- `repo/` → repositorio Room (`ServiceRepository`) y repositorio Retrofit (`QuoteRepository`)
- `data/` → Room (`Entity`, `DAO`, `Database`)
- `network/` → Retrofit (`ApiService`, `RetrofitClient`, DTO)

**Flujo:** UI → ViewModel → Repository → (Room/Retrofit) → UI

---

## 📦 Tecnologías / Librerías
- Jetpack Compose + Material 3
- Room
- Navigation Compose
- ViewModel + LiveData + StateFlow
- Coroutines
- Retrofit + Gson
- OkHttp Logging
- Coil
- Timber
- LeakCanary

---

## ▶️ Ejecución
1. Abrir en Android Studio
2. **Sync Gradle**
3. Ejecutar en emulador recomendado: **API 34 (Android 14)**
4. Run ▶️

---

## 🧪 Pruebas
- Unit tests: `app/src/test`
- UI tests (Instrumented): `app/src/androidTest` (ej: `ServicesFlowUiTest`)
- Ejecutar: click derecho sobre la carpeta de tests → Run

---

## 🔐 APK firmado
El APK release firmado se genera desde:
**Build > Generate Signed Bundle / APK > APK > Release**

Archivo generado: `app-release.apk`

---

## 🧾 Evidencias
Las capturas y evidencias se adjuntan en la carpeta:
`EVIDENCIAS_SEMANA8/`

Incluye:
- App funcionando (CRUD + búsqueda)
- Retrofit + Coil
- Logcat (VM/DB/HTTP)
- LeakCanary + Memory Profiler
- Tests PASSED
- APK firmado