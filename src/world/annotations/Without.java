package src.world.annotations;

import java.lang.annotation.*;

@Repeatable(WithoutMany.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Without {
    Class<?> value();
}
