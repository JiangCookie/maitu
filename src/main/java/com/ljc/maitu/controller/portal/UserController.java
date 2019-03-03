package com.ljc.maitu.controller.portal;

import com.ljc.maitu.common.Const;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.common.utils.*;
import com.ljc.maitu.controller.BasicController;
import com.ljc.maitu.pojo.User;
import com.ljc.maitu.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author JYH
 * 2019/1/7 12:01
 */
@RestController
@RequestMapping("/user/")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    @Autowired
    public RedisOperator redis;

    @PostMapping("regist")
    public ServerResponse<User> regist(User user) throws Exception {

        //1.判断用户名和密码不为空
        if (StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())) {
            return ServerResponse.createByErrorMessage("用户名和密码不能为空");
        }

        // 2. 判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        if(usernameIsExist){
            return ServerResponse.createByErrorMessage("用户名已经存在，请换一个再试");
        }

        // 3. 判断Email是否存在
        boolean emailIsExist = userService.queryEmailIsExist(user.getEmail());
        if(emailIsExist){
            return ServerResponse.createByErrorMessage("Email已经存在，请换一个再试");
        }

        // 3. 保存用户，注册信息
        user.setCreateTime(DateUtils.parse(DateUtils.getNow()));
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        int result = userService.saveUser(user);
        if(result == 0){
            return ServerResponse.createByErrorMessage("用户注册失败");
        }
        return ServerResponse.createBySuccessMessage("用户注册成功");
    }


    /**
     * @Description: 用户登录
     */
    @PostMapping("login")
    public ServerResponse<User> login(User user, HttpServletResponse httpServletResponse, HttpSession session) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

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
        User userResult = userService.queryUserForLogin(username,MD5Utils.getMD5Str(password));
        if (userResult != null) {
            userResult.setPassword("");

            String jwt = JwtUtil.generateToken(userResult.getUsername());


            //根据jwt与用户信息保存到redis缓存中
            redis.set(jwt, JsonUtils.objectToJson(userResult), Const.TIMEOUT);

            return ServerResponse.createBySuccess("登录成功",userResult);
        } else {
            return ServerResponse.createByErrorMessage("密码不正确, 请重试...");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        CookieUtil.delLoginToken(httpServletRequest,httpServletResponse);
        redis.del(loginToken);

        return "注销成功";
    }

    @PostMapping("get_user_info")
    public ServerResponse<User> getUserInfo(HttpServletRequest request){
        User user = getUser(request);
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }


    @PostMapping("forget_get_question")
    public ServerResponse<String> forgetGetQuestion(String username){
        //判断用户名是否存在
        boolean result = userService.queryUsernameIsExist(username);
        if(!result){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userService.selectQuestion(username);
        if(question == null){
            return ServerResponse.createByErrorMessage("找回密码的问题是空的");
        }
        return ServerResponse.createBySuccess(question);
    }

    @PostMapping( "forget_check_answer")
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        User userResult = userService.checkAnswer(username,question,answer);
        if(userResult != null){
            //说明问题及问题答案是这个用户的,并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            redis.set(Const.TOKEN_PREFIX + username,forgetToken,60*5);
            return ServerResponse.createBySuccess(forgetToken);
        }

        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    /**
     * @Description: 重置密码，忘记密码中的重置密码
     */
    @PostMapping( "forget_reset_password")
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){

        //判断token是否存在
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误,token需要传递");
        }

        //判断用户名是否存在
        boolean result = userService.queryUsernameIsExist(username);
        if(!result){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //判断token是否有效
        String token = redis.get(Const.TOKEN_PREFIX+username);
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }

        //判断token是否正确
        if(StringUtils.equals(forgetToken,token)){
            String md5Password  = null;
            try {
                md5Password = MD5Utils.getMD5Str(passwordNew);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int rowCount = userService.updatePasswordByUsername(username,md5Password);

            if(rowCount > 0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    /**
     * @Description: 重置密码，登录状态
     */
    @PostMapping("reset_password")
    public ServerResponse<String> resetPassword(HttpServletRequest request,String passwordOld,String passwordNew){
        //检查是否登录
        String redisValue = isLogin(request);
        if(redisValue == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        return userService.resetPassword(passwordOld,passwordNew,JsonUtils.jsonToPojo(redisValue,User.class));
    }

    /**
     * @Description: 更新用户信息
     */
    @PostMapping("update_information")
    public ServerResponse<User> update_information(HttpServletRequest request,User user){

        User oldUser = getUser(request);
        user.setId(oldUser.getId());
        user.setUsername(oldUser.getUsername());
        ServerResponse<User> response = userService.updateInformation(user);
        if(response.isSuccess()){
            //更新redis缓存用户信息
            String loginToken = CookieUtil.readLoginToken(request);
            redis.del(loginToken);
            redis.set(loginToken,JsonUtils.objectToJson(response.getData()),Const.TIMEOUT);
        }
        return response;
    }

    @PostMapping("get_information")
    public ServerResponse<User> get_information(HttpServletRequest request){
        User user = getUser(request);
        return userService.getInformation(user.getId());
    }




    /**
     * @Description: 验证手机号
     */
    @PostMapping("validatePhone")
    public  ServerResponse validatePhone(String phone){
        return userService.validatePhone(phone);
    }
}
