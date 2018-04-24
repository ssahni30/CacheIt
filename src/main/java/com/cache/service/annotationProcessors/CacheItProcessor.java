package com.cache.service.annotationProcessors;

import com.cache.core.MapData;

import java.util.Map;

public interface CacheItProcessor {
    Map<String, MapData> initialization();
}
