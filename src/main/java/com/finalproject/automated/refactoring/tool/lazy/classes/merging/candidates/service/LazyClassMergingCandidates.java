package com.finalproject.automated.refactoring.tool.lazy.classes.merging.candidates.service;

import com.finalproject.automated.refactoring.tool.model.ClassModel;
import lombok.NonNull;

import java.util.List;

public interface LazyClassMergingCandidates {
    ClassModel searchTarget(@NonNull List<ClassModel> classes, @NonNull ClassModel lazyClass);

    List<ClassModel> searchTarget(@NonNull List<ClassModel> classes, @NonNull List<ClassModel> lazyClasses);
}
