package com.ckh.blog.controller.admin;

import com.ckh.blog.pojo.User;
import com.ckh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;



@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    //登录页
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    //用户登录
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            //防止密码保存在服务器上
            user.setPassword(null);
            session.setAttribute("user", user);
            return "redirect:/admin/blog/List";
        }else {
            attributes.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    //用户注销
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "admin/login";
    }

}
