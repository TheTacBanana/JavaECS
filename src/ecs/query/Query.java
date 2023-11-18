package src.ecs.query;

import java.util.ArrayList;

import src.ecs.EntityId;

public class Query<T > {
    private ClassCollection with;

    private ArrayList<EntityId> entities;

    public Query(ClassCollection collection){
        this.with = collection;
    }
}
