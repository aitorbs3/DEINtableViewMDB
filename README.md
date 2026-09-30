# TableView - Gestión de Personas

## 1. Descripción del ejercicio

El objetivo de este ejercicio es crear una aplicación de escritorio utilizando **JavaFX** y **Scene Builder**, tomando como referencia la interfaz proporcionada en el ejercicio.

La aplicación permite gestionar una lista de personas mediante un formulario y una tabla (`TableView`), utilizando una base de datos **MariaDB** para almacenar los datos.

Las funciones principales son:

- Introducir nombre y apellido.
- Seleccionar una fecha de nacimiento.
- Añadir personas a la base de datos.
- Mostrar las personas almacenadas en MariaDB.
- Seleccionar una o varias filas.
- Eliminar las personas seleccionadas de la base de datos.
- Recargar los datos almacenados.
- Validar los datos introducidos.
- Utilizar la aplicación en español o inglés.
- Detectar automáticamente el idioma del sistema.
- Gestionar la conexión con MariaDB mediante JDBC.
- Utilizar variables de entorno para las credenciales de la base de datos.

---

## 2. Tecnologías utilizadas

Para realizar el ejercicio se han utilizado:

- **Java 25**
- **JavaFX 21.0.6**
- **FXML**
- **Scene Builder**
- **Maven**
- **IntelliJ IDEA**
- **MariaDB**
- **JDBC**
- **MariaDB Java Client**
- **ResourceBundle**
- Archivos `.properties`
- **dotenv-java**
- Archivos `.env` y `.env.example`

---

## 3. ¿Cómo funciona?

La aplicación funciona mediante una interfaz gráfica creada con **JavaFX y Scene Builder**.

A diferencia de la versión inicial del ejercicio, los datos ya no se almacenan directamente en el programa, sino que se guardan en una base de datos **MariaDB**.

Al iniciar la aplicación se realiza una consulta a la base de datos y las personas almacenadas se muestran automáticamente en el `TableView`.

El usuario puede introducir los datos de una persona mediante los campos de texto y el selector de fecha.

Al pulsar el botón **Add**, el programa comprueba que los datos sean correctos. Si todo es válido, se crea un objeto `Persona` y se inserta en la base de datos mediante la clase `GestorBD`.

Después de insertar la persona, se vuelven a cargar los datos de MariaDB para actualizar la tabla.

La tabla muestra:

- ID
- Nombre
- Apellido
- Fecha de nacimiento

El identificador de cada persona es generado automáticamente por MariaDB mediante `AUTO_INCREMENT`.

También es posible seleccionar varias filas al mismo tiempo. Al pulsar **Delete Selected Rows**, se eliminan de MariaDB todas las personas seleccionadas.

El botón **Restore Rows** vuelve a consultar la base de datos y actualiza el contenido de la tabla.

---

## 4. Diseño de la interfaz

La interfaz gráfica se ha diseñado utilizando **Scene Builder**.

La ventana está formada principalmente por un `VBox`, dentro del cual se organizan diferentes `HBox`.

La estructura general es:

```text
VBox
│
├── HBox
│   ├── Label - First Name
│   ├── TextField
│   └── Button - Add
│
├── HBox
│   ├── Label - Last Name
│   └── TextField
│
├── HBox
│   ├── Label - Birth Date
│   └── DatePicker
│
├── HBox
│   ├── Button - Restore Rows
│   └── Button - Delete Selected Rows
│
└── TableView
    ├── Id
    ├── First Name
    ├── Last Name
    └── Birth Date