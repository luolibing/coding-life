package cn.tim.springsrc.context.editor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luolibing on 2017/5/31.
 */
public class DatePropertyEditor extends PropertyEditorSupport {

    private String format = "yyyy-MM-dd";

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println("arg0:" + text);
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date d = df.parse(text);
            this.setValue(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
