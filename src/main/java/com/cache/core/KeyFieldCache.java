package com.cache.core;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class KeyFieldCache<K, V> {

    private Class<?> dataType;

    private String name;

    private Map<K, V> cacheMap;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyFieldCache<?, ?> that = (KeyFieldCache<?, ?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Map<K, V> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(Map<K, V> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public KeyFieldCache(Class<?> dataType, String name) {
        this.dataType = dataType;
        this.name = name;
        this.cacheMap = new ConcurrentHashMap<>();
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
