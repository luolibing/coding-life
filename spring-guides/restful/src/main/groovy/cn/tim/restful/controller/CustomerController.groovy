package cn.tim.restful.controller

import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest
/**
 * Created by LuoLiBing on 15/10/15.
 */
@RestController
class CustomerController extends BaseController {

    /*@RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String index() {
        return "success";
    }*/

    @RequestMapping(value = "/upload", method = RequestMethod.PATCH)
    public String upload(String base, HttpServletRequest request) {
        def map = request.getParameterMap()
        println "aaaa"
        println map
        println base
        return "aaaa"
    }

    @RequestMapping(value = "/get/{username}", method = RequestMethod.GET)
    public Object get(@PathVariable String username) {
        //Collections.singletonMap("key", username)
        def map = new HashMap()
        map.put("key", username)
        map
    }

    /**
     * @Cacheable 有个坑， 在缓存null值的时候，如果后面没有地方evict这个缓存，那么这个null缓存会一直存在
     * 解决方案：
     * 1 在查询的时候添加一个@Cacheable里面添加unless，只有当unless成立时才缓存，例如unless = "#result != null", 或者在condition=""表达式中写上条件
     * 2 在增删改的时候都添加上@CacheEvict，这样才能确保缓存的有效性，本身null值也是缓存的一种，并不是无效的缓存。
     * @param id
     * @return
     */
    @Cacheable(value = "findOne", key = "#id", unless = "#result != null")
    @GetMapping(value = "/findOne/{id}")
    Object findOne(@PathVariable Long id) {
        if(id % 2 == 0) {
            return ResponseEntity.ok().build()
        } else {
            return null
        }
    }

    /**
     * @CachePut会每次都调用方法体，并且缓存返回结果，所以如果是更新操作，有返回结果还好，没有返回结果，则只有用@CacheEvict来清掉缓存
     * @CachePut常用的场景是：
     * 1 更新单体，然后刷新单体缓存
     * 2 添加单体，然后将添加的单体加入到集合缓存
     *
     * 与@Cacheable的区别， Cacheable当已经有缓存的时候不会再次调用方法体，而@CachePut则必然会调用方法体。
     * @return
     */
    @Cacheable(value = "findAll")
    @GetMapping(value = "/findAll")
    Object findAll() {
        List<String> result = Arrays.asList("a", "b")
    }
}
