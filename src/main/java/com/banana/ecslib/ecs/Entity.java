package com.banana.ecslib.ecs;

import java.util.BitSet;

public class Entity {
    private int id;
    private ECSInternal ecs;

    public Entity(int id, ECSInternal ecs) {
        this.id = id;
        this.ecs = ecs;
    }

    public EntityId getId(){
        return new EntityId(id);
    }

    public BitSet getBitSet() {
        return ecs.getEntityBitSet(id);
    }

    public <T> T getComponent(Class<T> c){
        return ecs.getComponent(this.id, c);
    }

    public <T extends IComponent> void addComponent(T comps){
        ecs.addComponents(this.id, comps);
    }

    public <T extends IComponent> void addComponents(T ...comps){
        ecs.addComponents(this.id, comps);
    }

    public <T extends IComponent> void removeComponent(Class<T> compClass){
        ecs.removeComponent(this.id, compClass);
    }

    public <T extends IComponent> boolean hasComponent(Class<T> compClass){
        return ecs.hasComponent(this.id, compClass);
    }
}
