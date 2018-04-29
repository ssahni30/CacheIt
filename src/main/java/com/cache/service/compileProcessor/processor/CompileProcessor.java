package com.cache.service.compileProcessor.processor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Set;

public interface CompileProcessor {
    Set<String> getMapNames(List<TypeElement> types, Messager messager, boolean logError);
}
