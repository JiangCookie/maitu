package com.ljc.maitu.service;

import com.github.pagehelper.PageInfo;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.pojo.Product;
import com.ljc.maitu.pojo.vo.ProductDetailVo;

/**
 * @author JYH
 * 2019/2/12 14:11
 */
public interface ProductService {

    /**
     * @Description: 更新或增加产品
     */
    ServerResponse saveOrUpdateProduct(Product product);

    /**
     * @Description: 更新产品状态
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * @Description: 产品详情
     */
    ServerResponse<ProductDetailVo> productDetail(Integer productId);

    /**
     * @Description: 产品列表
     */
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    /**
     * @Description: 产品搜索
     */
    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

}
