package cn.tim.dto.entity.jpa;

import cn.tim.dto.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by liuzhihao on 16/3/1.
 */
public interface OrderRepository extends JpaRepository<Order,Long>{

    Page<Order> findByUserIDAndStatus(Pageable pageable, Long userID, Integer status);

    Page<Order> findByUserID(Pageable pageable, Long userID);

    Order findByCode(Long code);

    List<Order> findByUserIDOrderByCreateTimeDesc(Long userID);
}
