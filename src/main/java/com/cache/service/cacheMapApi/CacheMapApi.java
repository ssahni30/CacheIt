package com.cache.service.cacheMapApi;

import java.lang.reflect.InvocationTargetException;

public interface CacheMapApi {

    <T> T getValueByNameAndKeyDataType(String name, Class<?> dataType, Object key) throws Exception;

    <T> T getValueByNameAndKey(String name, Object key) throws Exception;

    <T> T getValueByNameAndKeyName(String name, String keyName, Object key) throws Exception;

    void loadMapByName(String name) throws IllegalAccessException, InstantiationException, InvocationTargetException;

    void loadAllMaps() throws IllegalAccessException, InstantiationException, InvocationTargetException;

    void asyncMapLoad();
}
