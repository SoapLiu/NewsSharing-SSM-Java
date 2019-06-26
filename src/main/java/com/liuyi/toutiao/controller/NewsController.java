package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.model.Comment;
import com.liuyi.toutiao.model.HostHolder;
import com.liuyi.toutiao.model.News;
import com.liuyi.toutiao.model.ViewObject;
import com.liuyi.toutiao.service.CommentService;
import com.liuyi.toutiao.service.NewsService;
import com.liuyi.toutiao.service.UserService;
import com.liuyi.toutiao.util.ToutiaoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NewsController {

    private static final Logger log = Logger.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName, HttpServletResponse response) {
        try {
            response.setContentType("image/jpg");
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR  + imageName)),
                    response.getOutputStream());
        } catch (Exception e) {
            log.error("读取图片错误" + e.getMessage());
        }
    }

    @RequestMapping(path = {"/upload/newsImage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = newsService.saveImage(file);
            if(fileUrl == null) {
                return ToutiaoUtil.getJSONString(1, "上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0, fileUrl);
        } catch (Exception e) {
            log.error("上传图片失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "上传图片失败");
        }
    }

    @RequestMapping(path = {"/news/create/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("imageUrl") String image,
                          @RequestParam("title") String title,
                          @RequestParam("content") String link) {
        try {
            News news = new News();
            if(hostHolder.getUser() != null) {
                news.setUserId(hostHolder.getUser().getId());
            } else {
                //匿名id
                news.setUserId(1);
            }
            news.setImage(image);
            news.setTitle(title);
            news.setLink(link);
            news.setCreatedDate(new Date());
            newsService.addNews(news);

            return ToutiaoUtil.getJSONString(0, "添加资讯成功");
        } catch (Exception e) {
            log.error("添加资讯失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(2, "添加资讯失败");
        }
    }

    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetail(Model model, @PathVariable("newsId") int newsId) {
        News news = newsService.getNewsById(newsId);
        if(news != null) {
            //评论 todo:分页显示评论
            List<Comment> comments = commentService.selectCommentByEntity(0, newsId);
            List<ViewObject> commentvo = new ArrayList<>();
            for(Comment comment : comments) {
                ViewObject vo = new ViewObject();
                vo.set("user", userService.getUser(comment.getUserId()));
                vo.set("comment", comment);
                commentvo.add(vo);
            }
            model.addAttribute("comments", commentvo);

        }
        model.addAttribute("news", news);
        model.addAttribute("owner", userService.getUser(news.getUserId()));
        return "detail";
    }

}
