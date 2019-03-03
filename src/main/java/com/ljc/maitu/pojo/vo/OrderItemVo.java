package com.ljc.maitu.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author JYH
 * @Description
 * @date 2019/3/3 21:59
 */
@Data
public class OrderItemVo {
    private Long orderNo;

    private Integer productId;

    private String productName;
    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String createTime;
}
