package src.ecs;

public class ECS {

    private ECSInternal internal;

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

}
