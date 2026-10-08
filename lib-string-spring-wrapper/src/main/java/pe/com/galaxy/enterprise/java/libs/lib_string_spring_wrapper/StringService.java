package pe.com.galaxy.enterprise.java.libs.lib_string_spring_wrapper;

public interface StringService {

    boolean isBlank(String value);

    String nullIfBlank(String value);

    String normalizeWhitespace(String value);

    String truncate(String value, int maxLength);

    String defaultIfNull(String value, String defaultValue);
}