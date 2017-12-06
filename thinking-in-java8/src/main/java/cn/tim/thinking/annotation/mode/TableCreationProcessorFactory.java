package cn.tim.thinking.annotation.mode;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.util.SimpleElementVisitor8;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created by LuoLiBing on 17/1/14.
 */
public class TableCreationProcessorFactory extends AbstractProcessor {

    public Processor getProcessorFor() {
        return null;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

    public Collection<String> supportedOptions() {
        return Collections.emptySet();
    }

    private static class TableCreationProcessor extends AbstractProcessor {

        private final ProcessingEnvironment env;
        private String sql = "";
        public TableCreationProcessor(ProcessingEnvironment env) {
            this.env = env;
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            Set<? extends Element> elements = roundEnv.getRootElements();
            for(Element element : elements) {
                element.accept(new TableCreationVisitor(), null);
            }
            return false;
        }

        private class TableCreationVisitor extends SimpleElementVisitor8 {
            @Override
            public Object visitType(TypeElement e, Object o) {
                return super.visitType(e, o);
            }

            @Override
            public Object visitTypeParameter(TypeParameterElement e, Object o) {
                return super.visitTypeParameter(e, o);
            }
        }

    }
}
