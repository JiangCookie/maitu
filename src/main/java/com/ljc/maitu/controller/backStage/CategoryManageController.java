package com.ljc.maitu.controller.backStage;

import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.controller.BasicController;
import com.ljc.maitu.service.CategoryService;
import com.ljc.maitu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author JYH
 * 2019/2/11 14:44
 */
@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController extends BasicController {



    @Autowired
    public CategoryService categoryService;

    @Autowired
    private UserService userService;

    @RequestMapping("add_category")
    public ServerResponse addCategory(String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
            return categoryService.addCategory(categoryName,parentId);
    }

    @RequestMapping("set_category_name")
    public ServerResponse setCategoryName(Integer categoryId, String categoryName){
            //更新categoryName
            return categoryService.updateCategoryName(categoryId,categoryName);
    }

    @RequestMapping("get_category")
    public ServerResponse getChildrenParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
            return categoryService.getChildrenParallelCategory(categoryId);

    }

    @RequestMapping("get_deep_category")
    public ServerResponse getCategoryAndDeepChildrenCategory(@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
            //查询当前节点的id和递归子节点的id
//            0->10000->100000
            return categoryService.selectCategoryAndChildrenById(categoryId);

    }
}
