package cn.tim.thinking.annotation.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by LuoLiBing on 17/1/13.
 * apt只在java5中适用, 之后的版本, 使用JSR269标准, 提供了一套标准API来处理Annotations.
 * JSR269不仅仅是用来处理Annotation, 更强大的是建立了一个java语言本身的模型,
 * 它把method, package, constructor, type, variable, enum annotation等元素映射到Types和Elements,
 * 从而将Java语言的语义映射成为对象. 我们可以在javax.lang.model包下面看到这些类, 可以利用JSR269提供的API来构建一个功能丰富的元编程环境.
 *
 * JSR269Annotation Processor是在编译期而不是运行期间处理Annotation, 相当于一编译期的一个插件, 所以称为插入式注解处理.
 * 如果Annotation Processor处理Annotation时, 产生了新的java代码, 编译器会再次调用Annotation Processor, 直到没有新代码为止
 * 每次执行process()方法被称为一个round, 整个Annotation processing过程可以看做一个round的序列.
 */
@SupportedAnnotationTypes("cn.tim.thinking.annotation.apt.ExtractInterface")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class InterfaceExtractorProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<String> collect = annotations.stream().map(TypeElement::toString).collect(Collectors.toSet());
        if(!collect.contains("cn.tim.thinking.annotation.apt.ExtractInterface")) {
            System.out.println(false);
            return false;
        }


//        builder.append()
        // 获取源代码的映射对象
        Set<? extends Element> elements = roundEnv.getRootElements();
        for(Element e : elements) {
            ExtractInterface annotation = e.getAnnotation(ExtractInterface.class);
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("package %s;", e.getEnclosingElement().toString()));
            // 查看是否是公开类
            builder.append(Modifier.PUBLIC.toString());
            builder.append(" interface ");
            builder.append(annotation.value());
            builder.append("{");

//            builder.append()
            // 获取源代码对象的成员
            List<? extends Element> enclosedElements = e.getEnclosedElements();
            // 留下方法成员, 过滤掉其他成员
            List<ExecutableElement> ees = ElementFilter.methodsIn(enclosedElements);

            for(ExecutableElement ee : ees) {
                // 判断是否是课访问的public, 如果是则抽取出来
                Set<Modifier> modifiers = ee.getModifiers();
                // 只查找public的成员方法
                if(!modifiers.contains(Modifier.PUBLIC) || modifiers.contains(Modifier.STATIC)) {
                    continue;
                }
                builder.append(String.format("%s %s %s(", "public", ee.getReturnType(), ee.getSimpleName()));
                for(VariableElement var : ee.getParameters()) {
                    builder.append(String.format("%s %s", var.asType(), var.getSimpleName()));
                    builder.append(",");
                }
                if(ee.getParameters() != null && !ee.getParameters().isEmpty()) {
                    builder.setLength(builder.length() - 1);
                }
                builder.append(");");
            }
            builder.append("}");
            System.out.println(builder.toString());
            try {
                // 代码自动创建, 然后自动编译
                JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(annotation.value());
                Writer writer = sourceFile.openWriter();
                writer.write(builder.toString());
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
}
