package es.aitorbarros.tabla.modelos;

import java.time.LocalDate;

/**
 * Clase que representa una persona.
 *
 * Contiene los datos básicos de una persona:
 * identificador, nombre, apellido y fecha de nacimiento.
 *
 * @author Aitor Barros
 */
public class Persona {

    /** Identificador único de la persona. */
    private int id;

    /** Nombre de la persona. */
    private String firstName;

    /** Apellido de la persona. */
    private String lastName;

    /** Fecha de nacimiento de la persona. */
    private LocalDate birthDate;

    /**
     * Constructor de la clase Persona.
     *
     * @param id identificador de la persona
     * @param firstName nombre de la persona
     * @param lastName apellido de la persona
     * @param birthDate fecha de nacimiento de la persona
     */
    public Persona(int id, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * Obtiene el identificador de la persona.
     *
     * @return identificador de la persona
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return nombre de la persona
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Obtiene el apellido de la persona.
     *
     * @return apellido de la persona
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Obtiene la fecha de nacimiento de la persona.
     *
     * @return fecha de nacimiento
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }
}