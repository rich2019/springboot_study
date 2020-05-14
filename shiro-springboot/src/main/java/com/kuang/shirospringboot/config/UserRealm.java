package com.kuang.shirospringboot.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuang.shirospringboot.mapper.ShiroMapper;
import com.kuang.shirospringboot.pojo.Shiro;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm extends AuthorizingRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    ShiroMapper shiroMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了授权");


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前的登陆对象
        Shiro user = (Shiro)SecurityUtils.getSubject().getPrincipal();
        //设置当前用户的权限
        info.addStringPermission(user.getPerms());


        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");

//        //用户名,密码数据库中取
//        String username = "root";
//        String password = "123";
//
//        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
//        if (!userToken.getUsername().equals(username)) {
//            return null;    //抛出异常 UnknownAccountException
//        }

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        QueryWrapper<Shiro> wrapper = new QueryWrapper<>();
        wrapper.eq("username",userToken.getUsername());
        Shiro user = shiroMapper.selectOne(wrapper);
        System.out.println(user);
        if (null == user){
            return null;
        }

        //密码认证,shiro做
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
