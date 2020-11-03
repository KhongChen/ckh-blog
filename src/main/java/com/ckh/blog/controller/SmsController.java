package com.ckh.blog.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ckh.blog.utils.RedisUtil;
import com.ckh.blog.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SmsController {

    @Autowired
    private RedisUtil redisUtil;

    // 发送手机验证码
    @PostMapping("/sms")
    public String sendSms(String phoneNumber, String code) throws ClientException {
        boolean result = SmsUtils.sendSms(phoneNumber, code);
        if (result) {
            // 短信发送成功后设置验证码过期时间5分钟
            redisUtil.set("sms:" + phoneNumber + ":code", code);
            redisUtil.expire("sms:" + phoneNumber + ":code", 300);
        }
        return "register";
    }
}
