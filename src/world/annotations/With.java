package src.world.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(WithMany.class)
@Retention(RetentionPolicy.RUNTIME)
public
@interface With {
    Class<?> value() default Default.class;
}