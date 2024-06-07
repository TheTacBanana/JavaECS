package src.world;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import src.world.annotations.Resource;
import src.world.annotations.With;
import src.world.annotations.WithMany;

public class SystemRunner {
    private Method method;
    private Object[] methodInputs;
    private SystemParam[] params;

    public SystemRunner(Method method) throws InvalidSystemException{
        this.method = method;
        this.methodInputs = new Object[method.getParameterCount()];
        this.params = new SystemParam[method.getParameterCount()];

        method.setAccessible(true);
        Class<?>[] params = this.method.getParameterTypes();
        Annotation[][] annos = this.method.getParameterAnnotations();

        for (int i = 0; i < params.length; i++) {
            this.params[i] = this.buildSystemParam(params[i], annos[i]);
        }

        for (SystemParam p : this.params) {
            System.out.println(p);
        }
    }

    public SystemParam buildSystemParam(Class<?> paramType, Annotation[] annotations) throws InvalidSystemException {
        if (annotations.length == 0) {
            return null;
        }

        RetrieveEntities query = null;
        RetrieveResource resource = null;

        for (Annotation annotation : annotations) {
            Class<?> c = annotation.annotationType();

            if (c == With.class) {
                if (query == null) {
                    query = new RetrieveEntities();
                }
                query.addWith(((With) annotation).value());
            } else if (c == WithMany.class){
                if (query == null) {
                    query = new RetrieveEntities();
                }
                With[] withMany = ((WithMany) annotation).value();

                for (With with : withMany) {
                    query.addWith(with.value());
                }
            } else if (c == Resource.class) {
                if (resource == null) {
                    resource = new RetrieveResource(paramType);
                }
            }
        }

        if (query != null && resource == null) {
            return query;
        }
        if (resource != null && query == null) {
            return resource;
        }
        if (query != null && resource != null) {
            throw new InvalidSystemException();
        }

        return null;
    }

    public void run(World world) {
        for (int i = 0; i < this.params.length; i++) {
            SystemParam p = this.params[i];
            if (p != null) {
                this.methodInputs[i] = p.getFromWorld(world);
            }
            System.out.println(this.methodInputs[i]);
        }

        try {
            this.method.invoke(null, this.methodInputs);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}