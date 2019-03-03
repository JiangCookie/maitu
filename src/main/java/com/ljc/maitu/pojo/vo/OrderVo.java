package com.ljc.maitu.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author JYH
 * @Description
 * @date 2019/3/3 22:00
 */
@Data
public class OrderVo {
    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private String paymentTypeDesc;
    private Integer postage;

    private Integer status;


    private String statusDesc;

    private String paymentTime;

    private String sendTime;

    private String endTime;

    private String closeTime;

    private String createTime;

    //订单的明细
    private List<OrderItemVo> orderItemVoList;

    private String imageHost;

    private String receiverName;



}
