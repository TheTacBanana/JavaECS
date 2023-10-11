package src;
import src.ecs.*;

public class Main {
    public static void main(String[] args) {
        ECS ecs = new ECS();

        Entity e1 = ecs.createEntity(new Position());
        Entity e2 = ecs.createEntity(new Position(), new Sprite());

        Sprite s1 = e1.getComponent(Sprite.class);
        System.out.println(s1);

        e1.addComponent(new Sprite());

        Sprite s2 = e1.getComponent(Sprite.class);
        System.out.println(s2);

    }
}

class Position extends IComponent {
    int x;
    int y;
}

class Sprite extends IComponent {
    String s;
}