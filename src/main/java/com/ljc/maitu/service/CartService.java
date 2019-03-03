package com.ljc.maitu.service;

import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.pojo.vo.CartVo;

/**
 * @author JYH
 * 2019/3/3 8:59
 */
public interface CartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    ServerResponse<CartVo> list (Integer userId);
    ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
