package com.cache.core;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapData {


    private Class<?> parentClass;

    private Method invokingMethod;

    private List<KeyFieldCache> keyFieldCaches;

    private boolean keyFieldCacheDataTypesUnique;

    private Date updated;

    public MapData(Class<?> parentClass, Method method, List<KeyFieldCache> keyFieldCacheSet,boolean keyFieldCacheDataTypesUnique) {
        this.parentClass = parentClass;
        this.invokingMethod = method;
        this.keyFieldCaches = keyFieldCacheSet;
        this.keyFieldCacheDataTypesUnique = keyFieldCacheDataTypesUnique;
    }

    public Class<?> getParentClass() {
        return parentClass;
    }

    public void setParentClass(Class<?> parentClass) {
        this.parentClass = parentClass;
    }

    public Method getInvokingMethod() {
        return invokingMethod;
    }

    public void setInvokingMethod(Method invokingMethod) {
        this.invokingMethod = invokingMethod;
    }

    public List<KeyFieldCache> getKeyFieldCaches() {
        return keyFieldCaches;
    }

    public void setKeyFieldCaches(List<KeyFieldCache> keyFieldCaches) {
        this.keyFieldCaches = keyFieldCaches;
    }

    public boolean isKeyFieldCacheDataTypesUnique() {
        return keyFieldCacheDataTypesUnique;
    }

    public void setKeyFieldCacheDataTypesUnique(boolean keyFieldCacheDataTypesUnique) {
        this.keyFieldCacheDataTypesUnique = keyFieldCacheDataTypesUnique;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
