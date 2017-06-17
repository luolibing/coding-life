package cn.tim.cache.jpa;

import cn.tim.cache.entity.Category;

/**
 * Created by LuoLiBing on 15/11/14.
 */
public interface CategoryRepository {

    public Category getCategoryById(String id);
}
