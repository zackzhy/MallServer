package com.bobooi.mall.data.entity.customer;

import com.bobooi.mall.data.bo.valid.UserEditValidGroup;
import com.bobooi.mall.data.bo.valid.UserLoginValidGroup;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author bobo
 * @date 2021/3/31
 */

@Data
@Entity
@Table(name="customer_login")
@NoArgsConstructor
@AllArgsConstructor
public class CsmLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String loginName;
    private String password;
    private Integer roleId;
}
