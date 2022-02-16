package com.kim.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kim.common.lang.Result;
import com.kim.entity.Article;
import com.kim.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kim_japan
 * @since 2021-11-07
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    //获取文章列表
    @GetMapping("/articleslist")
    public Result getArticleList(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage, 10);
        IPage pageData = articleService.page(page, new QueryWrapper<Article>().orderByDesc("create_date"));
        return Result.succ(pageData);
    }


    //根据ID获取一篇文章
    @GetMapping("/{id}")
    public Result getOneArticle(@PathVariable(name="id") Long id){
        Article article = articleService.getById(id);
        Assert.notNull(article,"没有这篇文章");
        return Result.succ(article);
    }


    //编辑、新建文章
    @PostMapping("/edit")
    public Result articleEditOrUpdate(@Validated @RequestBody Article article){
        Article temp_article = null;
        if(article.getArticleId() != null){
            temp_article = articleService.getById(article.getArticleId());
        }else {
            temp_article = new Article();
        }
        BeanUtil.copyProperties(article,temp_article,"article_id");
        articleService.saveOrUpdate(temp_article);
        return Result.succ(null);
    }

    //数据库删除文章
    @GetMapping("/del/{id}")
    public Result delArticle(@PathVariable("id") Long id){
        boolean res = articleService.removeById(id);
        return Result.succ(res);
    }


    //逻辑删除文章
    @GetMapping("/logicdel/{id}")
    public Result LogicDelArticle(@PathVariable("id") Long id){
        Article article = articleService.getById(id);
        Article del_article = article.setLogicDel(false);
        boolean res = articleService.updateById(del_article);
        return Result.succ(res);
    }


    @Value("${upload.pathArticle}")
    private String baseFolderPath;

    @PostMapping("/pic/upload")
    public Result uploadBlogsPics(MultipartFile file, HttpServletRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String filePath = sdf.format(new Date());

        File baseFolder = new File(baseFolderPath + filePath);
        if (!baseFolder.exists()){
            baseFolder.mkdirs();
        }
        StringBuffer url = new StringBuffer();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append(baseFolderPath)
                .append(filePath);
        String imgName = UUID.randomUUID().toString().replace("_", "") + "_"+file.getOriginalFilename();
        try {
            File dest = new File(baseFolder, imgName);
            FileCopyUtils.copy(file.getBytes(), dest);
            url.append("/").append(imgName);

            JSONObject object = new JSONObject();
            object.put("url", url);

            return Result.succ(object);
        } catch (IOException e) {
            return Result.fail("文件上传错误");
        }

    }



}
