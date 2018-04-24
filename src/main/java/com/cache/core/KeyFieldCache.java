package com.cache.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class KeyFieldCache {

    private Class<?> dataType;

    private String name;

    private Map<Object,Object> cacheMap = new ConcurrentHashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyFieldCache keyFieldCache = (KeyFieldCache) o;
        return Objects.equals(dataType, keyFieldCache.dataType) &&
                Objects.equals(name, keyFieldCache.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dataType, name);
    }

    public Map<Object, Object> getCacheMap() {

        return cacheMap;
    }

    public void setCacheMap(Map<Object, Object> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public KeyFieldCache(Class<?> dataType, String name) {
        this.dataType = dataType;
        this.name = name;
        this.cacheMap = new HashMap<>();
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "KeyFieldCache{" +
                ", dataType=" + dataType +
                ", name='" + name + '\'' +
                ", cacheMap=" + cacheMap +
                '}';
    }
}
