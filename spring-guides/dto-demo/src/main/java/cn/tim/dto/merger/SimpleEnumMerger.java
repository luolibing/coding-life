package cn.tim.dto.merger;

import cn.tim.dto.enumentity.BaseEnum;
import cn.tim.dto.enumentity.EnumProvider;
import org.jdto.SinglePropertyValueMerger;

/**
 * Created by LuoLiBing on 16/3/18.
 */
public class SimpleEnumMerger implements SinglePropertyValueMerger<String, Integer> {

    @Override
    public String mergeObjects(Integer value, String[] extraParam) {
        if(value == null) {
            return null;
        }
        if(extraParam == null || extraParam.length == 0) {
            return null;
        }
        BaseEnum baseEnum = EnumProvider.getEnum(extraParam[0]);
        if(baseEnum == null) {
            return null;
        }
        return baseEnum.getName(value);
    }

    @Override
    public boolean isRestoreSupported(String[] strings) {
        return false;
    }

    @Override
    public Integer restoreObject(String s, String[] strings) {
        return null;
    }
}
