package com.ljc.maitu.controller.portal;

import com.ljc.maitu.common.Const;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.controller.BasicController;
import com.ljc.maitu.pojo.User;
import com.ljc.maitu.pojo.vo.CartVo;
import com.ljc.maitu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JYH
 * 2019/2/12 14:09
 */
@RestController
@RequestMapping("/cart/")
public class CartController extends BasicController {

    @Autowired
    private CartService cartService;



    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpServletRequest request){
        User user = getUser(request);
        return cartService.list(user.getId());
    }

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse<CartVo> add(HttpServletRequest request, Integer count, Integer productId){
        User user = getUser(request);
        return cartService.add(user.getId(),productId,count);
    }



    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse<CartVo> update(HttpServletRequest request, Integer count, Integer productId){
        User user = getUser(request);
        return cartService.update(user.getId(),productId,count);
    }

    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse<CartVo> deleteProduct(HttpServletRequest request, String productIds){
        User user = getUser(request);
        return cartService.deleteProduct(user.getId(),productIds);
    }


    /**
     * @Description 全选
     * @param request
     * @return
     */
    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> selectAll(HttpServletRequest request){
        User user = getUser(request);
        return cartService.selectOrUnSelect(user.getId(),null, Const.Cart.CHECKED);
    }

    /**
     * @Description 全反选
     * @param request
     * @return
     */
    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request){
        User user = getUser(request);
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
    }


    /**
     * @Description 单独选
     * @param request
     * @return
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<CartVo> select(HttpServletRequest request, Integer productId){
        User user = getUser(request);
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

    /**
     * @Description 单独反选
     * @param request
     * @return
     */
    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelect(HttpServletRequest request, Integer productId){
        User user = getUser(request);
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
    }


    /**
     * 查询当前用户的购物车里面的产品数量,如果一个产品有10个,那么数量就是10.
     * @param request
     * @return
     */
    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request){
        User user = getUser(request);
        return cartService.getCartProductCount(user.getId());
    }






}
