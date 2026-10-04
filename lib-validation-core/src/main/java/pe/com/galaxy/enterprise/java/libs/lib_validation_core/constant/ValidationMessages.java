package pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant;

/**
 * Defines default messages used by validation constraints.
 *
 * @since 1.0.0
 */
public final class ValidationMessages {

    public static final String INVALID_UUID = "must be a valid UUID";

    public static final String INVALID_CURRENCY = "must be a valid ISO 4217 currency code";

    public static final String INVALID_DOCUMENT = "must be a valid document number";

    public static final String INVALID_ACCOUNT_NUMBER = "must be a valid bank account number";

    public static final String INVALID_CARD_NUMBER = "must be a valid card number";

    public static final String INVALID_EMAIL_ADDRESS = "must be a valid email address";

    public static final String INVALID_MONEY_AMOUNT = "must be a valid money amount";

    public static final String INVALID_CARD_EXPIRATION = "must be a valid card expiration";

    private ValidationMessages() {
    }
}
