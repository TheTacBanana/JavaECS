package ecs;

abstract public class IComponent {
    private Entity entity;

    public Entity entity(){
        return this.entity;
    }

    public void linkEntity(Entity e){
        this.entity = e;
    }

    public boolean isLinked(){
        return entity == null;
    }
}