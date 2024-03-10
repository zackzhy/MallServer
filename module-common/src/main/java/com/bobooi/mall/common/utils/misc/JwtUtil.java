package com.bobooi.mall.common.utils.misc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bobooi.mall.common.response.SystemCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.codec.Base64;
import org.springframework.stereotype.Component;
import com.bobooi.mall.common.exception.ApplicationException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author bobo
 * @date 2021/4/8
 */

@Component
@Slf4j
public class JwtUtil {
    public static final String ACCOUNT = "account";
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * 校验token是否正确
     * @param token Token
     * @return boolean 是否正确
     */
    public static boolean verify(String token) {
        try {
            // 帐号加JWT私钥解密
            String secret = getClaim(token, ACCOUNT)
                    + new String(Base64.decode(Constant.ENCRYPT_JWT_KEY));
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    /**
     * 生成签名
     * @param account 帐号
     * @return java.lang.String 返回加密的Token
     */
    public static String sign(String account, String currentTimeMillis){
        try {
            // 帐号加JWT私钥加密
            String secret = account + new String(Base64.decode(Constant.ENCRYPT_JWT_KEY));
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis()
                    + Long.parseLong(Constant.ACCESS_TOKEN_EXPIRE_TIME) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim(ACCOUNT, account)
                    .withClaim(CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken加密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new ApplicationException(SystemCodeEnum.SERVER_INNER_ERROR,
                    "JWTToken加密出现UnsupportedEncodingException异常:{}"+e.getMessage());
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token token串
     * @param claim 成分
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw new ApplicationException(SystemCodeEnum.SERVER_INNER_ERROR,
                    "解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    public static String getCurrentClaim(String claim){
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得Account
        return getClaim(token, claim);
    }
}
