package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.handler;

import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.exception.MaskingException;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy.MaskingStrategy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Default {@link MaskerService} implementation that delegates masking
 * operations to registered {@link MaskingStrategy} implementations.
 *
 * <p>Strategies are indexed by their supported {@link MaskType}. Only one
 * strategy can be registered for each mask type.</p>
 *
 * <p>Null and blank values are returned unchanged without invoking a
 * masking strategy.</p>
 *
 * @since 0.0.1
 */
public class MaskingStrategyHandler implements MaskerService {

    private final Map<MaskType, MaskingStrategy> strategies;

    /**
     * Creates a masking strategy handler using the specified strategies.
     *
     * @param strategies masking strategies available to the handler
     * @throws IllegalArgumentException if more than one strategy supports
     *         the same {@link MaskType}
     */
    public MaskingStrategyHandler(
            List<MaskingStrategy> strategies
    ) {
        this.strategies = new EnumMap<>(MaskType.class);

        for (MaskingStrategy strategy : strategies) {
            MaskType type = strategy.supports();

            if (this.strategies.putIfAbsent(type, strategy) != null) {
                throw new IllegalArgumentException(
                        "Duplicate masking strategy for type: " + type
                );
            }
        }
    }

    /**
     * Masks the specified value using the strategy registered for the
     * requested mask type.
     *
     * <p>Null or blank values are returned unchanged.</p>
     *
     * @param value the value to mask
     * @param type the masking type to apply
     * @return the masked value, or the original value if it is null or blank
     * @throws MaskingException if no strategy is registered for the
     *         specified mask type
     */
    @Override
    public String mask(
            String value,
            MaskType type
    ) {
        if (value == null || value.isBlank()) {
            return value;
        }

        MaskingStrategy strategy = strategies.get(type);

        if (strategy == null) {
            throw new MaskingException(
                    "No masking strategy registered for type: " + type
            );
        }

        return strategy.mask(value);
    }
}