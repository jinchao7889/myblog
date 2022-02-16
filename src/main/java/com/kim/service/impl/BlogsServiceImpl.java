package com.kim.service.impl;

import com.kim.entity.Blogs;
import com.kim.mapper.BlogsMapper;
import com.kim.service.BlogsService;
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
public class BlogsServiceImpl extends ServiceImpl<BlogsMapper, Blogs> implements BlogsService {

}
