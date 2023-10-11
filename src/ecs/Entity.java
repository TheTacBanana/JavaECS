package src.ecs;

public class Entity {
    private int id;
    private ECS ecs;

    public Entity(int id, ECS ecs) {
        this.id = id;
        this.ecs = ecs;
    }

    // public Entity setTag(String string){
        
    //     return this;
    // }

    public int getId(){
        return this.id;
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
