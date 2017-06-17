package cn.tim.version.entity.jpa;

import cn.tim.version.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by LuoLiBing on 15/12/15.
 */
public interface BookJpaRepository extends JpaRepository<Book, Long> {
}
