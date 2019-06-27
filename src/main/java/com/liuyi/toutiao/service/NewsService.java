package com.liuyi.toutiao.service;

import com.liuyi.toutiao.dao.NewsDAO;
import com.liuyi.toutiao.model.News;
import com.liuyi.toutiao.util.ToutiaoUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {

    @Resource
    public NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    public News getNewsById(int newsId) {
        return newsDAO.getNewsById(newsId);
    }

    public String saveImage(MultipartFile file) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if(dotPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos+1).toLowerCase();
        if(!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

    public void addNews(News news) {
        newsDAO.addNews(news);
    }

    public void updateNewsCommentCount(int commentCount, int newsId) {
        newsDAO.updateNewsCommentCount(commentCount, newsId);
    }

    public int updateNewsLikeCount(int id, int likeCount) {
        return newsDAO.updateNewsLikeCount(id, likeCount);
    }

}
