package com.ljc.maitu.mapper;

import com.ljc.maitu.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderItemMapper extends Mapper<OrderItem> {

    void  batchInsert(@Param("orderItemList")List<OrderItem> orderItemList);
}