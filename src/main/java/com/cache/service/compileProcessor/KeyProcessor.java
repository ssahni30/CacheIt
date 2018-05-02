package com.cache.service.compileProcessor;

import com.cache.core.annotations.CacheIt;
import com.cache.core.annotations.Key;
import com.cache.service.compileProcessor.processor.CompileProcessor;
import com.cache.service.compileProcessor.processor.impl.CompileProcessorImpl;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("com.cache.core.annotations.Key")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
@SupportedOptions("debug")
public class KeyProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Collection<? extends Element> annotatedFields = roundEnv.getElementsAnnotatedWith(Key.class);

        Messager messager = processingEnv.getMessager();
        Set<String> mapNames = new HashSet<>();
        for(Element annotatedFied : annotatedFields){
            Element element = annotatedFied.getEnclosingElement();
            String name = "";
            if(name.isEmpty()){
                String[] nameArray = element.getSimpleName().toString().split("\\.");
                name = nameArray[nameArray.length-1];
            }
            mapNames.add(name);
        }
        CompileProcessor processor = new CompileProcessorImpl();
        Collection<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(CacheIt.class);
        Set<String> mapNameSet = processor.getMapNames(ElementFilter.typesIn(annotatedClasses),messager,false);

        for(String mapName : mapNameSet){
            if(!mapNames.contains(mapName)){
                messager.printMessage(Diagnostic.Kind.ERROR,"No key found for this class: " +  mapName);
            }
        }
        return true;
    }
}
