package cn.tim.thinking.annotation.jsr269;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by LuoLiBing on 17/1/14.
 */
@SupportedAnnotationTypes("cn.tim.thinking.annotation.jsr269.ToBeTested")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyAnnotationProcessor extends AbstractProcessor {

    private void note(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(TypeElement te : annotations) {
            note("annotation: " + te);
        }

        // 获取源代码的映射对象
        Set<? extends Element> elements = roundEnv.getRootElements();
        for(Element e : elements) {
            // 获取源代码对象的成员
            List<? extends Element> enclosedElements = e.getEnclosedElements();
            // 留下方法成员, 过滤掉其他成员
            List<ExecutableElement> ees = ElementFilter.methodsIn(enclosedElements);

            for(ExecutableElement ee : ees) {
                note("--ExecutableElement name is " + ee.getSimpleName());
                // 获取方法上的注解集合
                List<? extends AnnotationMirror> as = ee.getAnnotationMirrors();
                note("--as=" + as);

                for(AnnotationMirror am : as) {
                    // 获取annotation的值
                    Map<? extends ExecutableElement, ? extends AnnotationValue> map
                            = am.getElementValues();
                    map.forEach((k, v) -> note("---" + ee.getSimpleName() + "." + k.getSimpleName() + "=" + v.getValue()));
                }
            }
        }
        return false;
    }
}
