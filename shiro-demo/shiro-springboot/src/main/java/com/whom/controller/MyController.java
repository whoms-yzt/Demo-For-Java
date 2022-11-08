package com.whom.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyController {

    protected Logger log = LoggerFactory.getLogger(MyController.class);

    @GetMapping(value = "/oauth2/hello")
    public void hello(){
        log.info("=========hello========");
        System.out.println("hello world!");
    }

    @GetMapping(value = "/toLogin")
    public ModelAndView toLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @GetMapping(value = "/index/add")
    public ModelAndView add(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add.html");
        return modelAndView;
    }

    @GetMapping(value = "/index/update")
    public ModelAndView update(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update.html");
        return modelAndView;
    }

    @GetMapping(value = "/unAuth")
    public String unAuth(){
        return "无权限访问";
    }

    @RequestMapping("/login")
    public ModelAndView login(String username,String password){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        /*获取当前用户*/
        Subject subject = SecurityUtils.getSubject();
        /*封装用户的登录数据*/
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            /*登录*/
            subject.login(token);
        } catch (UnknownAccountException e) {
            modelAndView.addObject("msg","用户名错误");
            modelAndView.setViewName("login.html");
        }catch (IncorrectCredentialsException e){
            modelAndView.addObject("msg","密码错误");
            modelAndView.setViewName("login.html");
        }
        return modelAndView;
    }
}
