package cn.tim.enumsample;

/**
 * Created by LuoLiBing on 16/3/25.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshichao on 16/3/23.
 */
public enum RoleType {

    UNKNOWN(0, "未知", ""),
    ZHONGJIAO(93, "", ""),
    WAIJIAO(94, "已取消", ""),
    DAYI(3, "答疑老师", ""),
    GUIHUA(4, "课程规划师", "");


    RoleType() {
    }

    RoleType(int code, String desc, String remark) {
        this.code = code;
        this.desc = desc;
        this.remark = remark;
    }

    private int code;
    private String desc;
    private String remark;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getRemark() {
        return remark;
    }

    private static Map<Integer, RoleType> varMap = new HashMap<>();

    static {
        for (RoleType v : RoleType.values()) {
            varMap.put(v.getCode(), v);
        }
    }

    public static RoleType get(int code) {
        if (varMap.containsKey(code)) {
            return varMap.get(code);
        }
        return UNKNOWN;
    }

    public static String getDesc(int code) {
        if (varMap.containsKey(code)) {
            return varMap.get(code).getDesc();
        }
        return "未知";
    }

    public static String getRemark(int code) {
        if (varMap.containsKey(code)) {
            return varMap.get(code).getRemark();
        }
        return "";
    }

}
