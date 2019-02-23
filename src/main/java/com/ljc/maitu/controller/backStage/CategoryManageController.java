package com.ljc.maitu.controller.backStage;

import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.common.utils.JsonUtils;
import com.ljc.maitu.controller.BasicController;
import com.ljc.maitu.pojo.User;
import com.ljc.maitu.service.CategoryService;
import com.ljc.maitu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


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
    public ServerResponse addCategory(HttpServletRequest request,String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        //检查是否登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        //校验一下是否是管理员
        if(userService.checkAdminRole(JsonUtils.jsonToPojo(redisValue, User.class)).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            return categoryService.addCategory(categoryName,parentId);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("set_category_name")
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName){
        //检查是否登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        //更新操作
        if(userService.checkAdminRole(JsonUtils.jsonToPojo(redisValue, User.class)).isSuccess()){
            //更新categoryName
            return categoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("get_category")
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        //检查是否登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        if(userService.checkAdminRole(JsonUtils.jsonToPojo(redisValue, User.class)).isSuccess()){
            //查询子节点的category信息,并且不递归,保持平级
            return categoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category")
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest request,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        //检查是否登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        if(userService.checkAdminRole(JsonUtils.jsonToPojo(redisValue, User.class)).isSuccess()){
            //查询当前节点的id和递归子节点的id
//            0->10000->100000
            return categoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
}
