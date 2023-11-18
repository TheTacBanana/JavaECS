package src.world;

import src.ecs.*;

public class World {
    ECS ecs;

    SystemSchedule schedule;

    public World(){
        this.ecs = new ECS();
    }
}
