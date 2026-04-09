# ColegioDB-Manager: Sistema de Gestión Escolar con JDBC y Swing

Este proyecto es una aplicación de escritorio robusta desarrollada en Java para la gestión integral de datos de un colegio. Implementa una arquitectura orientada a objetos que separa estrictamente la interfaz de usuario de la persistencia de datos.

## 🚀 Características Principales

* **Arquitectura DAO (Data Access Object):** Aislamiento total de la lógica de negocio del lenguaje SQL. Los DAOs implementan interfaces que permiten cambiar la persistencia sin afectar al resto del sistema.
* **DAO Manager:** Gestión centralizada de los DAOs para asegurar una estructura organizada y profesional.
* **Pool de Conexiones con HikariCP:** Implementación de Hikari para una gestión eficiente y de alto rendimiento de las conexiones a la base de datos.
* **Interfaz Gráfica Swing:** Pantallas intuitivas para la gestión de alumnos, profesores y matrículas.
* **Seguridad en el Acceso:** Pantalla de Login dinámico que solicita las credenciales de la base de datos antes de permitir el acceso al sistema.
  

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 11+
* **GUI:** Java Swing
* **Persistencia:** JDBC (Java Database Connectivity)
* **Pooling:** HikariCP
* **Patrones de Diseño:** DAO, Factory Method, Singleton.
  

## 📁 Estructura del Proyecto

El proyecto sigue una estructura de paquetes organizada por responsabilidades:

* `dao`: Define las interfaces (contratos) de acceso a datos.
* `implementacionesPOSTGRESQL`: Implementación específica de los métodos CRUD utilizando SQL para PostgreSQL.
* `modelo`: Clases que representan las entidades del colegio (Alumnos, Profesores, etc.).
* `vistas`: Interfaz gráfica (Swing) organizada por módulos:
    * `alumnos`: Gestión de estudiantes.
    * `asignaturas`: Gestión del currículo académico.
    * `profesores`: Gestión del cuerpo docente.
* `excepciones`: Control de errores personalizado para una gestión de fallos más limpia.
* `resources`: Configuración de HikariCP y recursos estáticos

  
## ⚙️ Cómo ejecutar

1. Clona el repositorio.
2. Asegúrate de tener instalado el driver JDBC correspondiente a tu base de datos (MySQL/PostgreSQL).
3. Configura las dependencias de HikariCP.
4. Ejecuta la clase principal (`Main` o `App`).
5. En la pantalla de bienvenida, introduce el **host, usuario y contraseña** de tu servidor de base de datos local para iniciar la sesión.

## 📝 Operaciones Soportadas (CRUD)
El sistema permite realizar operaciones de **Crear, Leer, Actualizar y Eliminar** en todas las entidades del colegio (Alumnos, Profesores, Asignaturas, etc.).
