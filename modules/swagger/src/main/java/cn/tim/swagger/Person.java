package cn.tim.swagger;

import io.swagger.annotations.ApiModelProperty;

/**
 * User: luolibing
 * Date: 2017/6/2 15:42
 */
public class Person {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "年龄", allowableValues = "range(0-200)")
    private Integer age;

    @ApiModelProperty(value = "性别", allowableValues = "1-男,0-女")
    private Integer gender;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String isbn;

    @ApiModelProperty(value = "家庭住址", allowableValues = "16位")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
