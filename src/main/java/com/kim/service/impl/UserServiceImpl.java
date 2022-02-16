package com.kim.service.impl;

import com.kim.entity.User;
import com.kim.mapper.UserMapper;
import com.kim.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kim_japan
 * @since 2021-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
