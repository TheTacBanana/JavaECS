package src.ecs;

public class EntityId implements Cloneable{
    private int id;

    public EntityId(int id){
        this.id = id;
    }

    public int id(){
        return this.id;
    }

    public EntityId clone(){
        return new EntityId(this.id);
    }
}