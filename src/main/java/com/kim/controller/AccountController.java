package com.kim.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kim.common.dto.LoginDto;
import com.kim.common.dto.ManagerLoginDto;
import com.kim.common.lang.Result;
import com.kim.entity.Manager;
import com.kim.entity.Users;
import com.kim.service.ManagerService;
import com.kim.service.UsersService;
import com.kim.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    UsersService usersService;

    @Autowired
    ManagerService managerService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        Users users = usersService.getOne(new QueryWrapper<Users>().eq("user_name", loginDto.getUserName()));
        Assert.notNull(users,"该用户不存在");

        if (!users.getUserPassword().equals(SecureUtil.md5(loginDto.getUserPassword()))){
            //如果密码错误，则抛出异常
            return Result.fail("密码不正确");
        }

        //如果账号被封号，则进入
        if (users.getStatus() == -1){
            return Result.fail("该账户已被冻结");
        }


        String jwt = jwtUtils.generateToken(users.getId());

        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.succ(MapUtil.builder()
                .put("id",users.getId())
                .put("userName",users.getUserName())
                .put("userAvatar",users.getUserAvatar())
                .put("userMail",users.getUserMail())
                .put("userRole",users.getUserRole())
                .put("status",users.getStatus())
                .put("createDate",users.getCreateDate())
                .map()
        );
    }


    //管理员登录
    @PostMapping("/managerlogin")
    public Result managerLogin(@Validated @RequestBody ManagerLoginDto managerLoginDto){

        Manager manager = managerService.getOne(new QueryWrapper<Manager>().eq("manager_name", managerLoginDto.getManagerName()));
        Assert.notNull(manager,"该管理员不存在");

        if (!manager.getManagerPassword().equals(SecureUtil.md5(managerLoginDto.getManagerPassword()))){
            //如果密码错误，则抛出异常
            return Result.fail("密码不正确");
        }
        return Result.succ(manager);
    }

    //退出
    //@RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
