package cn.tim.data.entity.jpa;

import cn.tim.data.entity.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by LuoLiBing on 15/10/30.
 */
public interface TagJpaRepository extends CrudRepository<Tag, Long> {

}
