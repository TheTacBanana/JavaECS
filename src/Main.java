package src;

import java.util.Random;
import java.util.stream.Stream;

import src.ecs.*;
import src.world.World;
import src.world.annotations.*;

public class Main {
    public static void main(String[] args) throws Exception {
        World world = new World();
        world.addSystemsFrom(Main.class);

        world.addResource(new Time());
        for (int i = 0; i < 3; i++) {
            world.ecs().createEntity(
                    Position.random(),
                    new Velocity(),
                    new Acceleration(),
                    new Mass(10.),
                    new Gravity());
        }

        while (true) {
            world.runSystems();
        }
    }

    @ECSSystem
    public static void updateDeltaTime(@Resource Time time) {
        long current = System.nanoTime();
        time.deltaTime = ((current - time.lastTime) / 1000000.);
        time.lastTime = current;
    }

    @ECSSystem
    public static void applyGravity(@With(Acceleration.class) @With(Gravity.class) Stream<Entity> query) {
        query.forEach(e -> {
            Acceleration acc = e.getComponent(Acceleration.class);
            Gravity grav = e.getComponent(Gravity.class);
            acc.dy = grav.gravity;
        });
    }

    @ECSSystem
    public static void applyAcceleration(
        @With(Position.class)
        @With(Velocity.class)
        @With(Acceleration.class)
        @With(Mass.class)
        Stream<Entity> query,
        @Resource Time time)
    {
        query.forEach(e -> {
            Position pos = e.getComponent(Position.class);
            Velocity vel = e.getComponent(Velocity.class);
            Acceleration acc = e.getComponent(Acceleration.class);
            Mass mass = e.getComponent(Mass.class);

            vel.dx = (acc.dx / mass.mass) * time.deltaTime;
            vel.dy = (acc.dy / mass.mass) * time.deltaTime;

            pos.x += vel.dx * time.deltaTime;
            pos.y += vel.dy * time.deltaTime;

            acc.dx = 0;
            acc.dy = 0;
        });
    }
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