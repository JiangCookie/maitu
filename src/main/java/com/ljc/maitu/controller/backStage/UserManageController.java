package com.ljc.maitu.controller.backStage;

import com.ljc.maitu.common.Const;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.common.utils.CookieUtil;
import com.ljc.maitu.common.utils.JsonUtils;
import com.ljc.maitu.common.utils.MD5Utils;
import com.ljc.maitu.common.utils.RedisOperator;
import com.ljc.maitu.pojo.User;
import com.ljc.maitu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author JYH
 * 2019/1/8 13:22
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {


    @Autowired
    private UserService userService;

    @Autowired
    public RedisOperator redis;

    @PostMapping("login")
    public ServerResponse<User> login(String username, String password, HttpServletResponse httpServletResponse, HttpSession session) throws Exception {

        // 1. 判断用户名和密码必须不为空
        if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
            return ServerResponse.createByErrorMessage("用户名或密码不能为空");
        }

        // 2. 判断用户名是否存在
        boolean result = userService.queryUsernameIsExist(username);
        if(!result){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }


        // 3. 判断密码是否存在
        User userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult != null) {
            if(userResult.getRole() == Const.Role.ROLE_ADMIN){
                userResult.setPassword("");

                //保存登录JsessionId，返回到一级域名
                CookieUtil.writeLoginToken(httpServletResponse,session.getId());
                //根据JsessionId保存到redis缓存中
                redis.set(session.getId(), JsonUtils.objectToJson(userResult), Const.TIMEOUT);

                return ServerResponse.createBySuccess("登录成功",userResult);
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登陆");
            }

        } else {
            return ServerResponse.createByErrorMessage("密码不正确, 请重试...");
        }
    }
}
