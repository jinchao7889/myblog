package com.kim.service.impl;

import com.kim.entity.Users;
import com.kim.mapper.UsersMapper;
import com.kim.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kim_japan
 * @since 2021-11-07
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
