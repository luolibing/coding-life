package cn.tim.http.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 枚举验证扩展供javax扩展
 * User: luolibing
 * Date: 2017/7/12 16:32
 */
public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Object> {

    List<Object> valueList = null;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return valueList.contains(value);
    }

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();
        Enum[] enumValArr = enumClass.getEnumConstants();
        valueList = new ArrayList<>();

        // TODO group功能
//        String[] groups = constraintAnnotation.groups();
//        if(groups.length > 0) {
//            for(String group : groups) {
//                for(Enum enumVal : enumValArr) {
////                    if(enumVal)
//                }
//            }
//        }

        Class<? extends Functions> functionsClass = constraintAnnotation.valueFunction();
        Functions functions = null;

        try {
            if(!functionsClass.isInterface()) {
                functions = functionsClass.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        for(Enum enumVal : enumValArr) {
            if(functions == null) {
                valueList.add(Functions.applyDefault(enumVal));
            } else {
                valueList.add(functions.apply(enumVal));
            }
        }
    }

}