package src.ecs;

import java.util.ArrayList;
import java.util.HashMap;

public class ECS {
    public final static int MAX_ENTITY_ID = 128;
    public final static int MAX_COMPONENT_POOLS = 32;

    Entity[] entityArray;
    ArrayList<Integer> freeEntityIds;

    int nextComponentId = 0;
    HashMap<Class<?>, Integer> componentMap;
    ComponentPool[] componentPools;

    public ECS() {
        this.entityArray = new Entity[ECS.MAX_ENTITY_ID];
        this.freeEntityIds = new ArrayList<>();
        for (int i = MAX_ENTITY_ID; i >= 0; i--){
            this.freeEntityIds.add(i);
        }

        this.componentMap = new HashMap<>();
        this.componentPools = new ComponentPool[ECS.MAX_COMPONENT_POOLS];
    }

    public Entity createEntity() {
        int newId;
        if (this.freeEntityIds.size() > 0) {
            newId = this.freeEntityIds.removeLast();
        } else {
            return null;
            // TODO: Except
        }

        this.entityArray[newId] = new Entity(newId, this);
        return this.entityArray[newId];
    }

    public Entity createEntity(Object... comps) {
        Entity entity = this.createEntity();
        int[] ids = this.getComponentIds(comps);

        for (int i = 0; i < ids.length; i++){
            int compId = ids[i];
            ComponentPool pool = this.getComponentPool(compId);
            pool.insert(entity.getId(), comps[i]);
        }
        return entity;
    }

    public Entity getEntity(int id){
        if (id >= 0 && id < this.entityArray.length){
            return this.entityArray[id];
        } else {
            return null;
        }
    }

    public void removeEntity(int id){
        // TODO: delete
    }

    public boolean entityExists(int id){
        return id >= 0 && id < this.entityArray.length && this.entityArray[id] != null;
    }

    private int getComponentId(Class<?> compClass){
        if (this.componentMap.get(compClass) == null) {
            this.componentMap.put(compClass, this.nextComponentId++);
        }
        return this.componentMap.get(compClass);
    }

    private int[] getComponentIds(Object... comps) {
        int[] classes = new int[comps.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = this.getComponentId(comps[i].getClass());
        }
        return classes;
    }

    private ComponentPool getComponentPool(int compId){
        if (compId >= 0 && compId < this.componentPools.length){
            if (this.componentPools[compId] == null){
                this.componentPools[compId] = new ComponentPool();
            }
            return this.componentPools[compId];
        } else {
            return null;
        }        
    }

    public <T> boolean hasComponent(int entityId, Class<T> compClass){
        if (this.entityExists(entityId)){
            int compId = this.getComponentId(compClass);
            ComponentPool pool = this.getComponentPool(compId);
            return pool.exists(entityId);
        } else{
            return false;
        }
    }

    public <T extends IComponent> T getComponent(int entityId, Class<T> compClass){
        if (this.entityExists(entityId)){
            int compId = this.getComponentId(compClass);
            ComponentPool pool = this.getComponentPool(compId);
            return (T) pool.get(entityId);
        } else{
            return null;
            // TODO: Except
        }
    }

    public <T extends IComponent> T addComponent(int entityId, T comp){
        if (this.entityExists(entityId)){
            int compId = this.getComponentId(comp.getClass());
            ComponentPool pool = this.getComponentPool(compId);
            T newComp = (T) pool.insert(entityId, comp);
            comp.linkEntity(this.getEntity(entityId));
            return comp;
        } else{
            return null;
            // TODO: Except
        }
    }

    public <T> void removeComponent(int entityId, Class<T> compClass){
        if (this.entityExists(entityId)){
            int compId = this.getComponentId(compClass);
            ComponentPool pool = this.getComponentPool(compId);
            pool.remove(entityId);
        } 
        // TODO: Except
    }

    // public <T> T query()
}