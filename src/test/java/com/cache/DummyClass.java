package com.cache;

import com.cache.core.annotations.CacheIt;
import com.cache.core.annotations.Key;
import com.cache.core.annotations.Load;

import java.util.ArrayList;
import java.util.List;

@CacheIt(cron = "0 0/2 * * * ?")
public class DummyClass {

    public boolean a;

    @Key
    public String keyWithNoMultipleValues;

    @Key(multipleValuesAllowed = true)
    public String keyWithMultipleValues;

    public DummyClass(boolean a, String keyWithNoMultipleValues, String keyWithMultipleValues) {
        this.a = a;
        this.keyWithNoMultipleValues = keyWithNoMultipleValues;
        this.keyWithMultipleValues = keyWithMultipleValues;
    }



}
