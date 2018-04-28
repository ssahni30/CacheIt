package com.cache.service.annotationProcessors.impl;

import com.cache.core.KeyFieldCache;
import com.cache.core.MapData;
import com.cache.core.annotations.CacheIt;
import com.cache.core.annotations.Key;
import com.cache.service.annotationProcessors.CacheItProcessor;
import com.cache.service.annotationProcessors.LoadProcessor;
import org.reflections.Reflections;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CacheItProcessorImpl implements CacheItProcessor{

    private LoadProcessor loadProcessor = new LoadProcessorImpl();

    private Map<String,MapData> mapNameToKeyFieldCache = new ConcurrentHashMap<>();

    @Override
    public Map<String, MapData> initialization(){
        Set<Class<?>> annotatedClasses = getAllClassesWithCacheAnnotation();
        Map<String, List<Field>> mapNameToFieldKeys = new HashMap<>();
        List<String> keys = new ArrayList<>();
        Set<String> mapsToBeLoadedOnStartup = new HashSet<>();
        String mapName;
        for(Class<?> clazz : annotatedClasses){
            String[] packageName = getName(clazz).split("\\.");
            mapName = packageName[packageName.length-1];
            keys.add(getName(clazz));
            mapNameToFieldKeys.put(mapName,getMapKeys(clazz));
            if(clazz.getAnnotation(CacheIt.class).loadOnStartup()){
                mapsToBeLoadedOnStartup.add(mapName);
            }
        }

        Map<String,Method> mapNameToMethod = this.loadProcessor.getAllMethodsWithLoadAnnotation();

        for(Map.Entry<String,List<Field>> entry : mapNameToFieldKeys.entrySet()){
            List<KeyFieldCache> keyFieldCaches = new ArrayList<>();
            Set<Class<?>> fields = new HashSet<>();
            boolean uniqueDataTypes = true;
            Class<?> declaringClass = entry.getValue().get(0).getDeclaringClass();
            for(Field field: entry.getValue()){
                if(uniqueDataTypes && !fields.contains(field.getType())){
                    fields.add(field.getType());
                } else {
                    uniqueDataTypes = false;
                }
                KeyFieldCache keyFieldCache = new KeyFieldCache(field.getType(),field.getName());
                keyFieldCaches.add(keyFieldCache);
            }
            this.mapNameToKeyFieldCache.put(entry.getKey(),new MapData(declaringClass,mapNameToMethod.get(entry.getKey()),keyFieldCaches,uniqueDataTypes));
        }

        loadMapsOnStartup(mapsToBeLoadedOnStartup,this.mapNameToKeyFieldCache);
        return this.mapNameToKeyFieldCache;
    }

    private void loadMapsOnStartup(Set<String> mapsToBeLoadedOnStartup, Map<String, MapData> mapNameToKeyFieldCache){
        for(String mapName : mapsToBeLoadedOnStartup){
            try {
                this.loadProcessor.loadData(mapName,this.mapNameToKeyFieldCache);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private Set<Class<?>> getAllClassesWithCacheAnnotation() {
        Set<Class<?>> annotatedClasses = new HashSet<>();
        Map<String,Class<?>> nameToClassMap = new HashMap<>();
        Package[] pa = Package.getPackages();
        for (int i = 0; i < pa.length; i++) {
            Package p = pa[i];
            Set<Class<?>> classes = new Reflections(p.getName()).getTypesAnnotatedWith(CacheIt.class);
            annotatedClasses.addAll(classes);
            for(Class<?> clazz : classes){
                nameToClassMap.put(getName(clazz),clazz);
            }
        }

        return annotatedClasses;
    }

    private List<Field> getMapKeys(Class<?> cl) {
        List<Field> mapKeys = new ArrayList<>();
        for(Field field : cl.getDeclaredFields()){
            if (field.isAnnotationPresent(Key.class)) {
                mapKeys.add(field);
            }
        }
        return mapKeys;
    }

    private String getName(Class<?> clazz){
        String mapName = clazz.getAnnotation(CacheIt.class).mapName();
        return !mapName.isEmpty() ? mapName : clazz.getName();
    }

}
