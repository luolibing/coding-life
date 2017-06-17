package cn.tim.java8.other;

import edu.emory.mathcs.backport.java.util.Arrays;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by LuoLiBing on 16/7/27.
 */
public class MyAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String className = null;
        Map<String, List<TestCase>> map = new HashMap<>();
        for(TypeElement annotation : annotations) {
            for(Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                TestCase[] testCases = element.getAnnotation(TestCase.class) != null
                        ? element.getAnnotation(TestCases.class).value()
                        : null;
                if(testCases == null) {
                    testCases = new TestCase[] {element.getAnnotation(TestCase.class)};
                }
                map.put(element.getSimpleName().toString(), Arrays.asList(testCases));

            }
        }
        return false;
    }
}
