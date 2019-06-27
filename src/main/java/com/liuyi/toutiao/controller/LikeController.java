package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.model.EntityType;
import com.liuyi.toutiao.model.HostHolder;
import com.liuyi.toutiao.model.User;
import com.liuyi.toutiao.service.LikeService;
import com.liuyi.toutiao.service.NewsService;
import com.liuyi.toutiao.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    NewsService newsService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/news/like"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {
        User user = hostHolder.getUser();
        long likeCount = likeService.like(user.getId(), EntityType.NEWS, newsId);
        newsService.updateNewsLikeCount(newsId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/news/dislike"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId) {
        User user = hostHolder.getUser();
        long likeCount = likeService.dislike(user.getId(), EntityType.NEWS, newsId);
        newsService.updateNewsLikeCount(newsId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }


}
