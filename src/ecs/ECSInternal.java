package src.ecs;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

public class ECSInternal {
    // Static Consts
    public final static int MAX_ENTITY_ID = 256;
    public final static int MAX_COMPONENT_POOLS = 32;

    // Entitys
    Entity[] entityArray;
    ArrayList<Integer> freeEntityIds;

    // Components
    int nextComponentId = 0;
    HashMap<Class<?>, Integer> componentMap;
    ComponentPool[] componentPools;

    public ECSInternal() {
        this.entityArray = new Entity[ECSInternal.MAX_ENTITY_ID];
        this.freeEntityIds = new ArrayList<>();
        for (int i = MAX_ENTITY_ID; i >= 0; i--) {
            this.freeEntityIds.add(i);
        }

        this.componentMap = new HashMap<>();
        this.componentPools = new ComponentPool[ECSInternal.MAX_COMPONENT_POOLS];
    }

    public Entity createEntity() {
        return createEntity((Object[]) null);
    }

    public Entity createEntity(Object... comps) {
        int entityId = this.freeEntityIds.remove(this.freeEntityIds.size() - 1);
        this.entityArray[entityId] = new Entity(entityId, this);
        Entity entity = this.entityArray[entityId];

        int[] ids = this.registerComponents(comps);
        for (int i = 0; i < ids.length; i++) {
            int compId = ids[i];
            ComponentPool pool = this.getComponentPool(compId);
            IComponent newComp = (IComponent) pool.insert(entityId, comps[i]);
            newComp.linkEntity(this.getEntity(entityId));
        }
        // this.updateEntityBitset(entity.getId());
        return entity;
    }

    public Entity getEntity(int id) {
        if (id >= 0 && id < this.entityArray.length) {
            return this.entityArray[id];
        } else {
            return null;
        }
    }

    public void removeEntity(int id) {
        if (id >= 0 && id < this.entityArray.length) {
            this.entityArray[id] = null;

            for (ComponentPool componentPool : this.componentPools) {
                if (componentPool != null) {
                    componentPool.remove(id);
                }
            }
        }
    }

    public boolean entityExists(int id) {
        return id >= 0 && id < this.entityArray.length && this.entityArray[id] != null;
    }

    // private void updateEntityBitset(int id){
    // if (this.entityExists(id)){
    // BitSet bitset = this.getEntity(id).getBitSet();

    // for (int i = 0; i < componentPools.length; i++) {
    // ComponentPool pool = this.componentPools[i];
    // if (pool != null && pool.exists(id)){
    // bitset.set(i);
    // } else {
    // bitset.clear(i);
    // }
    // }
    // }
    // }

    private int[] registerComponents(Object... objects) {
        int length = objects == null ? 0 : objects.length;
        Class<?>[] classes = new Class<?>[length];

        for (int i = 0; i < classes.length; i++) {
            classes[i] = objects[i].getClass();
        }

        return this.registerComponents(classes);
    }

    private int[] registerComponents(Class<?>... comps) {
        int length = comps == null ? 0 : comps.length;
        int[] ids = new int[length];

        for (int i = 0; i < ids.length; i++) {
            Class<?> c = comps[i];
            if (this.componentMap.get(c) == null) {
                this.componentMap.put(c, this.nextComponentId++);
            }
            ids[i] = this.componentMap.get(c);
        }
        return ids;
    }

    public void addComponents(int entityId, Object... objects) {
        if (this.entityExists(entityId)) {
            int[] ids = this.registerComponents(objects);

            for (int i = 0; i < ids.length; i++) {
                ComponentPool pool = this.getComponentPool(ids[i]);
                IComponent newComp = (IComponent) pool.insert(entityId, objects[i]);
                newComp.linkEntity(this.getEntity(entityId));
            }

            // this.updateEntityBitset(entityId);
        }
    }

    private ComponentPool getComponentPool(int compId) {
        if (compId >= 0 && compId < this.componentPools.length) {
            if (this.componentPools[compId] == null) {
                this.componentPools[compId] = new ComponentPool();
            }
            return this.componentPools[compId];
        } else {
            return null;
        }
    }

    public <T> boolean hasComponent(int entityId, Class<T> compClass) {
        if (this.entityExists(entityId)) {
            int compId = this.registerComponents(compClass)[0];
            ComponentPool pool = this.getComponentPool(compId);
            return pool.exists(entityId);
        } else {
            return false;
        }
    }

    public <T extends IComponent> T getComponent(int entityId, Class<T> compClass) {
        if (this.entityExists(entityId)) {
            int compId = this.registerComponents(compClass)[0];
            ComponentPool pool = this.getComponentPool(compId);
            return (T) pool.get(entityId);
        } else {
            return null;
            // TODO: Except
        }
    }

    public <T> void removeComponent(int entityId, Class<T> compClass) {
        if (this.entityExists(entityId)) {
            int compId = this.registerComponents(compClass)[0];
            ComponentPool pool = this.getComponentPool(compId);
            pool.remove(entityId);

            // this.updateEntityBitset(entityId);
        }
        // TODO: Except
    }

    // public ArrayList<Integer> query(Class<?> ...comps){
    // int[] compIds = this.getComponentIds(comps);
    // BitSet target = this.idsToBitSet(compIds);

    // ArrayList<Integer> entities = new ArrayList<>();

    // for (Entity entity : this.entityArray) {
    // if (entity == null){
    // continue;
    // }

    // BitSet intersection = (BitSet) entity.getBitSet().clone();
    // intersection.and(target);

    // if (intersection.equals(target)){
    // entities.add(entity.getId());
    // }
    // }

    // return entities;
    // }
}