package es.aitorbarros.tabla.gestionBD;

import es.aitorbarros.tabla.modelos.Persona;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar la conexión y las operaciones
 * con la base de datos MariaDB.
 *
 * La información de conexión se obtiene del archivo .env
 * para evitar guardar las credenciales directamente en el código.
 *
 * @author Aitor Barros
 */
public class GestorBD {

    /** Variables de configuración obtenidas del archivo .env. */
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    /** URL de conexión a la base de datos. */
    private static final String URL = dotenv.get("DB_URL");

    /** Usuario de la base de datos. */
    private static final String USUARIO = dotenv.get("DB_USUARIO");

    /** Contraseña de la base de datos. */
    private static final String CONTRASENA = dotenv.get("DB_CONTRASENA");


    /**
     * Establece una conexión con MariaDB.
     *
     * @return conexión activa con la base de datos
     * @throws SQLException si no se puede establecer la conexión
     */
    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA
        );
    }


    /**
     * Obtiene todas las personas almacenadas en la tabla persona.
     *
     * @return lista de personas almacenadas en la base de datos
     */
    public static List<Persona> obtenerPersonas() {

        List<Persona> personas = new ArrayList<>();

        String sql = """
                SELECT id, first_name, last_name, birth_date
                FROM persona
                ORDER BY id
                """;

        try (
                Connection conexion = conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                int id = resultado.getInt("id");

                String nombre = resultado.getString("first_name");

                String apellido = resultado.getString("last_name");

                LocalDate fecha = resultado
                        .getDate("birth_date")
                        .toLocalDate();

                Persona persona = new Persona(
                        id,
                        nombre,
                        apellido,
                        fecha
                );

                personas.add(persona);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return personas;
    }


    /**
     * Inserta una nueva persona en la base de datos.
     *
     * @param persona persona que se quiere insertar
     * @return true si la inserción se realiza correctamente
     */
    public static boolean insertarPersona(Persona persona) {

        String sql = """
                INSERT INTO persona
                (first_name, last_name, birth_date)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conexion = conectar();
                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setString(
                    1,
                    persona.getFirstName()
            );

            sentencia.setString(
                    2,
                    persona.getLastName()
            );

            sentencia.setObject(
                    3,
                    persona.getBirthDate()
            );

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    /**
     * Elimina una persona de la base de datos utilizando su ID.
     *
     * @param id identificador de la persona que se quiere eliminar
     * @return true si la eliminación se realiza correctamente
     */
    public static boolean eliminarPersona(int id) {

        String sql = """
                DELETE FROM persona
                WHERE id = ?
                """;

        try (
                Connection conexion = conectar();
                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(1, id);

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
}