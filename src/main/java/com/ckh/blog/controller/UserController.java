package com.ckh.blog.controller;

import com.ckh.blog.pojo.User;
import com.ckh.blog.service.UserService;
import com.ckh.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, String code, RedirectAttributes attributes) {
        String phoneNumber = user.getPhoneNumber();
        user.setType(0); //普通用户
        user.setCreateTime(new Date());
        if (redisUtil.exist("sms:"+phoneNumber+":code")) {
            String phoneNumber_code = (String) redisUtil.get("sms:" + phoneNumber + ":code");
            System.out.println(phoneNumber_code);
            if (code.equals(phoneNumber_code)) {
                redisUtil.del("sms:"+phoneNumber+":code");
                int result = userService.saveUser(user);
                if (result>0) {
                    System.out.println("注册成功");
                    attributes.addFlashAttribute("msg","注册成功");
                    return "login";
                }else {
                    System.out.println("注册失败");
                    attributes.addFlashAttribute("msg","注册失败");
                    return "redirect:/register";
                }
            }else {
                System.out.println("注册失败,验证码不一致");
                attributes.addFlashAttribute("msg","注册失败,验证码不一致");
                return "redirect:/register";
            }
        }
        System.out.println("验证码已失效");
        attributes.addFlashAttribute("验证码已失效");
        return "redirect:/register";
    }
}
