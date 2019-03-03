package com.ljc.maitu.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author JYH
 * @Description
 * @date 2019/3/3 22:35
 */
@Data
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHost;
}
