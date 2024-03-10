package com.bobooi.mall.data.bo;

import com.bobooi.mall.data.entity.customer.CsmInf;
import com.bobooi.mall.data.entity.customer.CsmLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bobo
 * @date 2021/8/6
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailBO {
    private CsmLogin csmLogin;
    private CsmInf csmInf;
}
