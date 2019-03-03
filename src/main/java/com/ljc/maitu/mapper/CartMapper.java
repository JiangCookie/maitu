package com.ljc.maitu.mapper;

import com.ljc.maitu.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CartMapper extends Mapper<Cart> {

    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    int checkedOrUncheckedProduct(@Param("userId") Integer userId,@Param("productId")Integer productId,@Param("checked") Integer checked);

    /**
     * 获取购物车产品数量
     * @param userId
     * @return
     */
    int selectCartProductCount(@Param("userId") Integer userId);
}