package src.world;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import src.ecs.*;
import src.world.annotations.ComponentSystem;

public class World {
    public final static int MAX_ENTITY_ID = 100000;
    public final static int MAX_COMPONENT_POOLS = 32;

    // ECS
    private ECS ecs;
    private ECSInternal ecsInternal;

    // Systems
    private ArrayList<SystemRunner> runners;

    // Resources
    private ECSInternal resources;

    public World(){
        this.ecsInternal = new ECSInternal(MAX_ENTITY_ID, MAX_COMPONENT_POOLS);
        this.ecs = new ECS(this.ecsInternal);
        this.runners = new ArrayList<>();

        this.resources = new ECSInternal(1, MAX_COMPONENT_POOLS);
        this.resources.createEntity();
    }

    public ECS ecs(){
        return this.ecs;
    }

    public void addSystemsFrom(Class<?> c){
        Method[] methods = c.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(ComponentSystem.class)){
                int modifiers = methods[i].getModifiers();
                if (!Modifier.isStatic(modifiers)){
                    continue;
                }
                System.out.println("Added: " + methods[i]);
                this.runners.add(new SystemRunner(methods[i]));
            }
        }
    }

    public void runSystems(){
        for (SystemRunner systemRunner : runners) {
            systemRunner.run(this);
        }
    }

    public <T extends IComponent> void addResource(T res){
        this.resources.addComponents(0, res);
    }

    public <T extends IComponent> T getResource(Class<?> c){
        return this.resources.getComponent(0, c);
    }
}
