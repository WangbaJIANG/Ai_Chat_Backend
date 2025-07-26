package com.hclx.hclx_ai1.service.impl;

import com.hclx.hclx_ai1.entity.User;
import com.hclx.hclx_ai1.mapper.UserMapper;
import com.hclx.hclx_ai1.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;
   
}
