package es.aitorbarros.tabla.controladores;

import es.aitorbarros.tabla.gestionBD.GestorBD;
import es.aitorbarros.tabla.modelos.Persona;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controlador principal de la aplicación.
 *
 * Se encarga de gestionar la interfaz gráfica y de realizar
 * las operaciones necesarias sobre la base de datos.
 *
 * Permite:
 * - Mostrar las personas almacenadas en MariaDB.
 * - Añadir nuevas personas.
 * - Eliminar una o varias personas.
 * - Recargar los datos de la base de datos.
 * - Validar los datos introducidos por el usuario.
 * - Mostrar los textos de la aplicación en español o inglés.
 *
 * @author Aitor Barros
 */
public class HelloController {

    /** ResourceBundle utilizado para los textos de la aplicación. */
    private ResourceBundle bundle;

    /** Selector de fecha de nacimiento. */
    @FXML
    private DatePicker DatePicker;

    /** Botón para añadir una persona. */
    @FXML
    private Button btn_add;

    /** Botón para eliminar las filas seleccionadas. */
    @FXML
    private Button btn_deleteSelectedRows;

    /** Botón para restaurar los datos de la tabla. */
    @FXML
    private Button btn_restoreRows;

    /** Columna que muestra el identificador. */
    @FXML
    private TableColumn<Persona, Integer> col_Id;

    /** Columna que muestra el nombre. */
    @FXML
    private TableColumn<Persona, String> col_FirstName;

    /** Columna que muestra el apellido. */
    @FXML
    private TableColumn<Persona, String> col_LastName;

    /** Columna que muestra la fecha de nacimiento. */
    @FXML
    private TableColumn<Persona, LocalDate> col_BirthDate;

    /** Etiqueta del nombre. */
    @FXML
    private Label lbl_firstName;

    /** Etiqueta del apellido. */
    @FXML
    private Label lbl_lastName;

    /** Etiqueta de la fecha de nacimiento. */
    @FXML
    private Label lbl_birthDate;

    /** Tabla donde se muestran las personas. */
    @FXML
    private TableView<Persona> tablaPersonas;

    /** Campo de texto para introducir el nombre. */
    @FXML
    private TextField txt_FirstName;

    /** Campo de texto para introducir el apellido. */
    @FXML
    private TextField txt_LastName;


    /**
     * Inicializa el controlador.
     *
     * Configura las columnas de la tabla, permite seleccionar
     * varias filas, impide seleccionar fechas futuras y carga
     * las personas almacenadas en la base de datos.
     */
    @FXML
    public void initialize() {

        /*
         * Cargar el idioma de la aplicación.
         *
         * Si el idioma del sistema es español se utiliza
         * textos_es.properties. Para cualquier otro idioma
         * se utiliza textos_en.properties.
         */
        bundle = ResourceBundle.getBundle(
                "textos",
                Locale.getDefault().getLanguage().equals("es")
                        ? new Locale("es")
                        : new Locale("en")
        );

        /*
         * Configurar las columnas de la tabla.
         *
         * Los nombres utilizados coinciden con los atributos
         * y métodos getter de la clase Persona.
         */
        col_Id.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        col_FirstName.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );

