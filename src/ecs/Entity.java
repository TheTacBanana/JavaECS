package src.ecs;

import java.util.BitSet;

public class Entity {
    private int id;
    private ECS ecs;
    private BitSet bitset;

    public Entity(int id, ECS ecs) {
        this.id = id;
        this.ecs = ecs;
        this.bitset = new BitSet(ECS.MAX_COMPONENT_POOLS);
    }

    // public Entity setTag(String string){
        
    //     return this;
    // }

    public int getId(){
        return this.id;
    }

    public BitSet getBitSet(){
        return this.bitset;
    }

    public <T extends IComponent> T getComponent(Class<T> c){
        return ecs.getComponent(this.id, c);
    }

    public <T extends IComponent> T addComponent(T comp){
        return ecs.addComponent(this.id, comp);
    }

    public <T extends IComponent> void removeComponent(Class<T> compClass){
        ecs.removeComponent(this.id, compClass);
    }

    public <T extends IComponent> boolean hasComponent(Class<T> compClass){
        return ecs.hasComponent(this.id, compClass);
    }
}
