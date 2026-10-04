package pe.com.galaxy.enterprise.java.libs.lib_validation_core.model;

/**
 * Defines document types supported by the validation library.
 *
 * <p>The current implementation provides validation conventions
 * suitable for applications operating in Peru.</p>
 *
 * @since 1.0.0
 */
public enum DocumentType {

    /**
     * Documento Nacional de Identidad.
     */
    DNI,

    /**
     * Registro Único de Contribuyentes.
     */
    RUC,

    /**
     * Carné de Extranjería.
     */
    CE,

    /**
     * Passport.
     */
    PASSPORT
}