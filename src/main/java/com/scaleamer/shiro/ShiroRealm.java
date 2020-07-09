package com.scaleamer.shiro;

import com.scaleamer.dao.PermissionMapper;
import com.scaleamer.dao.RoleMapper;
import com.scaleamer.dao.UserMapper;
import com.scaleamer.domain.Permission;
import com.scaleamer.domain.Role;
import com.scaleamer.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    /**
     *  登录的验证实现方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken token2 = (UsernamePasswordToken) token;
//        String username = token2.getUsername();
        String username = (String) token.getPrincipal();
//        String password = String.valueOf(token2.getPassword());
        User user = userMapper.getUserByEmailAddr(username);
        if(user == null) {
            throw new UnknownAccountException("用户名或密码有误！");
        }


        /**
         * principals: 可以使用户名，或d登录用户的对象
         * hashedCredentials: 从数据库中获取的密码
         * credentialsSalt：密码加密的盐值
         * RealmName:  类名（ShiroRealm）
         */

        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return info; //框架完成验证
    }




    //shiro的权限信息配置
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        int uid = ((User) principals.getPrimaryPrincipal()).getUser_id();
        List<Role> roles = roleMapper.getByUserId(uid);
        Set<String> roleNames = new HashSet<>(roles.size());
        for (Role role : roles) {
            roleNames.add(role.getRole_name());
        }
        //此处把当前subject对应的所有角色信息交给shiro，调用hasRole的时候就根据这些role信息判断
        authorizationInfo.setRoles(roleNames);
        List<Permission> permissions = permissionMapper.getByUserId(uid);
        Set<String> permissionNames = new HashSet<>(permissions.size());
        for (Permission permission : permissions) {
            permissionNames.add(permission.getPermission_name());
        }
//        System.out.println(roles);
//        System.out.println(permissions);
        //此处把当前subject对应的权限信息交给shiro，当调用hasPermission的时候就会根据这些信息判断
        authorizationInfo.setStringPermissions(permissionNames);
        return authorizationInfo;
    }
}
