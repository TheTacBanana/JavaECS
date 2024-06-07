package src.main.java.com.banana.ecslib.ecs;

import java.util.stream.Stream;

public class ECS {
    private ECSInternal internal;

    public ECS(ECSInternal internal){
        this.internal = internal;
    }

    public Entity createEntity() throws Exception{
        return internal.createEntity();
    }

    public Entity createEntity(Object ...components) throws Exception{
        return internal.createEntity(components);
    }

    public Entity getEntity(EntityId id){
        return internal.getEntity(id.id());
    }

    public void removeEntity(EntityId id){
        internal.removeEntity(id.id());
    }

    public boolean entityExists(EntityId id){
        return internal.entityExists(0);
    }

    public Stream<Entity> query(Class<?>[] with, Class<?>[] without){
        return this.internal.query(with, without);
    }
}