        col_LastName.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );

        col_BirthDate.setCellValueFactory(
                new PropertyValueFactory<>("birthDate")
        );

        /*
         * Permitir seleccionar varias personas
         * al mismo tiempo.
         */
        tablaPersonas.getSelectionModel()
                .setSelectionMode(SelectionMode.MULTIPLE);

        /*
         * No permitir seleccionar fechas posteriores
         * al día actual.
         */
        DatePicker.setDayCellFactory(picker ->
                new DateCell() {

                    @Override
                    public void updateItem(
                            LocalDate date,
                            boolean empty
                    ) {

                        super.updateItem(date, empty);

                        if (date != null &&
                                date.isAfter(LocalDate.now())) {

                            setDisable(true);
                        }
                    }
                }
        );

        /*
         * Cargar las personas almacenadas
         * en la base de datos.
         */
        cargarPersonas();
    }


    /**
     * Carga las personas almacenadas en MariaDB
     * y las muestra en la TableView.
     */
    private void cargarPersonas() {

        List<Persona> personas = GestorBD.obtenerPersonas();

        tablaPersonas.getItems().clear();

        tablaPersonas.getItems().addAll(personas);
    }


    /**
     * Añade una nueva persona a la base de datos.
     *
     * Primero obtiene los datos introducidos por el usuario
     * y comprueba que sean válidos. Después crea un objeto
     * Persona y lo inserta mediante GestorBD.
     */
    @FXML
    private void addPersona() {

        // Obtener los datos introducidos por el usuario.
        String nombre = txt_FirstName.getText().trim();

        String apellido = txt_LastName.getText().trim();

        LocalDate fecha = DatePicker.getValue();


        /*
         * Comprobar que todos los campos
         * hayan sido rellenados.
         */
        if (nombre.isEmpty()
                || apellido.isEmpty()
                || fecha == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "alert.incomplete.title",
                    "alert.incomplete.message"
            );

            return;
        }


        /*
         * Comprobar que el nombre solo contiene
         * letras y espacios.
         */
        if (!nombre.matches(
                "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+"
        )) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "alert.invalidName.title",
                    "alert.invalidName.message"
            );

            return;
        }


        /*
         * Comprobar que el apellido solo contiene
         * letras y espacios.
         */
        if (!apellido.matches(
                "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+"
        )) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "alert.invalidLastName.title",
                    "alert.invalidLastName.message"
            );

            return;
        }


        /*
         * Comprobar que la fecha de nacimiento
         * no sea posterior al día actual.
         */
        if (fecha.isAfter(LocalDate.now())) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "alert.invalidFutureDate.title",
                    "alert.invalidFutureDate.message"
            );

            return;
        }


        /*
         * El ID se pone a 0 porque MariaDB lo genera
         * automáticamente mediante AUTO_INCREMENT.
         */
        Persona persona = new Persona(
                0,
                nombre,
                apellido,
                fecha
        );


        /*
         * Intentar insertar la persona en MariaDB.
         */
        boolean insertada = GestorBD.insertarPersona(persona);


        if (insertada) {

            /*
             * Volver a cargar los datos desde la BD
             * para mostrar también el ID generado.
             */
            cargarPersonas();

            /*
             * Limpiar los campos del formulario.
             */
            txt_FirstName.clear();

            txt_LastName.clear();

            DatePicker.setValue(null);

            /*
             * Volver a colocar el cursor en el
             * campo del nombre.
             */
            txt_FirstName.requestFocus();

        } else {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "alert.database.title",
                    "alert.database.message"
            );
        }
    }


    /**
     * Elimina las personas seleccionadas de la tabla
     * y también de la base de datos.
     *
     * Se pueden seleccionar varias filas gracias a la
     * selección múltiple configurada en initialize().
     */
    @FXML
    private void deleteSelectedRows() {

        /*
         * Obtener las personas seleccionadas.
         */
        List<Persona> seleccionadas =
                tablaPersonas.getSelectionModel()
                        .getSelectedItems();


        /*
         * Si no hay ninguna persona seleccionada,
         * no se realiza ninguna operación.
         */
        if (seleccionadas.isEmpty()) {
            return;
        }


        /*
         * Eliminar cada persona de la base de datos.
         */
        for (Persona persona : seleccionadas) {

            GestorBD.eliminarPersona(
                    persona.getId()
            );
        }


        /*
         * Volver a cargar los datos desde MariaDB.
         */
        cargarPersonas();
    }


    /**
     * Recarga las personas almacenadas en la base de datos.
     *
     * Este método sustituye al antiguo sistema que introducía
     * manualmente las cinco personas iniciales.
     */
    @FXML
    private void restoreRows() {

        cargarPersonas();
    }


    /**
     * Muestra una ventana de alerta utilizando los textos
     * definidos en el ResourceBundle.
     *
     * @param tipo tipo de alerta que se mostrará
     * @param titulo clave del título dentro del ResourceBundle
     * @param mensaje clave del mensaje dentro del ResourceBundle
     */
    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje
    ) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(
                bundle.getString(titulo)
        );

        alerta.setHeaderText(null);

        alerta.setContentText(
                bundle.getString(mensaje)
        );

        alerta.showAndWait();
    }
}