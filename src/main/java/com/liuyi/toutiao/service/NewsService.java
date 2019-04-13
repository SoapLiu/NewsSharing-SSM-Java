package com.liuyi.toutiao.service;

import com.liuyi.toutiao.dao.NewsDAO;
import com.liuyi.toutiao.model.News;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsService {

    @Resource
    public NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }
}
