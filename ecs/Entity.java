package ecs;

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

    public <T> T getComponent(Class<T> c){
        return ecs.getComponent(this.id, c);
    }
}
