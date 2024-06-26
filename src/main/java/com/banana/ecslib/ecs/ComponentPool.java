package com.banana.ecslib.ecs;

class ComponentPool{
    private Object[] components;

    ComponentPool(int maxEntities){
        this.components = new Object[maxEntities];
    }

    public Object get(int id){
        if (id >= 0 && id < components.length){
            return this.components[id];
        } else {
            return null;
        }
    }

    public Object insert(int id, Object o){
        if (id >= 0 && id < components.length){
            this.components[id] = o;
            return this.components[id];
        } else {
            return null;
        }
    }

    public Boolean exists(int id){
        return this.get(id) != null;
    }

    public void remove(int id){
        if (id >= 0 && id < components.length){
            this.components[id] = null;
        }
    }
}