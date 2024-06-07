package src.main.java.com.banana.ecslib.world.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WithMany {
    With[] value();
}