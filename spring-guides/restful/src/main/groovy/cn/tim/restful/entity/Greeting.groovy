package cn.tim.restful.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by LuoLiBing on 15/9/29.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class Greeting {

    private final long id
    private final String content
    @JsonProperty(value = "_name")
    private String name = "like"

    public Greeting(long id, String content) {
        this.id = id
        this.content = content
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
    long getId() {
        return id
    }

    String getContent() {
        return content
    }

    String getName() {
        return name
    }
}
