package es.aitorbarros.tabla;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase principal de la aplicación JavaFX.
 * Se encarga de iniciar la aplicación, cargar la interfaz
 * y configurar el idioma y el tamaño de la ventana.
 *
 * @author Aitor Barros
 */
public class HelloApplication extends Application {

    /**
     * Inicia la aplicación JavaFX.
     *
     * Detecta el idioma del sistema y carga el ResourceBundle
     * correspondiente. También carga la interfaz definida en
     * hello-view.fxml y configura la ventana.
     *
     * @param stage ventana principal de la aplicación
     * @throws IOException si no se puede cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Detectar el idioma del sistema
        Locale sistema = Locale.getDefault();

        Locale idioma;

        if (sistema.getLanguage().equals("es")) {
            idioma = new Locale("es");
        } else {
            idioma = new Locale("en");
        }

        // Cargar los textos del idioma correspondiente
        ResourceBundle bundle = ResourceBundle.getBundle(
                "textos",
                idioma
        );

        // Cargar la interfaz
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("hello-view.fxml"),
                bundle
        );

        Scene scene = new Scene(fxmlLoader.load());

        // Título
        stage.setTitle(bundle.getString("app.title"));

        // Tamaño inicial
        stage.setWidth(415);
        stage.setHeight(500);

        // Tamaño mínimo
        stage.setMinWidth(415);
        stage.setMinHeight(500);

        // Tamaño máximo
        stage.setMaxWidth(700);
        stage.setMaxHeight(700);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}