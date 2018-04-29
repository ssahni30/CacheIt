package com.cache.service.compileProcessor.processor.impl;

import com.cache.core.annotations.CacheIt;
import com.cache.service.compileProcessor.processor.CompileProcessor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompileProcessorImpl implements CompileProcessor {

    @Override
    public Set<String> getMapNames(List<TypeElement> types, Messager messager, boolean logError){
        String name = null;
        Set<String> mapNames = new HashSet<>();
        for(TypeElement type : types){
            name = type.getAnnotation(CacheIt.class).mapName();
            if(name.isEmpty()){
                String[] nameArray = type.toString().split("\\.");
                name = nameArray[nameArray.length-1];
            }
            if(mapNames.contains(name) && logError){
                messager.printMessage(Diagnostic.Kind.ERROR,"Multiple Maps with same name exist "+ name);
            } else {
                mapNames.add(name);
            }
        }
        return mapNames;
    }

}
