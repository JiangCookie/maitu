package com.ljc.maitu.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ljc.maitu.common.Const;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.common.utils.BigDecimalUtil;
import com.ljc.maitu.common.utils.DateUtils;
import com.ljc.maitu.mapper.CartMapper;
import com.ljc.maitu.mapper.OrderItemMapper;
import com.ljc.maitu.mapper.OrderMapper;
import com.ljc.maitu.mapper.ProductMapper;
import com.ljc.maitu.pojo.Cart;
import com.ljc.maitu.pojo.Order;
import com.ljc.maitu.pojo.OrderItem;
import com.ljc.maitu.pojo.Product;
import com.ljc.maitu.pojo.vo.OrderItemVo;
import com.ljc.maitu.pojo.vo.OrderProductVo;
import com.ljc.maitu.pojo.vo.OrderVo;
import com.ljc.maitu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author JYH
 * @Description
 * @date 2019/3/3 20:29
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public ServerResponse pay(Long orderNo, Integer userId, String path) {

        return null;
    }

    @Override
    public ServerResponse aliCallback(Map<String, String> params) {
        return null;
    }

    @Override
    public ServerResponse queryOrderPayStatus(Integer userId, Long orderNo) {
        return null;
    }


    /**
     * 创建订单
     * @param userId
     * @return
     */
    @Override
    public ServerResponse createOrder(Integer userId) {

        //从购物车获取数据
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("checked",1);
        List<Cart> cartList =cartMapper.selectByExample(example);

        //计算这个订单的总价
        ServerResponse serverResponse = this.getCartOrderItem(userId,cartList);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>)serverResponse.getData();
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);

        //生成订单
        Order order = this.assembleOrder(userId,payment);
        if(order == null){
            return ServerResponse.createByErrorMessage("生成订单错误");
        }
        if(CollectionUtils.isEmpty(orderItemList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        for(OrderItem orderItem : orderItemList){
            orderItem.setOrderNo(order.getOrderNo());
        }

        //mybatis 批量插入
        orderItemMapper.batchInsert(orderItemList);

        //生成成功,我们要减少我们产品的库存
        this.reduceProductStock(orderItemList);
        //清空一下购物车
        this.cleanCart(cartList);

        //返回给前端数据
        OrderVo orderVo = assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    @Override
    public ServerResponse<String> cancel(Integer userId,Long orderNo){

        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("orderNo",orderNo);
        Order order  = orderMapper.selectOneByExample(example);
        if(order == null){
            return ServerResponse.createByErrorMessage("该用户此订单不存在");
        }
        if(order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()){
            return ServerResponse.createByErrorMessage("已付款,无法取消订单");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());

        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if(row > 0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse getOrderCartProduct(Integer userId) {
        OrderProductVo orderProductVo = new OrderProductVo();
        //从购物车中获取数据
        //从购物车获取数据
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("checked",1);
        List<Cart> cartList =cartMapper.selectByExample(example);

        ServerResponse serverResponse =  this.getCartOrderItem(userId,cartList);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<OrderItem> orderItemList =( List<OrderItem> ) serverResponse.getData();

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        BigDecimal payment = new BigDecimal("0");
        for(OrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setImageHost("");
        return ServerResponse.createBySuccess(orderProductVo);
    }

    @Override
    public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) {

        return null;
    }

    @Override
    public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> manageList(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<OrderVo> manageDetail(Long orderNo) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<String> manageSendGoods(Long orderNo) {
        return null;
    }


    /**
     * 获取订单字表(私有方法)
     * @param userId
     * @param cartList
     * @return
     */
    private ServerResponse getCartOrderItem(Integer userId,List<Cart> cartList){
        List<OrderItem> orderItemList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(cartList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }

        //校验购物车的数据,包括产品的状态和数量
        for(Cart cartItem : cartList){
            OrderItem orderItem = new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            if(Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()){
                return ServerResponse.createByErrorMessage("产品"+product.getName()+"不是在线售卖状态");
            }

            //校验库存
            if(cartItem.getQuantity() > product.getStock()){
                return ServerResponse.createByErrorMessage("产品"+product.getName()+"库存不足");
            }

            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartItem.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }

    /**
     * 计算订单总价格(私有方法)
     * @param orderItemList
     * @return
     */
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList){
        BigDecimal payment = new BigDecimal("0");
        for(OrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    /**
     * 生成订单(私有方法)
     * @param userId
     * @param payment
     * @return
     */
    private Order assembleOrder(Integer userId,BigDecimal payment){
        Order order = new Order();
        long orderNo = this.generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        //发货时间等等
        //付款时间等等
        int rowCount = orderMapper.insert(order);
        if(rowCount > 0){
            return order;
        }
        return null;
    }

    /**
     * 生成订单号(私有方法)
     * @return
     */
    private long generateOrderNo(){
        long currentTime =System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }

    /**
     * 减少库存
     * @param orderItemList
     */
    private void reduceProductStock(List<OrderItem> orderItemList){
        for(OrderItem orderItem : orderItemList){
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    /**
     * 清除购物车
     * @param cartList
     */
    private void cleanCart(List<Cart> cartList){
        for(Cart cart : cartList){
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    /**
     * 返回前台的订单的信息封装类
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(Order order,List<OrderItem> orderItemList){
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());

        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());


        orderVo.setPaymentTime(DateUtils.format(order.getPaymentTime()));
        orderVo.setSendTime(DateUtils.format(order.getSendTime()));
        orderVo.setEndTime(DateUtils.format(order.getEndTime()));
        orderVo.setCreateTime(DateUtils.format(order.getCreateTime()));
        orderVo.setCloseTime(DateUtils.format(order.getCloseTime()));

        //todo
        orderVo.setImageHost("");


        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        for(OrderItem orderItem : orderItemList){
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    /**
     * 返回前端订单子类的信息封装类
     * @param orderItem
     * @return
     */
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateUtils.format(orderItem.getCreateTime()));
        return orderItemVo;
    }
}
