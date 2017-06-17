package cn.tim.reback.resource.entity.jpa;

import cn.tim.reback.resource.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by LuoLiBing on 16/3/14.
 */
public interface ResourceJpaRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByType(String type);
}
