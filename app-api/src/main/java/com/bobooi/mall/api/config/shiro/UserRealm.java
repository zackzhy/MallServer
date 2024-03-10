package com.bobooi.mall.api.config.shiro;

import com.bobooi.mall.data.entity.customer.CsmLogin;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Service;
import com.bobooi.mall.api.config.jwt.JwtToken;
import com.bobooi.mall.common.utils.misc.JwtUtil;
import com.bobooi.mall.data.entity.customer.RolePermission;
import com.bobooi.mall.data.repository.concrete.customer.RolePermissionRepository;
import com.bobooi.mall.data.repository.concrete.customer.CsmLoginRepository;
import com.bobooi.mall.data.service.concrete.PermissionService;
import com.bobooi.mall.data.service.concrete.RoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bobo
 * @date 2021/4/7
 */

@Service
public class UserRealm extends AuthorizingRealm {
    @Resource
    CsmLoginRepository csmLoginRepository;
    @Resource
    RolePermissionRepository rolePermissionRepository;
    @Resource
    RoleService roleService;
    @Resource
    PermissionService permissionService;


    /**
     * 每个Realm调用前默认调用supports方法检查token是否是自己支持的类型
     * 一般默认检查token是否为UsernamePasswordToken类型
     * 而自定义token为jwt需要重写该方法
     * @param authenticationToken 传入的token
     * @return token是否是支持类型
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = JwtUtil.getClaim(principalCollection.toString(), "account");
        CsmLogin currentCsmLogin = csmLoginRepository.findCsmLoginByLoginName(account);
        if(currentCsmLogin != null){
            roleService.getOne(currentCsmLogin.getRoleId()).ifPresent(role ->{
                simpleAuthorizationInfo.addRole(role.getRoleName());
                List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleId(role.getRoleId());
                for (RolePermission rolePermission: rolePermissions) {
                    permissionService.getOne(rolePermission.getPermissionId()).ifPresent(permission -> {
                        simpleAuthorizationInfo.addStringPermission(permission.getCode());
                    });
                }
            });
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, JwtUtil.ACCOUNT);
        // 帐号为空
        if (StringUtil.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        CsmLogin csmLogin = csmLoginRepository.findCsmLoginByLoginName(account);
        if (csmLogin == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        JwtUtil.verify(token);
        return new SimpleAuthenticationInfo(token, token, "userRealm");
    }
}
