package src;

import java.util.Random;
import java.util.stream.Stream;

import src.ecs.*;
import src.world.World;
import src.world.annotations.ECSSystem;
import src.world.annotations.Resource;
import src.world.annotations.With;

public class Main {
    public static void main(String[] args) throws Exception {
        World world = new World();

        world.addResource(new Time());

        world.addSystemsFrom(Main.class);

        for (int i = 0; i < 4; i++) {
            world.ecs().createEntity(
                    Position.random(),
                    new Velocity(),
                    new Acceleration(),
                    new Mass(10.),
                    new Gravity());
        }


        System.out.println(world.getResource(Time.class));
        world.runSystems();

        // while (true) {
            // System.out.println(1. / ((Time) world.getResource(Time.class)).deltaTime);
        // }
    }

    @ECSSystem
    public static void system(
            @With(Gravity.class) @With(Acceleration.class) Stream<Entity> query,
            @Resource Time time) {
        System.out.println(time);

        query.forEach(e -> {
            System.out.println(e);
        });
    }

    @ECSSystem
    public static void system2(
            @With(Gravity.class) Stream<Entity> query) {

    }

    // @ECSSystem
    // public static void updateDeltaTime(@Resource Time time) {
    //     long current = System.nanoTime();
    //     time.deltaTime = ((current - time.lastTime) / 1000000.);
    //     time.lastTime = current;
    // }

    // @ComponentSystem
    // public static void applyGravity(Gravity grav, Acceleration acc) {
    // acc.dy = grav.gravity;
    // }

    // @ComponentSystem
    // public static void applyAcceleration(Position pos, Velocity vel, Acceleration
    // acc, Mass mass, @Resource Time time) {
    // vel.dx = (acc.dx / mass.mass) * time.deltaTime;
    // vel.dy = (acc.dy / mass.mass) * time.deltaTime;

    // pos.x += vel.dx * time.deltaTime;
    // pos.y += vel.dy * time.deltaTime;

    // acc.dx = 0;
    // acc.dy = 0;
    // }
}

class Time extends IComponent {
    long lastTime;
    double deltaTime;

    public Time() {
        this.lastTime = System.nanoTime();
    }
}

class Position extends IComponent {
    double x;
    double y;

    public Position() {
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Position random() {
        Random r = new Random();
        return new Position(r.nextDouble() * 100 - 50, r.nextDouble() * 100 - 50);
    }
}

class Velocity extends IComponent {
    double dx;
    double dy;

    public Velocity() {
    }

    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

class Acceleration extends IComponent {
    double dx;
    double dy;

    public Acceleration() {
    }

    public Acceleration(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

class Mass extends IComponent {
    double mass;

    public Mass(double mass) {
        this.mass = mass;
    }
}

class Gravity extends IComponent {
    double gravity = -9.81;
}