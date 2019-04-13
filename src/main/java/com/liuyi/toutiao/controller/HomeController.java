package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.model.News;
import com.liuyi.toutiao.model.ViewObject;
import com.liuyi.toutiao.service.NewsService;
import com.liuyi.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    NewsService newsService;

    @RequestMapping(path = {"/", "home"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        List<News> newsList = newsService.getLatestNews(0, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for(News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }

        model.addAttribute("vos", vos);
        return "home";
    }
}
