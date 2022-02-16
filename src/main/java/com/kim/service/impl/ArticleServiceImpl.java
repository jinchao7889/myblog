package com.kim.service.impl;

import com.kim.entity.Article;
import com.kim.mapper.ArticleMapper;
import com.kim.service.ArticleService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
