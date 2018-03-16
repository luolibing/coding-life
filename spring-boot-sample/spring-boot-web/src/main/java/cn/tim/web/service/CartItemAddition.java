package cn.tim.web.service;

import org.springframework.stereotype.Service;

/**
 * User: luolibing
 * Date: 2018/3/15 12:51
 */
@Service
public class CartItemAddition extends Item {
    @Override
    public boolean processItem(Long productId, Integer quantity) {
        return false;
    }
}
