package pe.com.galaxy.enterprise.java.libs.lib_mask_core.handler;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.exception.MaskingException;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.MaskingStrategy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Default {@link MaskerService} implementation that resolves masking
 * strategies according to their supported {@link MaskType}.
 *
 * @since 0.0.1
 */
public final class MaskingStrategyHandler
        implements MaskerService {

    private final Map<MaskType, MaskingStrategy> strategies;

    /**
     * Creates a masking handler using the supplied strategies.
     *
     * @param strategies masking strategies to register
     *
     * @throws IllegalArgumentException when more than one strategy
     *                                  supports the same mask type
     */
    public MaskingStrategyHandler(
            List<MaskingStrategy> strategies
    ) {
        Objects.requireNonNull(
                strategies,
                "Masking strategies cannot be null"
        );

        this.strategies =
                new EnumMap<>(MaskType.class);

        for (MaskingStrategy strategy : strategies) {
            Objects.requireNonNull(
                    strategy,
                    "Masking strategy cannot be null"
            );

            MaskType type =
                    Objects.requireNonNull(
                            strategy.supports(),
                            "Masking strategy type cannot be null"
                    );

            MaskingStrategy previous =
                    this.strategies.putIfAbsent(
                            type,
                            strategy
                    );

            if (previous != null) {
                throw new IllegalArgumentException(
                        "Duplicate masking strategy for type: " + type
                );
            }
        }
    }

    /**
     * Masks a value using the default strategy behavior.
     *
     * @param value value to mask
     * @param type masking category
     * @return masked value
     */
    @Override
    public String mask(
            String value,
            MaskType type
    ) {
        if (value == null || value.isBlank()) {
            return value;
        }

        return resolve(type)
                .mask(value);
    }

    /**
     * Masks a value using the supplied masking options.
     *
     * @param value value to mask
     * @param type masking category
     * @param options masking configuration
     * @return masked value
     *
     * @since 1.2.0
     */
    @Override
    public String mask(
            String value,
            MaskType type,
            MaskingOptions options
    ) {
        if (value == null || value.isBlank()) {
            return value;
        }

        Objects.requireNonNull(
                options,
                "Masking options cannot be null"
        );

        return resolve(type)
                .mask(
                        value,
                        options
                );
    }

    private MaskingStrategy resolve(
            MaskType type
    ) {
        Objects.requireNonNull(
                type,
                "Mask type cannot be null"
        );

        MaskingStrategy strategy =
                strategies.get(type);

        if (strategy == null) {
            throw new MaskingException(
                    "No masking strategy registered for type: " + type
            );
        }

        return strategy;
    }
}