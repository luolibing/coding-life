package cn.tim.java8.lambda;

import com.google.common.collect.Lists;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Created by LuoLiBing on 16/7/11.
 */
public class C3E12 extends Application implements Exercise {
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @FunctionalInterface
    interface ColorTransformer {
        Color apply(int x, int y, Color color);
    }

    @FunctionalInterface
    interface ColorTransformer1<Color> extends UnaryOperator<Color> {

    }
}

class LatentImage {
    private Image in;
    private List<ColorTransformer> pendingOperations;
    private LatentImage(Image in) {
        this.in = in;
        pendingOperations = Lists.newArrayList();
    }

    /**
     * 链式编程需要返回对象自身
     * @param f
     * @return
     */
    public LatentImage transform(UnaryOperator<Color> f) {
        // 将UnaryOperator适配为ColorTransformer
        return transform(map(f));
    }

    public LatentImage transform(ColorTransformer t) {
        pendingOperations.add(t);
        return this;
    }


    public Image toImage() {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color color = in.getPixelReader().getColor(i, j);
                for(ColorTransformer f: pendingOperations) {
                    color = f.apply(i, j, color);
                }
                out.getPixelWriter().setColor(i, j, color);
            }
        }
        return out;
    }

    public static LatentImage from(Image in) {
        return new LatentImage(in);
    }

    public static ColorTransformer map(UnaryOperator<Color> op) {
        return (x, y, color) -> op.apply(color);
    }
}


