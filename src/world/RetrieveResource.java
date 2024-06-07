package src.world;

public class RetrieveResource implements SystemParam {
    private Class<?> resource;

    public RetrieveResource(Class<?> c) {
        this.resource = c;
        System.out.println("h" + this.resource);
    }

    @Override
    public Object getFromWorld(World world) {
        return world.getResource(this.resource);
    }

}
