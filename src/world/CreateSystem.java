package src.world;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface CreateSystem {
    int TickRate() default 1;
}
