package com.cache.service;

import com.cache.core.MapData;
import com.cache.service.annotationProcessors.CacheItProcessor;
import com.cache.service.annotationProcessors.LoadProcessor;
import com.cache.service.annotationProcessors.impl.CacheItProcessorImpl;
import com.cache.service.annotationProcessors.impl.LoadProcessorImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;


public class CacheMap {

    private static CacheMap cacheMap = null;

    public static void initiate(){
        if (cacheMap == null) {
            CacheItProcessor cacheInitialization = new CacheItProcessorImpl();
            cacheMap = new CacheMap(cacheInitialization.initialization());
        }
    }

    private Map<String,MapData> mapNameToKeyFieldCache;

    private CacheMap(Map<String,MapData> mapNameToKeyFieldCache) {
        this.mapNameToKeyFieldCache = mapNameToKeyFieldCache;
    }

    public static MapData getMapData(String name){
        return cacheMap.mapNameToKeyFieldCache.get(name);
    }

    public static void loadMapByName(String mapName) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if(cacheMap.mapNameToKeyFieldCache == null){
            throw new IllegalStateException();
        } else {
            LoadProcessor load = new LoadProcessorImpl();
            load.loadData(mapName, cacheMap.mapNameToKeyFieldCache);
        }
    }

    public static void loadAllMaps() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if(cacheMap.mapNameToKeyFieldCache == null) {
            throw new IllegalStateException();
        } else {
            for(String mapName : cacheMap.mapNameToKeyFieldCache.keySet()){
                loadMapByName(mapName);
            }
        }
    }

    public static Set<String> getAllMapNames(){
        return cacheMap.mapNameToKeyFieldCache.keySet();
    }

}
