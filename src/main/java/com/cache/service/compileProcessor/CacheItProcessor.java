package com.cache.service.compileProcessor;

import com.cache.core.annotations.CacheIt;
import com.cache.service.compileProcessor.processor.CompileProcessor;
import com.cache.service.compileProcessor.processor.impl.CompileProcessorImpl;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("com.cache.core.annotations.CacheIt")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
@SupportedOptions("debug")
public class CacheItProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Collection<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(CacheIt.class);
        List<TypeElement> types = ElementFilter.typesIn(annotatedClasses);
        Messager messager = processingEnv.getMessager();
        String packageName = null;
        String name = null;
        CompileProcessor processor = new CompileProcessorImpl();
        processor.getMapNames(types,messager,true);
        return true;
    }


}
