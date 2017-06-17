package cn.tim.hateoas.entity;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/1/18.
 */
@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private final List<Customer> customers = new ArrayList<>();

    public InMemoryCustomerRepository() {
        this.customers.add(new Customer(1L, "luo", "libing"));
        this.customers.add(new Customer(2L, "liu", "xiaoling"));
        this.customers.add(new Customer(2L, "luo", "libing"));
    }

    @Override
    public List<Customer> findAll() {
        return this.customers;
    }

    @Override
    public Customer findOne(Long id) {
        for (Customer customer : this.customers) {
            if (ObjectUtils.nullSafeEquals(customer.getId(), id)) {
                return customer;
            }
        }
        return null;
    }
}
