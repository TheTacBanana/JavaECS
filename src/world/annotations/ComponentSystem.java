package src.world.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentSystem {
    int TickRate() default 1;
}
