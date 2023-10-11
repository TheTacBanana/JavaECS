import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ecs.*;

public class Main {
    public static void main(String[] args) {
        ECS ecs = new ECS();

        Entity e1 = ecs.createEntity(new Sprite());
        Entity e2 = ecs.createEntity(new Position(), new Sprite());

        Position p1 = e1.getComponent(Position.class);

        System.out.println(p1);
    }
}

class Position extends IComponent {
    int x;
    int y;
}

class Sprite extends IComponent {
    String s;
}