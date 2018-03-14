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

    public List<Integer> getRoleByPrivate(final Role role) {
        return invokePrivate(role);
    }

    @Cacheable(value = "roleWrite", key = "#role.getRoleId()")
    private final List<Integer> invokePrivate(final Role role) {
        System.out.println("getRoleByPrivate by selected");
        return getRoleIds();
    }

    @Cacheable(value = "roleWrite", key = "#role.getRoleId()")
    public List<Integer> getRoleByPublic(final Role role) {
        System.out.println("getRoleByPrivate by selected");
        return getRoleIds();
    }

    @Cacheable(value = "roleWrite", key = "#role.getRoleId()")
    public final List<Integer> getRoleByFinal(final Role role) {
        System.out.println("getRoleByPrivate by selected");
        return getRoleIds();
    }

    private List<Integer> getRoleIds() {
        Random rand = new Random();
        return IntStream.range(0, 10).boxed().map(i -> rand.nextInt(100))
                .collect(Collectors.toList());
    }
}
