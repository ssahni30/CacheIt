package com.cache.service.cacheMapApi.impl;

import com.cache.core.KeyFieldCache;
import com.cache.core.MapData;
import com.cache.service.CacheMap;
import com.cache.service.cacheMapApi.CacheMapApi;

import java.util.List;

public class CacheMapApiImpl implements CacheMapApi {

    @Override
    public Object getValueByNameAndKeyDataType(String name, Class<?> dataType, Object key) throws Exception {
        MapData mapData = CacheMap.getMapData(name);
        if(!mapData.isKeyFieldCacheDataTypesUnique()){
            throw new Exception("");
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
            throw new Exception("");
        }
        List<KeyFieldCache> cacheList = mapData.getKeyFieldCaches();
        KeyFieldCache keyField = null;
        for(KeyFieldCache keyFieldCache : cacheList){
            if(keyFieldCache.getDataType().equals(key.getClass().getTypeName())){
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
    public Object getValueByNameAndKeyName(String name, String keyName, Object key) throws Exception {
        KeyFieldCache keyField = null;
        for(KeyFieldCache keyFieldCache :  CacheMap.getMapData(name).getKeyFieldCaches()){
            if(keyFieldCache.getName().equals(keyName)){
                keyField = keyFieldCache;
            }
        }
        if(keyField == null){
            throw new Exception();
        }
        return keyField.getCacheMap().get(key);
    }
}
