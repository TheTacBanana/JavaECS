package src.world;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import src.ecs.*;
import src.world.annotations.ComponentSystem;

public class World {
    private ECS ecs;
    private ECSInternal ecsInternal;

    private ArrayList<SystemRunner> runners;

    public World(){
        this.ecsInternal = new ECSInternal();
        this.ecs = new ECS(this.ecsInternal);
        this.runners = new ArrayList<>();
    }

    public ECS ecs(){
        return this.ecs;
    }

    public void addSystem(Class<?> c){
        Method[] methods = c.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(ComponentSystem.class)){
                int modifiers = methods[i].getModifiers();
                if (!Modifier.isStatic(modifiers)){
                    continue;
                }
                this.runners.add(new SystemRunner(methods[i]));
            }
        }
    }

    public void runSystems(){
        for (SystemRunner systemRunner : runners) {
            systemRunner.run(this);
        }
    }
}
