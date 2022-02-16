package com.kim.controller;


import cn.hutool.crypto.SecureUtil;
import com.kim.common.lang.Result;
import com.kim.entity.User;
import com.kim.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kim_japan
 * @since 2021-08-29
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //@RequiresAuthentication
    @GetMapping("/index")
    public Result index(){
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        String md5_password = SecureUtil.md5(user.getPassword());//对用户输入的密码进行MD5加密
        User md5_user = user.setPassword(md5_password);//将加密完的密码返回到user中，得到新的user数据
        boolean save_msg = userService.save(md5_user);//进行注册
        return Result.succ(save_msg);
    }


}
