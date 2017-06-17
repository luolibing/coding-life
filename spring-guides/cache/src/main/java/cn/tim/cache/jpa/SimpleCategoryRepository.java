package cn.tim.cache.jpa;

import cn.tim.cache.entity.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 15/11/14.
 */
@Component
public class SimpleCategoryRepository implements CategoryRepository {

    @Override
    @Cacheable("categorys")
    public Category getCategoryById(String id) {
        System.out.println("invoke getCategory");
        return new Category(id, "test");
    }
}
