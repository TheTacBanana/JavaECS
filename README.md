# Java Entity Component System

ECS Paradigm implemented in Java using Reflection and Annotations

Supports Query's, Resources, Locals

Example code:

```java
public static void main(String[] args) throws Exception {
    World world = new World();
    world.addSystemsFrom(Main.class);

    world.addResource(new Time());

    world.ecs().createEntity(
            Position.random(),
            new Velocity(),
            new Acceleration(),
            new Mass(10.),
            new Gravity());

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
```

Licenced under MIT, feel free to use

I wouldn't necessarily recommend use of this software