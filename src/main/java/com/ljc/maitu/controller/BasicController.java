package com.ljc.maitu.controller;

import com.ljc.maitu.common.utils.CookieUtil;
import com.ljc.maitu.common.utils.JsonUtils;
import com.ljc.maitu.common.utils.RedisOperator;
import com.ljc.maitu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JYH
 * 2019/2/12 14:40
 */
@RestController
public class BasicController {

    @Autowired
    public RedisOperator redis;

    /**
     * @Description: 判断是否登录
     */
    public String isLogin(HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        String redisValue = null;
        if(loginToken != null){
            redisValue = redis.get(loginToken);
        }

        return redisValue;
    }

    /**
     * @Description: 获取用户信息
     */
    public User getUser(HttpServletRequest request){
        String token = request.getHeader("Authorization.js");
        User user = JsonUtils.jsonToPojo(redis.get(token),User.class);
        return user;
    }
}
