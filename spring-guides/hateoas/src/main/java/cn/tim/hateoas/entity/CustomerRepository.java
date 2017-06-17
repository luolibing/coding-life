package cn.tim.hateoas.entity;

import java.util.List;

/**
 * Created by LuoLiBing on 16/1/18.
 */
public interface CustomerRepository {

    List<Customer> findAll();

    Customer findOne(Long id);

}
