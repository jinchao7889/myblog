package com.kim.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kim.common.lang.Result;
import com.kim.entity.Blogs;
import com.kim.service.BlogsService;
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
@RequestMapping("/blogs")
public class BlogsController {

    @Autowired
    BlogsService blogsService;

    //博客列表页面
    @GetMapping("/blogslist")
    public Result BlogsList(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,10);
        IPage pageDate = blogsService.page(page, new QueryWrapper<Blogs>().orderByDesc("create_date"));
        return Result.succ(pageDate);
    }

    //博客详情页面
    @GetMapping("/{id}")
    public Result BlogDetail(@PathVariable(name = "id") Long id){
        Blogs blogs = blogsService.getById(id);
        Assert.notNull(blogs,"该博客已被删除");
        return Result.succ(blogs);
    }

    //编辑/新建博客
    @PostMapping("/edit")
    public Result BlogEditOrUpdate(@Validated @RequestBody Blogs blogs){
        Blogs temp_blog = null;
        if(blogs.getBlogId() != null){
            temp_blog = blogsService.getById(blogs.getBlogId());
        }else {
            temp_blog = new Blogs();
        }
        BeanUtil.copyProperties(blogs,temp_blog,"blog_id");
        blogsService.saveOrUpdate(temp_blog);
        return Result.succ(null);
    }

    //逻辑删除博客
    @GetMapping("/logicdel/{id}")
    public Result LogicDelBlog(@PathVariable("id") Long id ){
        Blogs blogs = blogsService.getById(id);
        Blogs del_blog = blogs.setLogicDel(false);
        boolean res = blogsService.updateById(del_blog);
        return Result.succ(res);
    }

    //数据库删除博客
    @GetMapping("/del/{id}")
    public Result DelBlog(@PathVariable("id") Long id){
        boolean res = blogsService.removeById(id);
        return Result.succ(res);
    }

    //恢复博客
    @GetMapping("/recover/{id}")
    public Result RecoverBlog(@PathVariable("id") Long id ){
        /*UpdateWrapper<Blogs> blogs = new UpdateWrapper<>();
        blogs.eq("blog_id",id).set("logic_del",false);*/
        Blogs blogs = blogsService.getById(id);
        Blogs rec_blog = blogs.setLogicDel(true);
        boolean res = blogsService.updateById(rec_blog);
        return Result.succ(res);
    }

    @Value("${upload.pathBlogs}")
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

      /* @Value("${upload.path}")
    private String baseFolderPath;

    @PostMapping("/upload")
    public Result upload(MultipartFile file,HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String filePath = sdf.format(new Date());

        File baseFolder = new File(baseFolderPath + filePath);
        if (!baseFolder.exists()) {
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

        System.out.println("获取的文件名字："+file.getOriginalFilename());

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
*/
}
