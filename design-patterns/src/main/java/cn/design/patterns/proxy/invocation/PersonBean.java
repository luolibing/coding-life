package cn.design.patterns.proxy.invocation;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public interface PersonBean {

    String getName();
    String getGender();
    String getInterests();
    int getHotOrNotRating();

    void setName(String name);
    void setGender(String gender);
    void setInterests(String interests);
    void setHotOrNotRating(int rating);
}
