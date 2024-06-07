package src.main.java.com.banana.ecslib.world;

public class RetrieveResource implements SystemParam {
    private Class<?> resource;

    public RetrieveResource(Class<?> c) {
        this.resource = c;
    }

    @Override
    public Object getFromWorld(World world) {
        return world.getResource(this.resource);
    }

}
