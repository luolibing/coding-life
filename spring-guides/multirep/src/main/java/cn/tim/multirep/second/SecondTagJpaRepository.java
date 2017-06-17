package cn.tim.multirep.second;

import cn.tim.multirep.second.entity.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by LuoLiBing on 15/10/31.
 */
public interface SecondTagJpaRepository extends CrudRepository<Tag, Long> {
}
