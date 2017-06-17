package cn.tim.reactor.entity;

/**
 * Created by LuoLiBing on 15/10/26.
 */
public class QuoteResource {
    String type;
    Quote value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Quote getValue() {
        return value;
    }

    public void setValue(Quote value) {
        this.value = value;
    }
}
