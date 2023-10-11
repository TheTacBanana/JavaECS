package src;
import src.ecs.*;

public class Main {
    public static void main(String[] args) {
        ECS ecs = new ECS();

        Entity e1 = ecs.createEntity(new Position());
        Entity e2 = ecs.createEntity(new Position(), new Sprite());

        System.out.println(ecs.query(Position.class));
        System.out.println(ecs.query(Position.class, Sprite.class));

        e1.addComponent(new Sprite());
        System.out.println(ecs.query(Position.class, Sprite.class));
    }
}

class Position extends IComponent {
    int x;
    int y;
}

class Sprite extends IComponent {
    String s;
}