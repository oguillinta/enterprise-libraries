package pe.com.galaxy.enterprise.java.libs.lib_mask_core.annotation;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.RECORD_COMPONENT
})
public @interface Masked {
    MaskType value();
}
