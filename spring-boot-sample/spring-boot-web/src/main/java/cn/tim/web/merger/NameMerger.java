package cn.tim.web.merger;

import org.jdto.MultiPropertyValueMerger;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luolibing on 2018/3/11.
 */
public class NameMerger implements MultiPropertyValueMerger<String> {

    @Override
    public String mergeObjects(List<Object> values, String[] extraParam) {
        if(CollectionUtils.isEmpty(values)) {
            return null;
        }
        return values.stream().map(Object::toString).collect(Collectors.joining(""));
    }
}
