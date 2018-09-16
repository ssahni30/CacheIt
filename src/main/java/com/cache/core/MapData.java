package com.cache.core;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class MapData {


    private Class<?> parentClass;

    private Method invokingMethod;

    private List<KeyFieldCache> keyFieldCaches;

    private boolean keyFieldCacheDataTypesUnique;

    private Date updated;

    public MapData(Class<?> parentClass, Method method, List<KeyFieldCache> keyFieldCacheSet, boolean keyFieldCacheDataTypesUnique) {
        this.parentClass = parentClass;
        this.invokingMethod = method;
        this.keyFieldCaches = keyFieldCacheSet;
        this.keyFieldCacheDataTypesUnique = keyFieldCacheDataTypesUnique;
    }

    public Class<?> getParentClass() {
        return parentClass;
    }

    public Method getInvokingMethod() {
        return invokingMethod;
    }

    public List<KeyFieldCache> getKeyFieldCaches() {
        return keyFieldCaches;
    }

    public boolean isKeyFieldCacheDataTypesUnique() {
        return keyFieldCacheDataTypesUnique;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
