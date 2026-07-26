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

Este proyecto fue desarrollado con fines académicos para la **Universidad Politécnica de Chiapas** por el equipo de **SoftFairies**.

