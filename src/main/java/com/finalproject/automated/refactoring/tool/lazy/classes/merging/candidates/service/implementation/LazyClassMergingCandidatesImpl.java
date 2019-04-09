package com.finalproject.automated.refactoring.tool.lazy.classes.merging.candidates.service.implementation;

import com.finalproject.automated.refactoring.tool.lazy.classes.merging.candidates.service.LazyClassMergingCandidates;
import com.finalproject.automated.refactoring.tool.model.ClassModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LazyClassMergingCandidatesImpl implements LazyClassMergingCandidates {
    @Override
    public ClassModel searchTarget(List<ClassModel> classes, ClassModel lazyClass) {
        String sourceClassName = lazyClass.getName();
        int targetClassIndex = 0;
        int callCountMax = 0;
        for(ClassModel target : classes){
            if(!target.getName().equals(lazyClass.getName())){
                Pattern pattern = Pattern.compile(sourceClassName+"\\s+\\w+", Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(target.getFullContent());
                while(matcher.find()){
                    String[] temp = target.getFullContent().substring(matcher.start(), matcher.end()).trim().split(" ");
                    Pattern callPattern = Pattern.compile(temp[1]+"\\.", Pattern.MULTILINE);
                    Matcher callMatcher = pattern.matcher(target.getFullContent());
                    int count = 0;
                    while (callMatcher.find()) {
                        count++;
                    }
                    if(count > callCountMax){
                        callCountMax = count;
                        targetClassIndex = classes.indexOf(target);
                    }
                    else if(count == callCountMax){
                        if(target.getLoc() < classes.get(targetClassIndex).getLoc()){
                            targetClassIndex = classes.indexOf(target);
                        }
                    }
                }
            }
        }
        if(callCountMax > 0){
            return classes.get(targetClassIndex);
        }
        return null;
    }

    @Override
    public List<ClassModel> searchTarget(List<ClassModel> classes, List<ClassModel> lazyClasses) {
        List<ClassModel> targetClasses = new ArrayList<>();
        for(ClassModel source : lazyClasses){
            targetClasses.add(searchTarget(classes, source));
        }
        return null;
    }
}
