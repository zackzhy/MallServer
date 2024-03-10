package com.bobooi.mall.data.repository.concrete.customer;

import com.bobooi.mall.data.entity.customer.RolePermission;
import com.bobooi.mall.data.repository.DataRepository;

import java.util.List;

/**
 * @author bobo
 * @date 2021/4/9
 */

public interface RolePermissionRepository extends DataRepository<RolePermission, Integer> {
    /**
     * 通过角色id找到角色权限关系列表
     * @param roleId 角色id
     * @return 角色权限关系列表
     */
    List<RolePermission> findAllByRoleId(Integer roleId);
}
