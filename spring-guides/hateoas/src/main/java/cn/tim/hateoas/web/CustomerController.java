package cn.tim.hateoas.web;

import cn.tim.hateoas.entity.Customer;
import cn.tim.hateoas.entity.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by LuoLiBing on 16/1/18.
 */
@Controller
@RequestMapping(value = "/customers")
@ExposesResourceFor(Customer.class)
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityLinks entityLinks;

    /**
     * {
     "_embedded": {
     "customerList": [
     {
     "id": 1,
     "firstName": "luo",
     "lastName": "libing"
     },
     {
     "id": 2,
     "firstName": "liu",
     "lastName": "xiaoling"
     },
     {
     "id": 2,
     "firstName": "luo",
     "lastName": "libing"
     }
     ]
     },
     "_links": {
     "self": {
     "href": "http://localhost:8080/customers"
     }
     }
     }
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity<Resources<Customer>> getAllCustomers() {
        Resources<Customer> resources = new Resources<>(customerRepository.findAll());
        resources.add(this.entityLinks.linkToCollectionResource(Customer.class));
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * {
         "id": 1,
         "firstName": "luo",
         "lastName": "libing",
         "_links": {
         "self": {
         "href": "http://localhost:8080/customers/1"
         }
         }
         }
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity<Resource<Customer>> getCustomerById(@PathVariable Long id) {
        Resource<Customer> resource = new Resource<>(this.customerRepository.findOne(id));
        resource.add(this.entityLinks.linkToSingleResource(Customer.class, id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
