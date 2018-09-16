package com.cache;

import com.cache.service.CacheMap;
import com.cache.service.cacheMapApi.CacheMapApi;
import com.cache.service.cacheMapApi.impl.CacheMapApiImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.base.Predicates.instanceOf;

public class CacheTest {

    private CacheMapApi cacheMapApi;

    @Before
    public void setUp() throws Exception{
        CacheMap.initiate();
        this.cacheMapApi = new CacheMapApiImpl();
    }

    @Test
    public void testGetValueByNameAndKeyDataType() throws Exception {
        Object dummyClass = cacheMapApi.getValueByNameAndKeyName("DummyClass","keyWithNoMultipleValues","sagar1");
        Assert.assertEquals(dummyClass.getClass(), DummyClass.class);
        DummyClass dummyClass1 = (DummyClass) dummyClass;
        Assert.assertEquals(dummyClass1.keyWithNoMultipleValues, "sagar1");
    }
}
