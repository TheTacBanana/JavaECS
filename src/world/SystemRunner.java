package src.world;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

import src.ecs.Entity;
import src.world.annotations.ComponentSystem;
import src.world.annotations.Resource;
import src.world.annotations.With;
import src.world.annotations.Without;

public class SystemRunner {
    private Method method;
    private ComponentSystem anno;

    private Class<?>[] with;
    private Class<?>[] without;

    private PopulateObjTo[] populate;
    private PopulateObjTo[] resources;

    private Object[] inputs;

    public SystemRunner(Method method) {
        this.method = method;
        this.anno = method.getAnnotation(ComponentSystem.class);
        this.inputs = new Object[method.getParameterCount()];

        method.setAccessible(true);
        Class<?>[] params = this.method.getParameterTypes();
        Annotation[][] annos = this.method.getParameterAnnotations();

        ArrayList<Class<?>> withList = new ArrayList<>();
        ArrayList<Class<?>> withoutList = new ArrayList<>();
        ArrayList<PopulateObjTo> pop = new ArrayList<>();
        ArrayList<PopulateObjTo> resourcesList = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            Class<?> c = params[i];
            if (annos[i].length > 0 && annos[i][0].annotationType() == With.class) {
                System.out.println("With Annotation");
                withList.add(c);
            } else if (annos[i].length > 0 && annos[i][0].annotationType() == Without.class) {
                System.out.println("Without Annotation");
                withoutList.add(c);
            } else if (annos[i].length > 0 && annos[i][0].annotationType() == Resource.class) {
                System.out.println("Resource Annotation");
                resourcesList.add(new PopulateObjTo(c, i));
            } else {
                System.out.println("No Annotation");
                withList.add(c);
                pop.add(new PopulateObjTo(c, i));
            }
        }

        this.with = new Class<?>[withList.size()];
        for (int i = 0; i < this.with.length; i++) {
            this.with[i] = withList.get(i);
        }

        this.without = new Class<?>[withoutList.size()];
        for (int i = 0; i < this.without.length; i++) {
            this.without[i] = withoutList.get(i);
        }

        this.populate = new PopulateObjTo[pop.size()];
        for (int i = 0; i < this.populate.length; i++) {
            this.populate[i] = pop.get(i);
        }

        this.resources = new PopulateObjTo[resourcesList.size()];
        for (int i = 0; i < this.resources.length; i++) {
            this.resources[i] = resourcesList.get(i);
        }
    }

    public void run(World world) {
        ArrayList<Entity> entities = world.ecs().query(this.with, this.without);
        for (Entity entity : entities) {
            for (PopulateObjTo pop : this.populate) {
                Class<?> c = pop.c();
                this.inputs[pop.to()] = entity.getComponent(c);
            }

            for (PopulateObjTo res : this.resources) {
                Class<?> c = res.c();
                this.inputs[res.to()] = world.getResource(c);
            }

            try {
                this.method.invoke(null, this.inputs);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public record PopulateObjTo(Class<?> c, int to) {
    }
}