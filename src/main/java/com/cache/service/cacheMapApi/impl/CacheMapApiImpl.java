package com.cache.service.cacheMapApi.impl;

import com.cache.core.KeyFieldCache;
import com.cache.core.MapData;
import com.cache.service.CacheMap;
import com.cache.service.cacheMapApi.CacheMapApi;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheMapApiImpl implements CacheMapApi {

    @Override
    public Object getValueByNameAndKeyDataType(String name, Class<?> dataType, Object key) throws Exception {
        MapData mapData = CacheMap.getMapData(name);
        if(!mapData.isKeyFieldCacheDataTypesUnique()){
            throw new Exception("Multiple Data Types of same class");
        }
        List<KeyFieldCache> cacheList = mapData.getKeyFieldCaches();
        KeyFieldCache keyField = null;
        for(KeyFieldCache keyFieldCache : cacheList){
            if(keyFieldCache.getDataType().equals(dataType)){
                keyField = keyFieldCache;
                break;
            }
        }
        if(keyField == null){
            throw new Exception();
        }
        return keyField.getCacheMap().get(key);
    }

    @Override
    public <T> Object getValueByNameAndKey(String name,T key) throws Exception {
        MapData mapData = CacheMap.getMapData(name);
        if(!mapData.isKeyFieldCacheDataTypesUnique()){
            throw new Exception("Multiple Data Types of same class");
        }
        List<KeyFieldCache> cacheList = mapData.getKeyFieldCaches();
        KeyFieldCache keyField = null;
        for(KeyFieldCache keyFieldCache : cacheList){
            if(keyFieldCache.getDataType().getName().equals(key.getClass().getTypeName())){
                keyField = keyFieldCache;
                break;
            }
        }
        if(keyField == null){
            throw new Exception("No data type found for " +  key.getClass().getTypeName());
        }
        return keyField.getCacheMap().get(key);
    }

    @Override
    public Object getValueByNameAndKeyName(String name, String keyName, Object key) throws Exception {
        KeyFieldCache keyField = null;
        for(KeyFieldCache keyFieldCache :  CacheMap.getMapData(name).getKeyFieldCaches()){
            if(keyFieldCache.getName().equals(keyName)){
                keyField = keyFieldCache;
            }
        }
        if(keyField == null){
            throw new Exception("No key with given name");
        }
        return keyField.getCacheMap().get(key);
    }

    @Override
    public void loadMapByName(String mapName) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        CacheMap.loadMapByName(mapName);
    }

    @Override
    public void loadAllMaps() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        CacheMap.loadAllMaps();
    }

    @Override
    public void asyncMapLoad(){
        ExecutorService executorService1 = Executors.newFixedThreadPool(5);
        for(String mapName : CacheMap.getAllMapNames()){
            executorService1.execute(new AsyncMapLoad(mapName));
        }

    }

    private class AsyncMapLoad implements Runnable {
        private String mapName;

        public AsyncMapLoad(String mapName) {
            this.mapName = mapName;
        }

        @Override
        public void run() {
            try {
                CacheMap.loadMapByName(mapName);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }


}
