package cn.tim.resttemplate.util;

import org.apache.commons.lang.StringUtils;

/***
 * Emoji特殊表情处理
 */
public class TextUtils {

    public static String stripEmoji(String text) {
        if(StringUtils.isEmpty(text)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        long count = Character.codePointCount(text, 0, text.length());
        for (int index = 0; index < count; index++) {
            int codePoint = Character.codePointAt(text, index);
            if (Character.charCount(codePoint) <= 1) {
                builder.appendCodePoint(codePoint);
            }
        }
        return builder.toString();
    }

}
