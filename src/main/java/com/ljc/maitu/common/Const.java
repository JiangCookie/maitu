package com.ljc.maitu.common;

/**
 * @author JYH
 * 2019/1/7 16:17
 */
public class Const {
    /**
     * @Description: 登录成功token
     */
    public static final String USER_REDIS_SESSION = "user-redis-session";

    /**
     * @Description: 找回密码提示成功token
     */
    public static final String TOKEN_PREFIX = "token_";

    /**
     * @Description: 过期时间
     */
    public static final Integer TIMEOUT = 60*60*24*30;

    public interface Role{
        //普通用户
        int ROLE_CUSTOMER = 0;
        //管理员
        int ROLE_ADMIN = 1;
    }
}
