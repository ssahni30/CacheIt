package com.cache.service.annotationProcessors.impl;

import com.cache.core.KeyFieldCache;
import com.cache.core.MapData;
import com.cache.core.annotations.Key;
import com.cache.core.annotations.Load;
import com.cache.service.CacheMap;
import com.cache.service.annotationProcessors.LoadProcessor;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.*;
import java.util.*;

public class LoadProcessorImpl implements LoadProcessor {

    @Override
    public void loadData(String mapName, Map<String, MapData> mapNameToKeyFieldCache) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        MapData mapData = mapNameToKeyFieldCache.get(mapName);
        Method method = mapData.getInvokingMethod();
        Object objects = null;
        try {
            objects = method.invoke(method.getDeclaringClass().newInstance());
        } catch (IllegalAccessException e) {
            //This has been handled in compile time
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw e;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw e;
        }
        Collection<Object> objectCollection = null;
        if (objects instanceof List<?> || objects instanceof List<?>) {
            objectCollection = (Collection<Object>) objects;
        } else {
            return;
        }
        loadMapValues(objectCollection,mapNameToKeyFieldCache);
        mapNameToKeyFieldCache.get(mapName).setUpdated(new Date());
    }

    private  void loadMapValues(Collection<Object> objects, Map<String, MapData> mapNameToKeyFieldCache) throws IllegalAccessException {
        for(Map.Entry<String, MapData> mapNameSetEntry : mapNameToKeyFieldCache.entrySet()){
            if(mapNameSetEntry.getValue().getParentClass().isInstance(objects.iterator().next())){
                prepareKeyFieldCacheSet(objects,mapNameSetEntry.getValue().getKeyFieldCaches(),mapNameSetEntry.getValue().getParentClass());
            }
        }
        return;
    }

    private void prepareKeyFieldCacheSet(Collection<Object> objectList, List<KeyFieldCache> values, Class<?> parentClass) throws IllegalAccessException {
        for(KeyFieldCache keyFieldCache : values){
            for(Object object : objectList){
                Field keyField = null;
                for(Field field : parentClass.getDeclaredFields()){
                    if(field.getName().equals(keyFieldCache.getName())){
                        field.setAccessible(true);
                        keyField = field;
                        break;
                    }
                }
                Object fieldValue = keyField.get(object);
                Map<Object,Object> map = keyFieldCache.getCacheMap();
                if(keyField.getAnnotation(Key.class).multipleValuesAllowed()){
                    List<Object> objects =  map.get(fieldValue) == null? new ArrayList<>(): (List<Object>) map.get(fieldValue);
                    objects.add(object);
                    map.put(fieldValue,objects);
                } else {
                    map.put(fieldValue,object);
                }
            }
        }
        return;
    }


    @Override
    public Map<String, Method> getAllMethodsWithLoadAnnotation(){
        Map<String,Method> mapNameToMethod = new HashMap<>();
        Package[] packages = Package.getPackages();
        for (int i = 0; i < packages.length; i++) {
            Package packageObject = packages[i];
            Set<Method> methods = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(packageObject.getName())).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(Load.class);
            for(Method method : methods){
                if(mapNameToMethod.get(getName(method)) == null){
                    mapNameToMethod.put(getName(method),method);
                }
            }
        }
        return mapNameToMethod;
    }

    private String getName(Method method){
        String mapName = method.getAnnotation(Load.class).mapName();
        if(!mapName.isEmpty()){
            return mapName;
        }
        String methodReturnClass = null;
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType)type;
            Type[] arr = pType.getActualTypeArguments();
            Class<?> clazz = (Class<?>)arr[0];
            String[] packageNameArray = pType.getActualTypeArguments()[0].getTypeName().split("\\.");
            methodReturnClass = packageNameArray[packageNameArray.length-1];
        }
        return  methodReturnClass;
    }

}
