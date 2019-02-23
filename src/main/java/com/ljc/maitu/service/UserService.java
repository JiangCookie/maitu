package com.ljc.maitu.service;


import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.pojo.User;

/**
 * @author JYH
 * 2019/1/7 15:01
 */
public interface UserService {

    /**
     * @Description: 判断用户名是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * @Description: 判断Email是否使用
     */
    boolean queryEmailIsExist(String email);
    /**
     * @Description: 用户登录，根据用户名和密码查询用户
     */
    User queryUserForLogin(String username, String password);

    /**
     * @Description: 保存用户(用户注册)
     */
    int saveUser(User user);

    /**
     * @Description: 找回密码的问题
     */
    String selectQuestion(String username);

    /**
     * @Description: 核对找回密码的问题的答案
     */
    User checkAnswer(String username,String question,String answer);

    /**
     * @Description: 重置密码，根据用户名查找用户
     */
    int updatePasswordByUsername(String username,String password);

    /**
     * @Description: 重置密码，登录状态
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * @Description: 更新用户信息
     */
    ServerResponse<User> updateInformation(User user);

    /**
     * @Description: 获取用户信息
     */
    ServerResponse<User> getInformation(Integer userId);

    /**
     * @Description: 检验是否是管理员
     */
    ServerResponse checkAdminRole(User user);

    /**
     * @Description: 验证手机号是否被注册
     */
    ServerResponse validatePhone(String phone);

}
