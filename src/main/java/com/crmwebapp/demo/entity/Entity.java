package com.crmwebapp.demo.entity;

public abstract class Entity {

    public abstract Object getPrimaryKey();

    public abstract String getDelineation();

    @Deprecated
    public abstract String prymaryKeyToString();

    /**
     * Indicates whether some other entity is essentially like this one.
     *
     * @param obj
     * @return this.equals(obj)
     */
    public boolean isJustLike(Entity obj) {
        return this.equals(obj);
    }

}
