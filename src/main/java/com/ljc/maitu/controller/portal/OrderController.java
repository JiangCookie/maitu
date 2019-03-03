package com.ljc.maitu.controller.portal;


import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.controller.BasicController;
import com.ljc.maitu.pojo.User;
import com.ljc.maitu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


/**
 * @author JYH
 * @Description
 * @date 2019/3/3 20:27
 */
@RestController
@RequestMapping("/order/")
public class OrderController extends BasicController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("create.do")
    public ServerResponse create(HttpServletRequest request){
        User user = getUser(request);
        return orderService.createOrder(user.getId());
    }


    @RequestMapping("cancel.do")
    public ServerResponse cancel(HttpServletRequest request,  Long orderNo){
        User user = getUser(request);
        return orderService.cancel(user.getId(),orderNo);
    }


    @RequestMapping("get_order_cart_product.do")
    public ServerResponse getOrderCartProduct(HttpServletRequest request){
        User user = getUser(request);
        return orderService.getOrderCartProduct(user.getId());
    }



    @RequestMapping("detail.do")
    public ServerResponse detail(HttpServletRequest request,Long orderNo){
        User user = getUser(request);
        return orderService.getOrderDetail(user.getId(),orderNo);
    }

    @RequestMapping("list.do")
    public ServerResponse list(HttpServletRequest request,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = getUser(request);
        return orderService.getOrderList(user.getId(),pageNum,pageSize);
    }
}
