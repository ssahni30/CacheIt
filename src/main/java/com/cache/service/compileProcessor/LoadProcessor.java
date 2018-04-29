package com.cache.service.compileProcessor;

import com.cache.core.annotations.CacheIt;
import com.cache.core.annotations.Load;
import com.cache.service.compileProcessor.processor.CompileProcessor;
import com.cache.service.compileProcessor.processor.impl.CompileProcessorImpl;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("com.cache.core.annotations.Load")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
@SupportedOptions("debug")
public class LoadProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        Collection<? extends Element> annotatedMethods = roundEnv.getElementsAnnotatedWith(Load.class);
        String name = null;
        Set<String> mapNameSet = new HashSet<>();
        for (Element annotatedElement : annotatedMethods) {
            name = annotatedElement.getAnnotation(Load.class).mapName();
            if (name.isEmpty()){
                ExecutableElement methodElement = (ExecutableElement) annotatedElement;
                String[] returnArray = methodElement.getReturnType().toString().split("\\.");
                String className = returnArray[returnArray.length - 1].replace(">","");
                name = className;
            }
            if(mapNameSet.contains(name)){
                messager.printMessage(Diagnostic.Kind.ERROR,"Multiple Methods for same map name exist "+ name);
            } else {
                mapNameSet.add(name);
            }
        }

        CompileProcessor processor = new CompileProcessorImpl();
        Collection<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(CacheIt.class);
        processor.getMapNames(ElementFilter.typesIn(annotatedClasses),messager,false);
        return true;
    }
}
