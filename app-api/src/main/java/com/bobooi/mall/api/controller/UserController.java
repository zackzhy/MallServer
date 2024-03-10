package com.bobooi.mall.api.controller;

import com.bobooi.mall.api.module.vo.UserDetailVO;
import com.bobooi.mall.api.module.vo.UserVO;
import com.bobooi.mall.data.bo.PageParam;
import com.bobooi.mall.data.entity.customer.CsmAddr;
import com.bobooi.mall.data.entity.customer.CsmLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.common.utils.misc.Constant;
import com.bobooi.mall.common.utils.misc.JwtUtil;
import com.bobooi.mall.data.config.redis.RedisUtil;
import com.bobooi.mall.data.service.concrete.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author bobo
 * @date 2021/3/31
 */
@CrossOrigin("*")
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    /**
     * 获取当前用户积分
     *
     * @return 用户积分
     */
    @ApiOperation("获取当前用户积分")
    @GetMapping("/point")
    public ApplicationResponse<Integer> getUserPoint() {
        return ApplicationResponse.succeed(userService.getUserPoint());
    }

    @ApiOperation("管理员获取用户地址列表【分页！！！！！】")
    @GetMapping("/addr")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    public ApplicationResponse<List<CsmAddr>> getUserAddr(PageParam pageParam) {
        return ApplicationResponse.succeed(userService.getUserAddrList(pageParam));
    }

    @ApiOperation("管理员获取用户地址总数【！！！！！总数】")
    @GetMapping("/addr/sum")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    public ApplicationResponse<Long> getUserAddrSum() {
        return ApplicationResponse.succeed(userService.getUserAddrSum());
    }

    @ApiOperation("管理员获取用户详细信息列表【分页！！！！！】")
    @GetMapping
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    public ApplicationResponse<List<UserDetailVO>> getAll(PageParam pageParam) {
        return ApplicationResponse.succeed(userService.getDetailUserList(pageParam).stream()
                .map(UserDetailVO::fromUserDetailBO)
                .collect(Collectors.toList()));
    }

    @ApiOperation("管理员获取用户详细信息总数【！！！！！总数】")
    @GetMapping("/sum")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    public ApplicationResponse<Long> getAllSum() {
        return ApplicationResponse.succeed(userService.getDetailUserSum());
    }


    @ApiOperation("获取登录信息")
    @GetMapping("/info")
    @RequiresAuthentication
    public ApplicationResponse<UserVO> info() {
        CsmLogin csmLogin = userService.info();
        // 用户是否存在
        if (csmLogin == null) {
            return ApplicationResponse.fail(SystemCodeEnum.NEED_LOGIN, "未登录");
        }
        // 获取当前登录用户
        return ApplicationResponse.succeed("您已经登录了", UserVO.fromUser(csmLogin));
    }

    /**
     * 用户登录
     *
     * @return 登录结果
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ApplicationResponse<Void> login(String account, String password, HttpServletResponse httpServletResponse) throws Exception {
        if (!userService.match(account, password)) {
            return ApplicationResponse.fail(SystemCodeEnum.NEED_LOGIN, "帐号不存在或密码错误");
        }

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        RedisUtil.setObject(Constant.REDIS_REFRESH_TOKEN + account, currentTimeMillis,
                Long.parseLong(Constant.REFRESH_TOKEN_EXPIRE_TIME));
        String token = JwtUtil.sign(account, currentTimeMillis);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return ApplicationResponse.succeed("登录成功");
    }

    /**
     * 剔除登录状态
     *
     * @return 剔除结果
     */
    @ApiOperation("注销")
    @PostMapping("/logout")
    public ApplicationResponse<String> deleteOnline() {
        CsmLogin csmLogin = userService.info();
        if (csmLogin == null) {
            return ApplicationResponse.fail(SystemCodeEnum.ARGUMENT_WRONG, "用户不存在！");
        }
        String key = Constant.REDIS_REFRESH_TOKEN + csmLogin.getLoginName();
        if (RedisUtil.hasKey(key)) {
            RedisUtil.deleteKey(key);
        }
        return ApplicationResponse.succeed();
    }

    /**
     * 增加用户地址
     *
     * @return 用户地址实体
     */
    @ApiOperation("增加用户地址")
    @PostMapping("/address/add")
    public ApplicationResponse<CsmAddr> addCsmAddr(Integer customerId, Integer zipcode, String address) {
        return ApplicationResponse.succeed(userService.addCsmAddress(customerId, zipcode, address));
    }

    /**
     * 删除用户地址
     *
     * @return 用户地址实体
     */
    @ApiOperation("删除用户地址")
    @DeleteMapping("/address")
    public ApplicationResponse<Void> deleteCsmAddrByCsmAddrId(Integer customerAddrId) {
        userService.deleteCsmAddrByCsmAddrId(customerAddrId);
        return ApplicationResponse.succeed("地址删除成功");
    }

    /**
     * 修改用户地址
     *
     * @return 用户地址实体
     */
    @ApiOperation("修改用户地址")
    @PostMapping("/address/update")
    public ApplicationResponse<CsmAddr> deleteCsmAddrByCsmAddrId(Integer customerAddrId, Integer zipcode, String address) {
        return ApplicationResponse.succeed("地址修改成功", userService.updateCsmAddress(customerAddrId, zipcode, address));
    }

    /**
     * 根据用户id获取用户地址列表
     *
     * @return 用户地址列表
     */
    @ApiOperation("获取用户地址")
    @GetMapping("/address")
    public ApplicationResponse<List<CsmAddr>> getCsmAddressListByCustomerId() {
        return ApplicationResponse.succeed(userService.getCsmAddressListByCustomerId(userService.info().getCustomerId()));
    }

    @ApiOperation("管理员获取所有用户所有地址信息【分页！！！！！！】")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    @GetMapping("/allAddressInfo")
    public ApplicationResponse<List<CsmAddr>> getAllAddressInfo(PageParam pageParam) {
        return ApplicationResponse.succeed(userService.getAllCsmAddress(pageParam));
    }

    @ApiOperation("管理员获取所有用户所有地址总数【！！！！！！总数】")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    @GetMapping("/allAddressInfo/sum")
    public ApplicationResponse<Long> getAllAddressInfoSum() {
        return ApplicationResponse.succeed(userService.getAllCsmAddressSum());
    }

    /**
     * 根据地址id设置默认地址
     *
     * @return 用户地址实体
     */
    @ApiOperation("根据地址id设置默认地址")
    @PostMapping("/setDefaultAddr/{customerAddrId}")
    public ApplicationResponse<CsmAddr> customerAddrId(@PathVariable Integer customerAddrId) {
        return ApplicationResponse.succeed(userService.setDefaultCsmAddress(customerAddrId));
    }
}
