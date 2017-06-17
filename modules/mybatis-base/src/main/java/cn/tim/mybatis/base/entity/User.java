package cn.tim.mybatis.base.entity;

import lombok.Data;

/**
 * Created by luolibing on 2017/3/31.
 */
@Data
public class User {
    private Long id;
    private String accessToken;
    private Integer enabled;
    private String password;
    private String username;
    private Integer maxNumberOfDevices;
    private String nickname;
    private Integer exp;
    private Integer level;
    private Integer gold;
    private Integer gem;
}
