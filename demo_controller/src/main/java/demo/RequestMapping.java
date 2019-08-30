package demo;

import java.lang.annotation.*;

/**
 * @author lijiayin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestMapping {
    String value();
}