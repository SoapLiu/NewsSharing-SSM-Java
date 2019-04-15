package com.liuyi.toutiao;

import com.liuyi.toutiao.dao.NewsDAO;
import com.liuyi.toutiao.dao.UserDAO;
import com.liuyi.toutiao.model.News;
import com.liuyi.toutiao.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTest {

    @Resource
    UserDAO userDAO;

    @Resource
    NewsDAO newsDAO;

    @Test
    public void contextLoads() {
        Random random = new Random();
        for(int i = 0; i < 19; i++) {
            User user = new User();
            user.setHeadUrl("/images/img/default.png");
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("Title{%d}", i));
            news.setLink(String.format("http://liuyi.cool/%d.html", i));
            news.setImage(String.format("/images/img/news.jpeg", i));
            newsDAO.addNews(news);

        }
    }
}
