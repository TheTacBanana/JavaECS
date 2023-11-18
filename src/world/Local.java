package src.world;

public class Local<T>{
    private T t;

    public boolean exists(){
        return this.t != null;
    }

    public void insert(T t){
        this.t = t;
    }

    public T inner(){
        return this.t;
    }
}
