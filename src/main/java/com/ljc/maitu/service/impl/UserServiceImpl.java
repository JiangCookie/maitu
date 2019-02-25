package com.ljc.maitu.service.impl;

import com.ljc.maitu.common.Const;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.common.utils.DateUtils;
import com.ljc.maitu.common.utils.MD5Utils;
import com.ljc.maitu.mapper.UserMapper;
import com.ljc.maitu.pojo.User;
import com.ljc.maitu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author JYH
 * 2019/1/7 15:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {
        User user = new User();
        user.setUsername(username);
        User result =  userMapper.selectOne(user);
        return result == null ? false : true;
    }

    @Override
    public boolean queryEmailIsExist(String email) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("email", 1);
        User result =  userMapper.selectOneByExample(example);
        return result == null ? false : true;
    }

    @Override
    public User queryUserForLogin(String username, String password) {
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        User result = userMapper.selectOneByExample(userExample);

        return result;
    }

    @Override
    public int saveUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public String selectQuestion(String username) {
        User user = new User();
        user.setUsername(username);
        String question = userMapper.selectQuestionByUsername(username);
        return question;
    }

    @Override
    public User checkAnswer(String username, String question, String answer) {
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("question", question);
        criteria.andEqualTo("answer", answer);

        User user = userMapper.selectOneByExample(userExample);


        return user;
    }

    @Override
    public int updatePasswordByUsername(String username, String password) {
        //条件
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);

        //更新值
        User update = new User();
        update.setPassword(password);
        int result = userMapper.updateByExampleSelective(update,userExample);

        return result;
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", user.getId());
        try {
            criteria.andEqualTo("password", MD5Utils.getMD5Str(passwordOld));
        } catch (Exception e) {
            e.printStackTrace();
        }
        User selectResult = userMapper.selectOneByExample(example);
        if(selectResult == null){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        try {
            User  user1 = new User();
            user1.setId(user.getId());
            user1.setPassword(MD5Utils.getMD5Str(passwordNew));
            int updateCount = userMapper.updateByPrimaryKeySelective(user1);
            if(updateCount > 0){
                return ServerResponse.createBySuccessMessage("密码更新成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        //username是不能被更新的
        //email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("email", user.getEmail());
        criteria.andNotEqualTo("id", user.getId());
        User selectResult = userMapper.selectOneByExample(example);
        if(selectResult != null){
            return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setUpdateTime(DateUtils.parse(DateUtils.getNow()));
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse checkAdminRole(User user) {

        if(user != null && user.getRole() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse validatePhone(String phone) {
        Example example = new Example(User.class);
        Example.Criteria criteria =  example.createCriteria();
        criteria.andEqualTo("phone", phone);
        User user = userMapper.selectOneByExample(example);
        if(user == null){
            return ServerResponse.createBySuccessMessage("手机号可以使用");
        }
        return ServerResponse.createByErrorMessage("手机号不可使用");
    }


}
