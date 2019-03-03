package com.ljc.maitu.service;

import com.github.pagehelper.PageInfo;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.pojo.vo.OrderVo;

import java.util.Map;

/**
 * @author JYH
 * @Description
 * @date 2019/3/3 20:28
 */
public interface OrderService {


    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String,String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

    /**
     * @Description 创建订单
     */
    ServerResponse createOrder(Integer userId);

    /**
     * @Description 取消订单
     */
    ServerResponse<String> cancel(Integer userId,Long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    //backend
    ServerResponse<PageInfo> manageList(int pageNum,int pageSize);
    ServerResponse<OrderVo> manageDetail(Long orderNo);
    ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);
    ServerResponse<String> manageSendGoods(Long orderNo);
}
