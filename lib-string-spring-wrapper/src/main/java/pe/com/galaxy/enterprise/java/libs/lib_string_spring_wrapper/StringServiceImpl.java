package pe.com.galaxy.enterprise.java.libs.lib_string_spring_wrapper;

import pe.com.galaxy.enterprise.java.libs.lib_string_utils.StringUtils;

public final class StringServiceImpl implements StringService {

    @Override
    public boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }

    @Override
    public String nullIfBlank(String value) {
        return StringUtils.nullIfBlank(value);
    }

    @Override
    public String normalizeWhitespace(String value) {
        return StringUtils.normalizeWhitespace(value);
    }

    @Override
    public String truncate(String value, int maxLength) {
        return StringUtils.truncate(value, maxLength);
    }

    @Override
    public String defaultIfNull(
            String value,
            String defaultValue
    ) {
        return StringUtils.defaultIfNull(
                value,
                defaultValue
        );
    }
}