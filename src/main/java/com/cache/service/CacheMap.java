package com.cache.service;

import com.cache.core.MapData;
import com.cache.service.annotationProcessors.CacheItProcessor;
import com.cache.service.annotationProcessors.LoadProcessor;
import com.cache.service.annotationProcessors.impl.CacheItProcessorImpl;
import com.cache.service.annotationProcessors.impl.LoadProcessorImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;


public class CacheMap {

    private static CacheMap cacheMap = null;

    public static void initiate(){
        if (cacheMap == null) {
            CacheItProcessor cacheInitialization = new CacheItProcessorImpl();
            cacheMap = new CacheMap(cacheInitialization.initialization());
        }
    }

//    public static CacheMap getCacheMap() {
//        return cacheMap;
//    }

    private Map<String,MapData> mapNameToKeyFieldCache;

    private CacheMap(Map<String,MapData> mapNameToKeyFieldCache) {
        this.mapNameToKeyFieldCache = mapNameToKeyFieldCache;
    }

    public static MapData getMapData(String name){
        return cacheMap.mapNameToKeyFieldCache.get(name);
    }

    public void loadMapByName(String mapName) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if(mapNameToKeyFieldCache == null){
            throw new IllegalStateException();
        } else {
            LoadProcessor load = new LoadProcessorImpl();
            load.loadData(mapName, this.mapNameToKeyFieldCache);
        }
    }

}
