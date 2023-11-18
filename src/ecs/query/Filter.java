package src.ecs.query;

public class Filter{
    Class<?>[] classes;

    public Filter(Class<?> ...classes){
        this.classes = classes;
    }
}
