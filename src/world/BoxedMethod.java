package src.world;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BoxedMethod {
    private Method m;
    private Object[] objs;
    private CreateSystem annotation;

    private BoxedMethod(Method method, CreateSystem anno){
        this.m = method;
        this.objs = new Object[method.getParameterCount()];
        this.annotation = anno;
    }

    public static BoxedMethod create(Method method){
        if (method == null){
            return null;
        }

        if (!Modifier.isStatic(method.getModifiers())){
            System.out.println("Cannot create BoxedMethod from non static method " + method);
            return null;
        }

        CreateSystem anno = method.getAnnotation(CreateSystem.class);
        if (anno == null){
            System.out.println("Cannot create BoxedMethod from non annotated method" + method);
            return null;
        }

        return new BoxedMethod(method, anno);
    }
}
