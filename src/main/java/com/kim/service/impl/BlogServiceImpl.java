package com.kim.service.impl;

import com.kim.entity.Blog;
import com.kim.mapper.BlogMapper;
import com.kim.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
