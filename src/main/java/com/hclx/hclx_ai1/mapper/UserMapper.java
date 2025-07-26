package com.hclx.hclx_ai1.mapper;

import com.hclx.hclx_ai1.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author springBoot-Learning
 * @since 2024-12-10
 */
public interface UserMapper extends BaseMapper<User> {

int insertUser(User user);
List findAllUser();

}
