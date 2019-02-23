package com.ljc.maitu.controller.portal;


import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.controller.BasicController;
import com.ljc.maitu.pojo.Product;
import com.ljc.maitu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author JYH
 * 2019/2/12 14:09
 */

@RestController
@RequestMapping("/product/")
public class ProductController extends BasicController {

    @Autowired
    private ProductService productService;


    @RequestMapping("save")
    public ServerResponse save(HttpServletRequest request, Product product){
        //检查是否登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return productService.saveOrUpdateProduct(product);
    }

    @RequestMapping("set_sale_status")
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId,Integer status){
        //检查是否登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return productService.setSaleStatus(productId,status);
    }

    @RequestMapping("detail")
    public ServerResponse getDetail(HttpServletRequest request,Integer productId){
        //检查是否登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return productService.productDetail(productId);
    }

    @RequestMapping("list")
    public ServerResponse getList(HttpServletRequest request,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize ){
        //检查登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        return productService.getProductList(pageNum,pageSize);

    }

    @RequestMapping("search.do")
    public ServerResponse productSearch(HttpServletRequest request,String productName,Integer productId,
                                        @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //检查登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        return productService.searchProduct(productName,productId,pageNum,pageSize);
    }
//    @RequestMapping("detail.do")
//    @ResponseBody
//    public ServerResponse<ProductDetailVo> detail(Integer productId){
//        return iProductService.getProductDetail(productId);
//    }
//
//    @RequestMapping("list.do")
//    @ResponseBody
//    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
//                                         @RequestParam(value = "categoryId",required = false)Integer categoryId,
//                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
//                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
//                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
//        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
//    }





}
