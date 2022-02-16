package com.kim.controller;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kim.common.lang.Result;
import com.kim.entity.Blogs;
import com.kim.entity.Users;
import com.kim.service.UsersService;
import com.kim.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kim_japan
 * @since 2021-11-07
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Value("${upload.pathAvatar}")
    private String userAvatarBaseFolderPath;

    //@RequiresAuthentication
    @GetMapping("/{id}")
    public Result test(@PathVariable("id") Long id) {
        Users users = usersService.getById(id);
        return Result.succ(200, "查询用户成功！", users);
    }


    @PostMapping("/register")
    public Result register(@Validated @RequestBody Users users) {
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        int count = usersService.count(usersQueryWrapper.eq("user_name", users.getUserName()));
        if (count > 0) {
            return Result.fail("用户名已存在，请重试！");
        } else {
            String md5_password = SecureUtil.md5(users.getUserPassword());//对用户输入密码进行MD5加密
            Users md5_users = users.setUserPassword(md5_password);//将加密完的密码返回到User中得 得到新的USER数据
            boolean register_msg = usersService.save(md5_users);//进行注册
            return Result.succ(register_msg);
        }


    }


    @PostMapping("/avatar/upload")
    public Result UserAvatarUpload(MultipartFile file, HttpServletRequest request) {
        //获取当前登录用户的信息，取出唯一ID
        AccountProfile userInfo = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        System.out.println("users的信息：" + userInfo);

        //通过当前用户id，获取当前用户的所有信息
        Users users = usersService.getById(userInfo.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String filePath = sdf.format(new Date());

        File baseFolder = new File(userAvatarBaseFolderPath + filePath);
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
                .append(userAvatarBaseFolderPath)
                .append(filePath);

        System.out.println("获取的文件名称为：" + file.getOriginalFilename());
        String imgName = UUID.randomUUID().toString().replace("_", "") + "_" + file.getOriginalFilename();

        try {
            File dest = new File(baseFolder, imgName);
            FileCopyUtils.copy(file.getBytes(), dest);
            url.append("/").append(imgName);

            JSONObject object = new JSONObject();
            object.put("url", url);

            System.out.println("最终的url为：" + url.toString());
            Users updatedUsers = users.setUserAvatar(url.toString());
            boolean res = usersService.updateById(updatedUsers);

            return Result.succ(res);

        } catch (Exception e) {
            return Result.fail("文件上传错误");
        }
    }


    //获取所有用户
    @GetMapping("/userslist")
    public Result getAllUsers(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 10);
        IPage pageData = usersService.page(page, new QueryWrapper<Users>().orderByDesc("create_date"));
        return Result.succ(pageData);
    }

}
