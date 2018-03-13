package cn.tim.ehcache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: luolibing
 * Date: 2018/3/13 19:34
 */
@Service
public class RoleService {

    public void invokePrivate() {

    }

    @Cacheable(value = "roleWrite", key = "#role.getRoleId()")
    public List<Integer> getRoleByPrivate(final Role role) {
        System.out.println("getRoleByPrivate by selected");
        Random rand = new Random();
        return IntStream.range(0, 10).boxed().map(i -> rand.nextInt(100))
                .collect(Collectors.toList());
    }
}
