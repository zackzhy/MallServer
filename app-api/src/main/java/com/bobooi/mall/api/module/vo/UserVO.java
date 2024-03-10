package com.bobooi.mall.api.module.vo;

import com.bobooi.mall.data.entity.customer.CsmLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bobo
 * @date 2021/4/9
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private Integer id;
    private String account;

    public static UserVO fromUser(CsmLogin csmLogin) {
        return UserVO.builder()
                .id(csmLogin.getCustomerId())
                .account(csmLogin.getLoginName())
                .build();
    }
}
