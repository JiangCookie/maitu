package com.ljc.maitu.mapper;

import com.ljc.maitu.pojo.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    String selectQuestionByUsername(String username);
}