package com.kim.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kim.common.lang.Result;
import com.kim.entity.Blog;
import com.kim.service.BlogService;
import com.kim.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kim_japan
 * @since 2021-08-29
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    //博客列表页面
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }

    //博客详情页面
    @GetMapping("/{id}")
    public Result list(@PathVariable(name = "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客已被删除");
        return Result.succ(blog);
    }

    //博客编辑/更新页面
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result list(@Validated @RequestBody Blog blog){
        Blog temp = null;//设置一个临时变量来存放查询到的博客数据
        if (blog.getId() != null){
            temp = blogService.getById(blog.getId());//将查询到的博客数据存放到临时变量中
            //只能编辑自己的文章，所以要判断当前用户的信息是否是博客作者的信息
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"没有权限编辑");
        }else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);


        return Result.succ(null);
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
