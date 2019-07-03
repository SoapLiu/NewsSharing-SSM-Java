package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.async.EventModel;
import com.liuyi.toutiao.async.EventProducer;
import com.liuyi.toutiao.async.EventType;
import com.liuyi.toutiao.model.EntityType;
import com.liuyi.toutiao.model.HostHolder;
import com.liuyi.toutiao.model.News;
import com.liuyi.toutiao.model.User;
import com.liuyi.toutiao.service.LikeService;
import com.liuyi.toutiao.service.NewsService;
import com.liuyi.toutiao.util.ToutiaoUtil;
import org.apache.log4j.Logger;
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
    EventProducer eventProducer;

    @Autowired
    HostHolder hostHolder;

    private static final Logger log = Logger.getLogger(LikeController.class);

    @RequestMapping(path = {"/news/like"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {
        try {
            User user = hostHolder.getUser();
            long likeCount = likeService.like(user.getId(), EntityType.NEWS, newsId);
            News news = newsService.getNewsById(newsId);
            newsService.updateNewsLikeCount(newsId, (int) likeCount);
            eventProducer.fireEvent(new EventModel(EventType.LIKE)
                    .setActorId(user.getId())
                    .setEntityId(newsId)
                    .setEntityType(EntityType.NEWS)
                    .setEnetityOwnerId(news.getUserId()));
            return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
        } catch (Exception e) {
            log.error("点赞失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "点赞失败");
        }

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
