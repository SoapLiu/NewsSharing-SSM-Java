package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.model.News;
import com.liuyi.toutiao.service.NewsService;
import com.liuyi.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    NewsService newsService;

    @RequestMapping(path = {"/", "home"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
//        List<News> newsList = newsService.selectByUserIdAndOffset(1, 0, 5);
//        model.addAttribute("news", newsList);
        return "home";
    }
}
