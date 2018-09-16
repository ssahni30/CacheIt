package com.cache;

import com.cache.core.annotations.Load;

import java.util.ArrayList;
import java.util.List;

public class TestCases {



    @Load
    public List<DummyClass> s1(){
        List<DummyClass> dummyClasses= new ArrayList<>();
        dummyClasses.add(new DummyClass(true,"sagar1","sahni1"));
        dummyClasses.add(new DummyClass(false,"sagar2","sahni1"));
        dummyClasses.add(new DummyClass(true,"sagar1","sahni2"));
        return dummyClasses;
    }


}
