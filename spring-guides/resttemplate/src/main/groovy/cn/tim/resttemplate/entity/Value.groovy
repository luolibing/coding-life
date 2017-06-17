package cn.tim.resttemplate.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by LuoLiBing on 15/9/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Value {

    private Date id
    private String content
    private int age

    public Greeting(Date id, String content, int age) {
        this.id = id
        this.content = content
        this.age = age
    }

    public Greeting() {
    }


    public void genericException() {
        int i = 1/0
    }

    public void runtimeException() {
        try {
            int i = 1/0
        } catch (Exception e) {
            throw new RuntimeException("除0异常")
        }
    }

    /***
     * 在使用springMvc时，需要在view上调用entity的get方法，虽然groovy在调用时不需要显示声明get方法，但是在springMvc中，不声明会报
     * Could not find acceptable representation 异常
     * @return
     */
    Date getId() {
        return id
    }

    String getContent() {
        return content
    }

    void setId(Date id) {
        this.id = id
    }

    void setContent(String content) {
        this.content = content
    }

    int getAge() {
        return age
    }

    void setAge(int age) {
        this.age = age
    }
}
