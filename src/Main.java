package src;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.ArrayList;

import src.ecs.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ECSInternal ecs = new ECSInternal();

        Entity e1 = ecs.createEntity(new Position());
        Entity e2 = ecs.createEntity(new Position(), new Sprite());

        for (Method m : methods) {
            Class<?>[] inputs = m.getParameterTypes();
            Object[] objects = new Object[inputs.length];
            
            for (int i = 0; i < inputs.length; i++) {
                Class<?> c = inputs[i];
                if (c == Position.class){
                    objects[i] = new Position();
                } else if (c == Sprite.class){
                    objects[i] = new Sprite();
                }
            }

            m.invoke(null, objects);
        }
    }
}

class Position extends IComponent {
    int x;
    int y;
}

class Sprite extends IComponent {
    String s;
}