package pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.result;

/**
 * Represents the result produced by an application use case.
 *
 * <p>An application result contains the returned data together with a code
 * and human-readable message describing the outcome of the operation.</p>
 *
 * <p>This type can be used by application services and handlers to return
 * structured outcomes without coupling the application layer to transport
 * concerns such as HTTP status codes or controller-specific response models.</p>
 *
 * @param data the data produced by the application operation
 * @param code a code identifying the operation result
 * @param message a human-readable description of the result
 * @param <T> the type of data returned by the operation
 * @since 0.0.1
 */
public record ApplicationResult<T>(
        T data,
        String code,
        String message
) {

    /**
     * Creates a successful application result.
     *
     * @param data    the data produced by the successful operation
     * @param code    the code identifying the successful result
     * @param message the human-readable result message
     * @param <T>     the type of returned data
     * @return a new successful application result
     */
    public static <T> ApplicationResult<T> success(
            T data,
            String code,
            String message
    ) {
        return new ApplicationResult<>(
                data,
                code,
                message
        );
    }

    /**
     * Creates a failed application result.
     *
     * <p>A failed result does not contain application data and instead
     * exposes a code and message describing the expected failure.</p>
     *
     * @param code    the code identifying the failure
     * @param message the human-readable failure message
     * @param <T>     the expected data type of the operation
     * @return a new failed application result
     */
    public static <T> ApplicationResult<T> failure(
            String code,
            String message
    ) {
        return new ApplicationResult<>(
                null,
                code,
                message
        );
    }
}