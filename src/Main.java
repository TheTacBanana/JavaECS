package src;

import src.ecs.*;
import src.world.World;
import src.world.annotations.ComponentSystem;
import src.world.annotations.Without;

public class Main {
    public static void main(String[] args) throws Exception {
        World world = new World();

        Entity e1 = world.ecs().createEntity(new Position());
        Entity e2 = world.ecs().createEntity(new Position(), new Sprite());

        world.addSystem(Position.class);

        world.runSystems();
    }
}

class Position extends IComponent {
    int x;
    int y;

    @ComponentSystem
    public static void update(Position position){
        System.out.println("This has run");
    }

    @ComponentSystem
    public static void update(Position position, @Without Sprite _s){
        System.out.println("This has run withjout sprite");
    }
}

class Sprite extends IComponent {
    String s;
}