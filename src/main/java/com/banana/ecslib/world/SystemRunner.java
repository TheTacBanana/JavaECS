package com.banana.ecslib.world;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import com.banana.ecslib.world.annotations.*;

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


            } else if (c == Without.class) {
                if (query == null) {
                    query = new RetrieveEntities();
                }
                query.addWithout(((Without) annotation).value());
            } else if (c == WithoutMany.class){
                if (query == null) {
                    query = new RetrieveEntities();
                }
                Without[] withoutMany = ((WithoutMany) annotation).value();

                for (Without without : withoutMany) {
                    query.addWithout(without.value());
                }


            } else if (c == Resource.class) {
                if (resource == null) {
                    resource = new RetrieveResource(paramType);
                }
            }

            else if (c == Local.class) {
                return null;
            }
        }

        if (query != null && resource == null && paramType == Stream.class) {
            return query;
        }
        if (resource != null && query == null) {
            return resource;
        }
        if (query != null && resource != null) {
            throw new InvalidSystemException(this.method);
        }

        return null;
    }

    public void run(World world) {
        for (int i = 0; i < this.params.length; i++) {
            SystemParam p = this.params[i];
            if (p != null) {
                this.methodInputs[i] = p.getFromWorld(world);
            }
        }
        try {
            this.method.invoke(null, this.methodInputs);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}