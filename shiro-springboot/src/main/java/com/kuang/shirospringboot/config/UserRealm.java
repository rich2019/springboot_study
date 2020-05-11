package com.kuang.shirospringboot.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

//自定义的UserRealm extends AuthorizingRealm
public class UserRealm extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了授权");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");

        //用户名,密码数据库中取
        String username = "root";
        String password = "123";

        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        if (!userToken.getUsername().equals(username)){
            return null;    //抛出异常 UnknownAccountException
        }

        //密码认证,shiro做
        return new SimpleAuthenticationInfo("",password,"");
    }
}
