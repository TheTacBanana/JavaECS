package com.banana.ecslib.world;

import java.lang.reflect.Method;

public class InvalidSystemException extends Exception{
    Method method;

    public InvalidSystemException(Method m) {
        this.method = m;
    }

    public String toString() {
        return "Invalid System: " + method.toString();
    }
}
