package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.model.*;
import com.liuyi.toutiao.service.CommentService;
import com.liuyi.toutiao.service.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private HostHolder hostHolder;

    private static final Logger log = Logger.getLogger(LoginController.class);

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {

        try {
            News news = newsService.getNewsById(newsId);
            User user = hostHolder.getUser();
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.NEWS_COMMENT);   //0为news的评论
            comment.setStatus(0);
            comment.setUserId(user.getId());
            commentService.addComment(comment);
            newsService.updateNewsCommentCount(commentService.getCommentCount(EntityType.NEWS_COMMENT, newsId), newsId);
        } catch (Exception e) {
            log.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/news/" + String.valueOf(newsId);
    }

}
