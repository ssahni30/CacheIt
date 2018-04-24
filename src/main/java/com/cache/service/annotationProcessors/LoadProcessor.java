package com.cache.service.annotationProcessors;

import com.cache.core.MapData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public interface LoadProcessor {
    void loadData(String mapName, Map<String, MapData> mapNameToKeyFieldCache) throws IllegalAccessException, InstantiationException, InvocationTargetException;

    Map<String, Method> getAllMethodsWithLoadAnnotation();
}
