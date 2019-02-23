package com.ljc.maitu.service;

import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.pojo.Category;

import java.util.List;

/**
 * @author JYH
 * 2019/2/11 15:12
 */
public interface CategoryService {

    /**
     * @Description: 增加分类
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * @Description: 更新分类
     */
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    /**
     * @Description: 查询当前的子节点（不递归）
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * @Description: 查询本节点ID及孩子节点ID（递归）
     */
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
