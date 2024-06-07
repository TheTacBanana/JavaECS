package src.main.java.com.banana.ecslib.world;

public class RetrieveEntities implements SystemParam {
    Class<?>[] with = new Class[0];
    Class<?>[] without = new Class[0];

    public void addWith(Class<?> c) {
        int l = this.with.length;
        Class<?>[] temp = with;
        this.with = new Class<?>[this.with.length + 1];
        System.arraycopy(temp, 0, this.with, 0, l);
        this.with[l] = c;
    }

    public void addWithout(Class<?> c) {
        int l = this.without.length;
        Class<?>[] temp = without;
        this.without = new Class<?>[this.without.length + 1];
        System.arraycopy(temp, 0, this.without, 0, l);
        this.without[l] = c;
    }

    @Override
    public Object getFromWorld(World world) {
        return world.ecs().query(this.with, this.without);
    }


}
