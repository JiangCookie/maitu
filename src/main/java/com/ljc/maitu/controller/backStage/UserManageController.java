package com.ljc.maitu.controller.backStage;

import com.ljc.maitu.common.Const;
import com.ljc.maitu.common.ResponseCode;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.common.utils.*;
import com.ljc.maitu.pojo.User;
import com.ljc.maitu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletResponse;


/**
 * @author JYH
 * 2019/1/8 13:22
 */
@RestController
@RequestMapping("/manage/user/")
public class UserManageController {


    @Autowired
    private UserService userService;

    @Autowired
    public RedisOperator redis;

    @PostMapping("login")
    public ServerResponse login(String username, String password, HttpServletResponse httpServletResponse) throws Exception {

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
            userResult.setPassword("");
            String jwt = JwtUtil.generateToken(userResult.getUsername());


            //根据jwt与用户信息保存到redis缓存中
            redis.set(jwt, JsonUtils.objectToJson(userResult), Const.TIMEOUT);

            if(userResult.getRole() == Const.Role.ROLE_ADMIN){


                    return ServerResponse.createByLoginCodeMessage(ResponseCode.ADMIN_LOGIN.getCode(),jwt);
            }else {
                return ServerResponse.createByLoginCodeMessage(ResponseCode.USER_LOGIN.getCode(),jwt);
            }

        } else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"密码不正确, 请重试...");
        }
    }
}
