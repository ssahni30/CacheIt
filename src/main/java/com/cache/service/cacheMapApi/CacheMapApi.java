package com.cache.service.cacheMapApi;

import java.util.Map;

public interface CacheMapApi {

    Object getValueByNameAndKeyDataType(String name, Class<?> dataType, Object key) throws Exception;

    <T> Object getValueByNameAndKey(String name, T key) throws Exception;

    Object getValueByNameAndKeyName(String name, String keyName, Object key) throws Exception;
}
