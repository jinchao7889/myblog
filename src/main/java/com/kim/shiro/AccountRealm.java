package com.kim.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.kim.entity.Users;
import com.kim.service.UsersService;
import com.kim.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsersService usersService;

    @Override   //快捷键  ctrl+o
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;

        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        Users users = usersService.getById(Long.valueOf(userId));

        System.out.println("users.status="+users.getStatus());
        if (users == null){
            throw new UnknownAccountException("该账户不存在");
        }
        if (users.getStatus() == -1){
            throw new LockedAccountException("该账户已被锁定");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(users,profile);

        System.out.println("-------------------");

        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
