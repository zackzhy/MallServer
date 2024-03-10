package com.bobooi.mall.api.controller;

import com.bobooi.mall.common.response.ApplicationResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.response.SystemCodeEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bobo
 * @date 2021/4/9
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/throw")
    public ApplicationResponse<String> rethrow(HttpServletRequest request) {
        String msg = (String)request.getAttribute("filter.error.msg");
        String code = (String)request.getAttribute("filter.error.code");
        return ApplicationResponse.fail(SystemCodeEnum.valueOf(code),msg);
    }
}
