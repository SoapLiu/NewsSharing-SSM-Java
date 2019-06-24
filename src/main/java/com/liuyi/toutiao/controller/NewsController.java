package com.liuyi.toutiao.controller;

import com.liuyi.toutiao.model.HostHolder;
import com.liuyi.toutiao.model.News;
import com.liuyi.toutiao.service.NewsService;
import com.liuyi.toutiao.util.ToutiaoUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

@Controller
public class NewsController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Resource
    NewsService newsService;

    @Resource
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
}
