# Literalura

Literalura es una aplicación diseñada para gestionar y explorar información sobre libros, autores y estadísticas relacionadas. Este proyecto incluye consultas avanzadas a una base de datos, así como interacción con los usuarios para la búsqueda y manejo de datos literarios.

## Características principales

- **Gestión de libros:**
  - Agregar libros a la base de datos.
  - Evitar duplicados mediante verificaciones avanzadas.

- **Gestión de autores:**
  - Mostrar información detallada de autores (nombre, fecha de nacimiento, fallecimiento).
  - Identificar autores vivos en un año específico ingresado por el usuario.

- **Estadísticas literarias:**
  - Top 10 libros más descargados.
  - Resumen estadístico (promedio, máximo, mínimo de descargas).

- **Idiomas:**
  - Listar libros disponibles por idioma.

## Tecnologías utilizadas

- **Backend:**
  - Java 17
  - Spring Boot
  - JPA (Java Persistence API) con Hibernate
  
- **Base de datos:**
  - PostgreSQL (configurable para otras bases de datos SQL)

- **Dependencias principales:**
  - PostgreSQL Driver
  - Spring Data JPA

## Requisitos previos

- Java 17 o superior instalado.
- Maven para gestionar las dependencias y construir el proyecto.

## Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/Itoito0126/Literalura.git
   cd Literalura
   ```

2. Construye el proyecto con Maven:

   ```bash
   mvn clean install
   ```

3. Ejecuta la aplicación:

   ```bash
   mvn spring-boot:run
   ```

4. Accede a la consola interactiva para explorar las funcionalidades.

## Uso

1. **Inicio:**
   - Al iniciar, se presenta un menú con diferentes opciones para interactuar con la base de datos de libros y autores.

2. **Buscar libros:**
   - Ingresa el nombre de un libro para buscarlo en la base de datos o en la API externa (configurada en el proyecto).

3. **Mostrar estadísticas:**
   - Consulta el top 10 de libros más descargados y estadísticas como promedio, máximo y mínimo de descargas.

4. **Idiomas:**
   - Filtra libros por idioma ingresando una opción válida (`es`, `en`, `fr`, `pt`).

5. **Autores vivos en un año:**
   - Especifica un año para obtener la lista de autores que estaban vivos durante ese tiempo.

## Configuración adicional

- **Base de datos:**
  - Por defecto, utiliza H2 para desarrollo. Puedes cambiar la configuración en el archivo `application.properties` para usar otras bases de datos como MySQL o PostgreSQL.

- **API externa:**
  - La aplicación puede integrarse con APIs externas para enriquecer la información. Configura las URL en las propiedades del proyecto.
