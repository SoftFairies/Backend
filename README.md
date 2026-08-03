# API REST - Sistema de Gestión de Lectura y Gamificación (SoftFairies Backend)

Backend desarrollado en **Java** utilizando **Spring Boot**, diseñado bajo una arquitectura modular y escalable para administrar bibliotecas virtuales, seguimiento de lectura, gamificación, sistema de recomendaciones y autenticación segura con JWT.

---

## 1. Tecnologías y Stack Utilizado

* **Lenguaje:** Java 21
* **Framework:** Spring Boot (Spring Data JPA, Spring WebMVC, Spring Security)
* **Base de Datos:** PostgreSQL
* **Seguridad:** JSON Web Token (JWT) + BCrypt para encriptación de contraseñas
* **Documentación:** Springdoc OpenAPI (Swagger UI)
* **Gestión de Archivos/Multimedia:** Cloudinary
* **Herramientas de Mapeo y Utilidades:** MapStruct, Lombok
* **Gestor de Dependencias:** Gradle
* **Inteligencia Artificial:** Gemini Pro (Integrado como parte del stack tecnológico)

---

## 2. Estructura del Proyecto (Arquitectura Modular)

El proyecto está organizado por **módulos de negocio**, separando claramente las capas de Dominio, Aplicación y Ecosistema/Infraestructura (API REST, Base de Datos):

```text
com.fairies.api.proyecto/
│
├── common/                # Utilidades globales (Seguridad, Excepciones, Configuración OpenAPI)
└── modules/               # Módulos del sistema
   ├── auth/              # Autenticación y registro (Login/Sign-up)
   ├── user/              # Gestión de usuarios y roles
   ├── book/              # Catálogo y búsqueda de libros (Integración externa)
   ├── library/           # Biblioteca personal del usuario y notas de lectura
   ├── readingsession/    # Sesiones y avance de lectura
   ├── readingStatus/     # Estados de lectura (Leyendo, Terminado, etc.)
   ├── gamification/      # Insignias y sistema de logros (Badges)
   ├── streak/            # Rachas de lectura diarias de los usuarios
   ├── recommendation/    # Preferencias y recomendaciones personalizadas
   ├── mailbox/           # Buzón de cartas / recomendaciones compartidas
   ├── author/            # Autores de los libros
   ├── format/            # Formatos de lectura (Físico, Ebook, Mangas, Commics)
   ├── gender/            # Géneros literarios
   ├── picture/           # Gestión de imágenes de perfil/portadas
   ├── reports/           # Métricas globales y del Dashboard
   └── Curiosityradar/    # Módulo de curiosidades y radar literario

```

---

## 3. Configuración y Puesta en Marcha

### Prerrequisitos

* **Java 21** instalado en tu equipo.


* **Gradle** (o puedes usar el wrapper incluido `./gradlew`).
* **PostgreSQL** configurado localmente o en un servicio en la nube (como Railway).

### Pasos para ejecutar:

1. **Clonar el repositorio:**
```bash
git clone <URL_DEL_REPOSITORIO>
cd <REPOSITORIO>

```


2. **Configurar las variables de entorno:**
   Crea un archivo `.env` en la raíz del proyecto basándote en el archivo `.env.example`:

3. **Ejecutar la aplicación:**
* En Linux / macOS:
```bash
./gradlew bootRun
```

* En Windows:
```cmd
gradlew.bat bootRun
```

La API estará corriendo por defecto en: `http://localhost:8080`

---

## 4. Documentación de la API (Swagger)

Una vez que la aplicación esté ejecutándose, puedes explorar y probar todos los endpoints directamente desde tu navegador usando la interfaz interactiva de Swagger UI:

**`http://localhost:8080/swagger-ui/index.html`**

---
## 5. Integración con Inteligencia Artificial (Gemini Pro)

El desarrollo de este backend se apoyó en **Gemini Pro** como herramienta de asistencia para mejorar la calidad y eficiencia del código. Su integración directa en el flujo de trabajo abarcó las siguientes áreas:

* **Lógica de Negocio:** Asistencia en el análisis, diseño y estructuración de la lógica interna para módulos complejos, específicamente en el sistema de **gamificación** (asignación de insignias) y el cálculo del seguimiento de **rachas de lectura** (streaks) e uso de una API externa.
* **Estandarización de DTOs:** Redacción y revisión de los mensajes de validación y respuesta dentro de los Data Transfer Objects (DTOs), asegurando que la comunicación de la API (mensajes de error, éxito y validaciones) mantenga un tono uniforme, claro y profesional.
* **Optimización de Procesos:** Apoyo en el análisis de código para la refactorización de métodos, simplificación de algoritmos y mejora general del rendimiento dentro de los servicios de la aplicación.

Este proyecto fue desarrollado con fines académicos para la **Universidad Politécnica de Chiapas** por el equipo de **SoftFairies**.

