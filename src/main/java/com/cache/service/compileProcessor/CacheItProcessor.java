//package com.cache.service.processing;
//
//import com.cache.core.annotations.CacheIt;
//import com.google.auto.service.AutoService;
//
//import javax.annotation.compileProcessor.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.PackageElement;
//import javax.lang.model.element.TypeElement;
//import javax.lang.model.util.ElementFilter;
//import javax.tools.Diagnostic;
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
//@SupportedAnnotationTypes("com.cache.core.annotations.CacheIt")
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
//@SupportedOptions("debug")
//public class CacheItProcessor extends AbstractProcessor {
//
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        Collection<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(CacheIt.class);
//        List<TypeElement> types = ElementFilter.typesIn(annotatedClasses);
//        Messager messager = processingEnv.getMessager();
//        String packageName = null;
//        String name = null;
//
//        for(TypeElement type : types){
//            PackageElement packageElement = (PackageElement) type.getEnclosingElement();
//            packageName =  packageElement.getQualifiedName().toString();
//            name = type.getAnnotation(CacheIt.class).mapName();
//            if(name.contains(" ")){
//                messager.printMessage(Diagnostic.Kind.ERROR,"name cannot contain space");
//            }
//        }
//
//        return true;
//    }
//}
