package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.async.EventModel;
import com.liuyi.toutiao.async.EventProducer;
import com.liuyi.toutiao.async.EventType;
import com.liuyi.toutiao.model.EntityType;
import com.liuyi.toutiao.service.UserService;
import com.liuyi.toutiao.util.ToutiaoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/user/register"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String register(Model model, @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "remember", defaultValue = "0") int rememberme) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if(map.containsKey("ticket")) {
                return ToutiaoUtil.getJSONString(0, "注册成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            log.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }
    }

    @RequestMapping(path = {"/user/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "remember", defaultValue = "0") int rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if(map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme > 0) {
                    cookie.setMaxAge(24 * 60 * 60 * 5);
                }
                response.addCookie(cookie);
                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                        .setActorId((int) map.get("userId"))
                        .setExt("username", username)
                        .setExt("to", "liuyi95@hotmail.com"));
                return ToutiaoUtil.getJSONString(0, "登录成功");
//                return "redirect:/";
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            log.error("登录异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "登录异常");
        }
    }

    @RequestMapping(path = {"/user/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String logout(@CookieValue("ticket") String ticket) {
        try {
            userService.logout(ticket);
            return ToutiaoUtil.getJSONString(0, "登出成功");
        } catch (Exception e) {
            log.error("登出失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "登出失败");
        }
    }

}
