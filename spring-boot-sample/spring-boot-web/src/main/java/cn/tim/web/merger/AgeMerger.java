package cn.tim.web.merger;

import org.jdto.SinglePropertyValueMerger;

/**
 * Created by luolibing on 2018/3/11.
 */
public class AgeMerger implements SinglePropertyValueMerger<String, Integer> {
    @Override
    public String mergeObjects(Integer value, String[] extraParam) {
        if(value == null) {
            return null;
        }
        return value + "岁了";
    }

    @Override
    public boolean isRestoreSupported(String[] params) {
        return false;
    }

    @Override
    public Integer restoreObject(String object, String[] params) {
        return null;
    }
}
